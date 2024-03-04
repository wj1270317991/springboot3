package com.example17.demo17.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.example17.demo17.web
 * ClassName: BatchController
 * Description:
 * Create by: wangjun
 * Date: 2024/3/1 14:08
 */
@Slf4j
@RestController
public class BatchController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job helloJob;

    @GetMapping("invokejob")
    public String invokejob()throws Exception{
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(helloJob, jobParameters);
        return "Batch job has been invoked";
    }
}
