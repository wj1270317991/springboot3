package com.example17.demo17.web;

import com.example17.demo17.socket.WebSocketServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.example17.demo17.web
 * ClassName: WebsocketController
 * Description:
 * Create by: wangjun
 * Date: 2024/1/25 18:25
 */

@RestController
@RequestMapping("/socket")
public class WebsocketController {


    //推送数据接口
    @PostMapping("/push")
    public Map pushToWeb(Map<String,String> map) {
        Map<String,Object> result = new HashMap<>();
        try {
            String message = map.get("message");
            String sid = map.get("sid");
            WebSocketServer.sendInfo(map.get("message"), sid);
            result.put("code", sid);
            result.put("msg", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
