package com.example.bysj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bysj.mapper")
public class BysjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BysjApplication.class, args);
    }

}
