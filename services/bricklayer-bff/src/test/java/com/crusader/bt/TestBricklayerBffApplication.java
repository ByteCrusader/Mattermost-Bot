package com.crusader.bt;

import org.springframework.boot.SpringApplication;

//@TestConfiguration(proxyBeanMethods = false)
public class TestBricklayerBffApplication {

	public static void main(String[] args) {
		SpringApplication.from(BricklayerBffApplication::main).with(TestBricklayerBffApplication.class).run(args);
	}

}
