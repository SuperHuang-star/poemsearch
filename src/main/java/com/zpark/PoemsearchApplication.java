package com.zpark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zpark.dao")
public class PoemsearchApplication {

    public static void main(String[] args) {
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(PoemsearchApplication.class, args);
    }

}
