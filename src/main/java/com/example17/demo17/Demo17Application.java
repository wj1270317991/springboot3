package com.example17.demo17;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.example17.demo17.mapper")
@SpringBootApplication
@EnableCaching
public class Demo17Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo17Application.class, args);
    }

}
