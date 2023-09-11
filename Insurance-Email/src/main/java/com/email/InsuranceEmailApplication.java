package com.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InsuranceEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceEmailApplication.class, args);
	}

}
