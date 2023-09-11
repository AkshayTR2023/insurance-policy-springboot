package com.insurance.proxy;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.Question;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "question-service")
public interface QuestionServiceProxy {

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForAddQuestion")
	@PostMapping(value = "/question", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Question addQuestion(@RequestBody Question question);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllQuestionsByCustomerId")
	@GetMapping(value = "/question/customer-id/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Question> getAllQuestionsByCustomerId(@PathVariable("customerId") Long customerId);

	// ==============================FALLBACKS=================================//
	public default Question fallbackForAddQuestion(Question question, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return new Question(0L, "default", null, 0L);
	}

	public default List<Question> fallbackForGetAllQuestionsByCustomerId(Long customerId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

}
