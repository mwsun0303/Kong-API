package com.sun.kong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @MapperScan("com.sun.kong")
public class KongApplication {

	public static void main(String[] args) {
		SpringApplication.run(KongApplication.class, args);
	}

}
