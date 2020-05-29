package com.mysign.service.common;

/**
 * @Descrssh.getIp()tion T000
 * @Author Mr.Li
 * @Date 2020/5/17 9:23
 */

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mysign.service.po.ssh;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.util.Vector;

@Slf4j

public class FtpUtils {

@Autowired
    ssh ssh;

    /**
     * 利用JSch包实现SFTP上传文件
     * @param bytes  文件字节流
     * @param fileName  文件名
     * @throws Exception
     */
    public  void sshSftp(byte[] bytes,String fileName) throws Exception{
        Session session = null;
        Channel channel = null;
        JSch jsch = new JSch();
        String ip="112.124.31.166";
        String name="root";
        String password="teng,jiayou520";
        Integer port =22;

        if(port <=0){
            //连接服务器，采用默认端口
            session = jsch.getSession(name, ip);
            log.info("服务器连接成功");
        }else{
            //采用指定的端口连接服务器
            session = jsch.getSession(name, ip ,port);
            log.info("服务器连接成功");
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            log.info("服务器连接失败");
            throw new Exception("session is null");

        }

        //设置登陆主机的密码
        session.setPassword(password);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);


        OutputStream outstream = null;
        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;


            //进入服务器指定的文件夹
            sftp.cd("/www/server/images");

//            列出服务器指定的文件列表
//            Vector v = sftp.ls("*");
//            for(int i=0;i<v.size();i++){
//                System.out.println(v.get(i));
//            }

            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            outstream = sftp.put(fileName);
            outstream.write(bytes);
            log.info("文件上传成功==========");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关流操作
            if(outstream != null){
                outstream.flush();
                outstream.close();
            }
            if(session != null){
                session.disconnect();
            }
            if(channel != null){
                channel.disconnect();
            }
        }
    }

}
