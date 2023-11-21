package com.crusader.bt;

import org.springframework.boot.SpringApplication;

//@TestConfiguration(proxyBeanMethods = false)
public class TestBricklayerStorageApplication {

	public static void main(String[] args) {
		SpringApplication.from(BricklayerStorageApplication::main).with(TestBricklayerStorageApplication.class).run(args);
	}

}
