package com.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InsuranceCustomerQueriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceCustomerQueriesApplication.class, args);
	}

}
