package com.example17.demo17.web;

import com.example17.demo17.utils.SftpUtil;
import com.jcraft.jsch.JSchException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * com.example17.demo17.web
 * ClassName: RBloomController
 * Description: 布隆过滤器
 * Create by: wangjun
 * Date: 2024/1/15 17:59
 */
@Slf4j
@RestController
@RequestMapping(value = "sftp")
public class SftpController {

    @RequestMapping("create")
    public String test1() {
        try {
            SftpUtil.execShell();
        } catch (JSchException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }


    @RequestMapping("execShell2")
    public String execShell() {
        try {
            SftpUtil.execShellNoReturn();
        } catch (JSchException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }


    @RequestMapping("execShell3")
    public void execShell3(HttpServletResponse response)throws Exception {
        ByteArrayOutputStream outputStream = SftpUtil.execShell3();
        // 清空response
        response.reset();
        // 设置response的Header
        response.setCharacterEncoding("UTF-8");
        //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
        // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("aaaa.log", "UTF-8"));
        // 告知浏览器文件的大小
        response.addHeader("Content-Length", "" + outputStream.size());
        response.setContentType("application/octet-stream");
        ServletOutputStream sos = null;
        try {
            byte[] byteArray = outputStream.toByteArray();
            sos = response.getOutputStream();
            sos.write(byteArray, 0, byteArray.length);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (sos != null) {
                    sos.close();
                }
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }

    }


    @RequestMapping("readFile")
    public String readFile() {
        try {
            return SftpUtil.readFile();
        } catch (JSchException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
