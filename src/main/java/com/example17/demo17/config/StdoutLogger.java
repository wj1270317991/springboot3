package com.example17.demo17.config;

/**
 * com.example17.demo17.config
 * ClassName: StdoutLogger
 * Description:
 * Create by: wangjun
 * Date: 2024/2/5 17:12
 */
public class StdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger{

    public StdoutLogger() {
    }

    public void logText(String text) {
        System.err.println(text);
    }
}
