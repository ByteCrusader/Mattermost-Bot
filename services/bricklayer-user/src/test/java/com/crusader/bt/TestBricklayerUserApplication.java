package com.crusader.bt;

import org.springframework.boot.SpringApplication;

//@TestConfiguration(proxyBeanMethods = false)
public class TestBricklayerUserApplication {

    public static void main(String[] args) {
        SpringApplication.from(BricklayerUserApplication::main).with(TestBricklayerUserApplication.class).run(args);
    }

}
