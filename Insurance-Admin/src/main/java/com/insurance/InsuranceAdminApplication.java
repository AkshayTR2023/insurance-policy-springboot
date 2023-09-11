package com.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.insurance.exception.CustomErrorDecoder;

import brave.sampler.Sampler;
import feign.codec.ErrorDecoder;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class InsuranceAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceAdminApplication.class, args);
	}

	@Bean
	Sampler getSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

	@Bean
	ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
}
