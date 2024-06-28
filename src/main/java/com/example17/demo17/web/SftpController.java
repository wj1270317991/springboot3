package com.example17.demo17.web;

import com.example17.demo17.utils.SftpUtil;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
