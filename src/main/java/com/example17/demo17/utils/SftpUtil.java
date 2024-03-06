package com.example17.demo17.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

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
}
