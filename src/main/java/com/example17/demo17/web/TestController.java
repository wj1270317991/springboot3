package com.example17.demo17.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example17.demo17.utils.SftpUtil;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * com.example17.demo17.web
 * User: wangjun
 * Date: 2025/10/16 - 9:52
 * Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "test")
public class TestController {


    @RequestMapping("test1")
    public String test1(@RequestBody Map<String, Object> map) {
        Object o = map.get("test1");
        System.out.println(o);
        List<String> list = JSON.parseArray(JSON.toJSONString(map.get("test1")), String.class);
        System.out.println(list);
        return null;
    }
}
