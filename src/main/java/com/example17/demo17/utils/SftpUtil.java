package com.example17.demo17.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.alibaba.fastjson2.JSONObject;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.example17.demo17.utils
 * ClassName: SftpUtil
 * Description:
 * Create by: wangjun
 * Date: 2024/3/6 13:51
 */
@Slf4j
public class SftpUtil {



    public static void execShell() throws JSchException {
        Session session = JschUtil.getSession("192.168.1.250", 22, "root", "Sino_123qwe");
        Sftp sftp = JschUtil.createSftp(session);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String path = IdUtil.simpleUUID();
        if (!sftp.exist("/tmp")){
            log.info("目录：/tmp 没有创建");
            sftp.mkDirs("/tmp");
        }
        String cmd = "cd /tmp & unzip -uu -o -O CP936 aa.zip -d "+ path +" && cd " + path
                + " && unzip -uu -o -O CP936 *.zip ";
        String s = JschUtil.exec(session, cmd, Charset.defaultCharset(),baos);
        String error = baos.toString();
        log.info("执行shell的返回结果："+s);
        log.info("执行shell的错误信息为："+error);
    }



    public static void execShellNoReturn() throws JSchException {
        Session session = JschUtil.getSession("192.168.1.143", 22, "root", "Arrow123");
        String filename = "/root/shell/0c1505eab8e047cf979344da9cb49a40_HQ_202310_pt_vendorpublish_attachment_busi.zip";
        String cmd = "sh /root/shell/aa.sh " + filename + " bbb &";
        long ss = System.currentTimeMillis();
        String s = JschUtil.exec(session, cmd, Charset.defaultCharset());
        System.out.println(System.currentTimeMillis() - ss);
        log.info("执行shell的返回结果："+s);
    }


    public static String readFile() throws JSchException {
//        Session session = JschUtil.getSession("192.168.1.143", 22, "root", "Arrow123");
        Sftp sftp = JschUtil.createSftp("192.168.1.143", 22, "root", "Arrow123");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sftp.get("/root/unziplogs/0c1505eab8e047cf979344da9cb49a40_HQ_202310_pt_vendorpublish_attachment_busi.zip.txt",baos );
        sftp.close();
        String str = baos.toString();
        return str;
    }

    public static ByteArrayOutputStream execShell3() {

        String doShell = "ls -ltr  /var/log/ |grep  log";
        Session session = null;
        Map<String,String> res = null;
        try {
            session = JschUtil.createSession("192.168.1.143", 22, "root", "Arrow123");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String result = JschUtil.exec(session, doShell, Charset.defaultCharset(),baos);
            String error = baos.toString();
            res = new HashMap<>();
            res.put("result", result);
            List<String> split = StrUtil.split(result, "\n");
            String regex = ".+\s+(.+log)";
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(split.get(0));
            if (!m.find()) {
                System.out.println("文件路径格式错误!");
                throw new Exception("文件路径格式错误");
            }
            System.out.println(JSONObject.toJSONString(m.group()));
            String localFullPathName = "/var/log/" + m.group(1);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JschUtil.createSftp(session).download(localFullPathName , byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (Exception e) {

        } finally {
            JschUtil.close(session);
        }
        return null;
    }
}
