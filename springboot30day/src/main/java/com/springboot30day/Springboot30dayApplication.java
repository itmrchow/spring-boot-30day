package com.springboot30day;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry //啟動retry
@SpringBootApplication
public class Springboot30dayApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot30dayApplication.class, args);
	}

}
