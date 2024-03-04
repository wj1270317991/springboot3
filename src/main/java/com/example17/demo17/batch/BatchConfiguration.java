package com.example17.demo17.batch;

import com.alibaba.fastjson2.JSONObject;
import com.example17.demo17.entity.BigDataUsers;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * com.example17.demo17.batch
 * ClassName: BatchConfiguration
 * Description: spring-batch
 * Create by: wangjun
 * Date: 2024/2/29 15:07
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public Step step1() {
        return new StepBuilder("sampleStep", jobRepository)
                .<BigDataUsers, BigDataUsers>chunk(10, platformTransactionManager)
                .reader(reader()).listener(
                        new ItemReadListener<BigDataUsers>() {
                            @Override
                            public void beforeRead() {

                            }

                            @Override
                            public void afterRead(BigDataUsers item) {
                                System.out.println(JSONObject.toJSONString(item));
                            }

                            @Override
                            public void onReadError(Exception ex) {

                            }
                        })
                .processor(new UserItemProcessor())
                .writer(new UserItemWriter())
                .build();

    }

    @Bean
    public MyBatisPagingItemReader<BigDataUsers> reader(){
        MyBatisPagingItemReader<BigDataUsers> itemReader = new MyBatisPagingItemReader<>();
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        itemReader.setPageSize(100);
        Map<String,Object> map = new HashMap<>();
        map.put("id", "10");
        itemReader.setParameterValues(map);
        itemReader.setQueryId("com.example17.demo17.mapper.getList");
        return itemReader;
    }


    @Bean
    public Job helloJob(){
        return new JobBuilder("helloJob", jobRepository).start(step1()).build();
    }
}
