package com.crusader.bt;

import org.springframework.boot.SpringApplication;

//@TestConfiguration(proxyBeanMethods = false)
public class TestBricklayerConstructorApplication {

	public static void main(String[] args) {
		SpringApplication.from(BricklayerConstructorApplication::main).with(TestBricklayerConstructorApplication.class).run(args);
	}

}
