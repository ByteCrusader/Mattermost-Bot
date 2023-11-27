package com.crusader.bt;

import org.springframework.boot.SpringApplication;

//@TestConfiguration(proxyBeanMethods = false)
public class TestBricklayerEngineApplication {

	public static void main(String[] args) {
		SpringApplication.from(BricklayerEngineApplication::main).with(TestBricklayerEngineApplication.class).run(args);
	}

}
