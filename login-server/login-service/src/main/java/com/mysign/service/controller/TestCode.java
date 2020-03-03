
package com.mysign.service.controller;

import com.mysign.service.utils.VerifyImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("test")
public class TestCode {

    /**
     * @param @return 参数说明
     * @return BaseRestResult 返回类型
     * @Description: 生成滑块拼图验证码
     */
    @RequestMapping(value = "/getImage", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String getImageVerifyCode(@Autowired HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        //读取本地路径下的图片,随机选一条
        File file = new File(this.getClass().getResource("/image").getPath());
        File[] files = file.listFiles();
        int n = new Random().nextInt(files.length);
        File imageUrl = files[n];
        resultMap = VerifyImageUtil.createImage(imageUrl, resultMap);

        //读取网络图片
        //ImageUtil.createImage("http://hbimg.b0.upaiyun.com/7986d66f29bfeb6015aaaec33d33fcd1d875ca16316f-2bMHNG_fw658",resultMap);
        request.getSession().setAttribute("xWidth", resultMap.get("xWidth"));
        System.out.println(resultMap.get("xWidth"));
        resultMap.remove("xWidth");
        resultMap.put("errcode", 0);
        resultMap.put("errmsg", "success");
        return "完成";
    }


    /**
     * 校验滑块拼图验证码
     *
     * @param moveLength 移动距离
     * @return BaseRestResult 返回类型
     * @Description: 生成图形验证码
     */
    @RequestMapping(value = "/verifyImageCode.do", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String verifyImageCode(@RequestParam(value = "moveLength") String moveLength, @Autowired HttpServletRequest request) {
        Double dMoveLength = Double.valueOf(moveLength);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer xWidth = (Integer) request.getSession().getAttribute("xWidth");
            if (xWidth == null) {
                resultMap.put("errcode", 1);
                resultMap.put("errmsg", "验证过期，请重试");
                return null;
            }
            if (Math.abs(xWidth - dMoveLength) > 10) {
                resultMap.put("errcode", 1);
                resultMap.put("errmsg", "验证不通过");
            } else {
                resultMap.put("errcode", 0);
                resultMap.put("errmsg", "验证通过");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getSession().removeAttribute("xWidth");
        }
        return "完成";
    }

}
