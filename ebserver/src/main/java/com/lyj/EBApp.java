package com.lyj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.lyj.mapper"})
public class EBApp {
    public static void main(String[] args) {
        SpringApplication.run(EBApp.class,args);
    }
}
