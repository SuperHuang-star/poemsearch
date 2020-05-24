package com.zpark.conf;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//解决ElasticSearch的配置文件
@Configuration
public class ElasticSearchConfig {

        @PostConstruct
        void init() {
            System.setProperty("es.set.netty.runtime.available.processors", "false");
        }
}
