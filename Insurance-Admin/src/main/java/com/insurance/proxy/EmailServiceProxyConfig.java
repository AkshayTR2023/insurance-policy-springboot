package com.insurance.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

@Configuration
public class EmailServiceProxyConfig {

	@Bean
	public RetryConfig adminRetryConfig() {
	    return RetryConfig.custom()
	            .maxAttempts(1) 
	            .build();
	}

	@Bean
	public Retry adminRetry(RetryConfig adminRetryConfig) {
	    return Retry.of("admin-feign-retry", adminRetryConfig);
	}

	@Bean
	public CircuitBreakerConfig adminCircuitBreakerConfig() {
	    // Customize the circuit breaker configuration here
	    return CircuitBreakerConfig.custom()
	            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
	            .slidingWindowSize(5) // Number of calls in the sliding window
	            .failureRateThreshold(50) // Threshold for failure rate in percentage
	            .waitDurationInOpenState(java.time.Duration.ofSeconds(30)) // Time to wait before transitioning to half-open state
	            .build();
	}

	@Bean
	public io.github.resilience4j.circuitbreaker.CircuitBreaker adminCircuitBreaker(CircuitBreakerConfig adminCircuitBreakerConfig) {
	    return io.github.resilience4j.circuitbreaker.CircuitBreaker.of("admin-feign-cb", adminCircuitBreakerConfig);
	}

}