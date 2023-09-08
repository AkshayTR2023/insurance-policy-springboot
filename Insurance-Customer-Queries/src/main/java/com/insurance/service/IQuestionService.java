package com.insurance.service;

import java.util.List;

import com.insurance.entity.Question;

public interface IQuestionService {

	public Question addQuestion(Question question);
	public Question addAnswer(Long questionId, Question questionWithAnswer);
	public List<Question> getQuestionsByUsername(String username);
	public List<Question> getAllQuestions();
	public Question getQuestionById(Long questionId);
	
}
