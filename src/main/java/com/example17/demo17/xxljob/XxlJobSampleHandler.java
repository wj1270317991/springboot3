package com.example17.demo17.xxljob;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * com.example17.demo17.xxljob
 * User: wangjun
 * Date: 2024/9/9 - 16:28
 * Description:
 */
@Component
@Slf4j
public class XxlJobSampleHandler {

    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        log.info("XXL-JOB, Hello World.");
    }

}