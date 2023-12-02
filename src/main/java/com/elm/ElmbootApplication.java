package com.elm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.elm.mapper"})
public class ElmbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElmbootApplication.class, args);
    }

}
