package com.mart.monitorsensors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MonitorSensorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorSensorsApplication.class, args);
    }

}