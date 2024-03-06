package com.example17.demo17.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * com.example17.demo17.config
 * ClassName: WebSocketConfig
 * Description:
 * Create by: wangjun
 * Date: 2024/3/6 16:20
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
