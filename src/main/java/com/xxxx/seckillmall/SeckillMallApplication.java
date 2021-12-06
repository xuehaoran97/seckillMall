package com.xxxx.seckillmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xxxx.seckillmall.mapper")
public class SeckillMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillMallApplication.class, args);
    }

}
