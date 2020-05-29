package com.mysign.service.controller;

import com.mysign.service.common.FtpUtils;
import com.mysign.service.service.curseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description T000
 * @Author Mr.Li
 * @Date 2020/5/5 12:09
 */
@RestController
@RequestMapping("publish")
@Slf4j
public class PublishCurseController {
@Autowired
com.mysign.service.service.curseService curseService;
@PostMapping("curse")
    public Map<String,Object> uploadCurseTitle(@RequestParam MultipartFile fileName) throws Exception {

    String originalFilename = fileName.getOriginalFilename();
log.info("传入文件：{}",originalFilename);
    Map<String,Object> map=new ConcurrentHashMap();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info("文件后缀：{}",substring);
    FtpUtils ftpUtils = new FtpUtils();
    String image = UUID.randomUUID().toString().replaceAll("-", "")+substring;
    ftpUtils.sshSftp(fileName.getBytes(),image);
    BufferedImage bufferedImage = ImageIO.read(new URL("http://112.124.31.166:8888//download?filename=/www/server/images/" + image));
    log.info("图片流：{}",bufferedImage);
//    if (bufferedImage!=null){
        map.put("code",200);
        map.put("message",image);
        log.info("文件上传成功，访问路径为：{}",image);
//    }else{
//        map.put("code",500);
//        map.put("message","请传入图片格式的文件");
//        log.info("================传入非图片格式文件============");
//    }
//    System.out.println("s = " + s);
//    File file1=new File(s);
//    try {
//        fileName.transferTo(file1);
//    } catch (IOException e) {
//        map.put("code",500);
//        map.put("message","文件上传失败");
//    }
//    try {
//        if (ImageIO.read(file1)!=null)

//    } catch (IOException e) {
//        map.put("code",500);
//        map.put("message","请上传图片类型的文件");
//    }
    return map;
}

}
