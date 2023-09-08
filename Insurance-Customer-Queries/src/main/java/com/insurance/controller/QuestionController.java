package com.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.Question;
import com.insurance.service.IQuestionService;

@RestController(value = "questionController")
@Scope(value = "request")
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	@Qualifier(value = "questionService")
	private IQuestionService questionService;

	// ==============================POST=================================//
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Question addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> answerQuestion(@PathVariable("questionId") Long questionId,
			@RequestBody Question questionWithAnswer) {
		Question question = questionService.addAnswer(questionId, questionWithAnswer);
		if (question != null) {
			return new ResponseEntity<>(question, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Question> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Question> getAllQuestionsByUsername(@PathVariable("username") String username) {
		return questionService.getQuestionsByUsername(username);
	}

	@GetMapping(value = "/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> getQuestionById(@PathVariable("questionId") Long questionId) {
		Question question = questionService.getQuestionById(questionId);
		if (question != null) {
			return new ResponseEntity<>(question, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
