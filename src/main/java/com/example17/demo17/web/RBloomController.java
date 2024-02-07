package com.example17.demo17.web;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class RBloomController {

    @Autowired
    private RBloomFilter<String> bloomFilter;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("test1")
    public String test1() {

        //add1000个
        for (int i = 0; i < 1000; i++) {
            bloomFilter.add("tom " + i);
        }
        int count = 0;
        //查询1000次
        for (int i = 0; i < 1000; i++) {
            if (bloomFilter.contains("tom" + i)) {
                count++;
            }
        }
        System.out.println("错误个数=" + count);
        System.out.println("'瓜田李下 1'是否存在：" + bloomFilter.contains("瓜田李下 " + 1));
        System.out.println("'海贼王'是否存在：" + bloomFilter.contains("海贼王"));
        System.out.println("预计插入数量：" + bloomFilter.getExpectedInsertions());
        System.out.println("容错率：" + bloomFilter.getFalseProbability());
        System.out.println("hash函数的个数：" + bloomFilter.getHashIterations());
        System.out.println("插入对象的个数：" + bloomFilter.count());
        return "success";
    }

}
