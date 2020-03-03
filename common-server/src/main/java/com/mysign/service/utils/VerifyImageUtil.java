package com.mysign.service.utils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @description 图形验证码生成工具类
 * @author SQW
 * @date 2019年4月30日
 */
public class VerifyImageUtil {
    static int targetWidth = 55;//小图长
    static int targetHeight = 45;//小图宽
    static int circleR = 8;//半径
    static int r1 = 4;//距离点

    /**
     * @Createdate: 2019年1月24日上午10:52:42
     * @Title: getBlockData
     * @Description: 生成小图轮廓
     * @author zhoujin
     * @return int[][]
     * @throws
     */
    private static int[][] getBlockData() {
        int[][] data = new int[targetWidth][targetHeight];
        double x2 = targetWidth -circleR; //47

        //随机生成圆的位置
        double h1 = circleR + Math.random() * (targetWidth-3*circleR-r1);
        double po = Math.pow(circleR,2); //64

        double xbegin = targetWidth - circleR - r1;
        double ybegin = targetHeight- circleR - r1;

        //圆的标准方程 (x-a)²+(y-b)²=r²,标识圆心（a,b）,半径为r的圆
        //计算需要的小图轮廓，用二维数组来表示，二维数组有两张值，0和1，其中0表示没有颜色，1有颜色
        for (int i = 0; i < targetWidth; i++) {
            for (int j = 0; j < targetHeight; j++) {
                double d2 = Math.pow(j - 2,2) + Math.pow(i - h1,2);
                double d3 = Math.pow(i - x2,2) + Math.pow(j - h1,2);
                if ((j <= ybegin && d2 < po)||(i >= xbegin && d3 > po)) {
                    data[i][j] = 0;
                }  else {
                    data[i][j] = 1;
                }
            }
        }
        return data;
    }




    /**
     *
     * @Createdate: 2019年1月24日上午10:51:30
     * @Title: cutByTemplate
     * @Description: 有这个轮廓后就可以依据这个二维数组的值来判定抠图并在原图上抠图位置处加阴影,
     * @author zhoujin
     * @param oriImage  原图
     * @param targetImage  抠图拼图
     * @param templateImage 颜色
     * @param x
     * @param y void
     * @throws
     */
    private static void cutByTemplate(BufferedImage oriImage, BufferedImage targetImage, int[][] templateImage, int x, int y){
        int[][] martrix = new int[3][3];
        int[] values = new int[9];
        //创建shape区域
        for (int i = 0; i < targetWidth; i++) {
            for (int j = 0; j < targetHeight; j++) {
                int rgb = templateImage[i][j];
                // 原图中对应位置变色处理
                int rgb_ori = oriImage.getRGB(x + i, y + j);

                if (rgb == 1) {
                    targetImage.setRGB(i, j, rgb_ori);

                    //抠图区域高斯模糊
                    readPixel(oriImage, x + i, y + j, values);
                    fillMatrix(martrix, values);
                    oriImage.setRGB(x + i, y + j, avgMatrix(martrix));
                }else{
                    //这里把背景设为透明
                    targetImage.setRGB(i, j, rgb_ori & 0x00ffffff);
                }
            }
        }
    }


    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;
        for (int i = xStart; i < 3 + xStart; i++)
            for (int j = yStart; j < 3 + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;

                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty);

            }
    }

    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int avgMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color(r / 8, g / 8, b / 8).getRGB();
    }



    /**
     * @Description: 读取本地图片，生成拼图验证码
     * @author zhoujin
     * @return Map<String,Object>  返回生成的抠图和带抠图阴影的大图 base64码及抠图坐标
     */
    public static Map<String,Object> createImage(File file, Map<String,Object> resultMap){
        try {
            BufferedImage oriImage = ImageIO.read(file);
            Random random = new Random();
            //X轴距离右端targetWidth  Y轴距离底部targetHeight以上
            int widthRandom = random.nextInt(oriImage.getWidth()-  2*targetWidth) + targetWidth;
            int heightRandom = random.nextInt(oriImage.getHeight()- targetHeight);
            System.out.println("原图大小"+oriImage.getWidth()+ "x" +oriImage.getHeight()+"随机生成的坐标 X,Y 为（"+widthRandom+heightRandom+"）");

            BufferedImage targetImage= new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR);
            cutByTemplate(oriImage,targetImage,getBlockData(),widthRandom,heightRandom);

            resultMap.put("bigImage", getImageBASE64(oriImage));//大图
            resultMap.put("smallImage", getImageBASE64(targetImage));//小图
            resultMap.put("xWidth",widthRandom);
            resultMap.put("yHeight",heightRandom);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            return resultMap;
        }
    }


    /**
     * @Description: 读取网络图片，生成拼图验证码
     * @author zhoujin
     * @return Map<String,Object>  返回生成的抠图和带抠图阴影的大图 base64码及抠图坐标
     */
    public static Map<String,Object> createImage(String imgUrl, Map<String,Object> resultMap){
        try {
            //通过URL 读取图片
            URL url = new URL(imgUrl);
            BufferedImage bufferedImage = ImageIO.read(url.openStream());
            Random rand = new Random();
            int widthRandom = rand.nextInt(bufferedImage.getWidth()-  targetWidth - 100 + 1 ) + 100;
            int heightRandom = rand.nextInt(bufferedImage.getHeight()- targetHeight + 1 );
            System.out.println("原图大小{} x {},随机生成的坐标 X,Y 为（{}，{}）"+bufferedImage.getWidth()+bufferedImage.getHeight()+widthRandom+heightRandom);

            BufferedImage target= new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR);
            cutByTemplate(bufferedImage,target,getBlockData(),widthRandom,heightRandom);
            resultMap.put("bigImage", getImageBASE64(bufferedImage));//大图
            resultMap.put("smallImage", getImageBASE64(target));//小图
            resultMap.put("xWidth",widthRandom);
            resultMap.put("yHeight",heightRandom);
        } catch (Exception e) {
e.printStackTrace();
        } finally{
            return resultMap;
        }
    }


    /**
     * @Title: getImageBASE64
     * @Description: 图片转BASE64
     * @author zhoujin
     * @param image
     * @return
     * @throws IOException String
     */
    public static String getImageBASE64(BufferedImage image) throws IOException {
        byte[] imagedata = null;
        ByteArrayOutputStream bao=new ByteArrayOutputStream();
        ImageIO.write(image,"png",bao);
        imagedata=bao.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String BASE64IMAGE=encoder.encodeBuffer(imagedata).trim();
        BASE64IMAGE = BASE64IMAGE.replaceAll("\r|\n", "");  //删除 \r\n
        return BASE64IMAGE;
    }



}
