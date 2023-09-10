package com.insurance.proxy;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.Question;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "question-service")
public interface QuestionServiceProxy {

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForAddQuestion")
	@PostMapping(value = "/question", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Question addQuestion(@RequestBody Question question);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForAnswerQuestion")
	@PutMapping(value = "/question/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> answerQuestion(@PathVariable("questionId") Long questionId,
			@RequestBody Question questionWithAnswer);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllQuestions")
	@GetMapping(value = "/question", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Question> getAllQuestions();

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllQuestionsByCustomerId")
	@GetMapping(value = "/question/customer-id/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Question> getAllQuestionsByCustomerId(@PathVariable("customerId") Long customerId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetQuestionById")
	@GetMapping(value = "/question/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> getQuestionById(@PathVariable("questionId") Long questionId);

	// ==============================FALLBACKS=================================//
	public default Question fallbackForAddQuestion(Question question, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return new Question(0L, "default", null, 0L);
	}

	public default ResponseEntity<Question> fallbackForAnswerQuestion(Long questionId, Question questionWithAnswer,
			Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Question(0L, "default", "default", 0L));

	}

	public default List<Question> fallbackForGetAllQuestions(Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default List<Question> fallbackForGetAllQuestionsByCustomerId(Long customerId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default ResponseEntity<Question> fallbackForGetQuestionById(Long questionId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Question(questionId, "default", "default", 0L));

	}

}
