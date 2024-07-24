package com.example17.demo17.web;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    final static Map<Long,Integer> map = new ConcurrentHashMap<>();

    @GetMapping("invokejob")
    public String invokejob()throws Exception{
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(helloJob, jobParameters);
        return "Batch job has been invoked";
    }



    @GetMapping("test2")
    public String test2(Long tenantId)throws Exception{
        if (!addOrDecrease(tenantId,"add")){
            System.out.println("没有添加上");
        }
        System.out.println(JSONObject.toJSONString(map));
        addOrDecrease(tenantId,"decrease");
        return JSONObject.toJSONString(map);
    }


    private boolean addOrDecrease(Long tenantId,String type){
        Integer num = map.getOrDefault(tenantId, 0);
        if (type.equals("add")) {
            if (num < 10) {
                map.put(tenantId, ++num);
                return true;
            }
            return false;
        }
        if (type.equals("decrease")) {
            map.put(tenantId, --num);
        }
        return true;
    }
}
