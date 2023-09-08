package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.Question;
import com.insurance.repository.QuestionRepository;

@Service(value = "questionService")
@Scope(value = "singleton")
public class QuestionService implements IQuestionService {

	@Autowired
	@Qualifier(value = "questionRepository")
	QuestionRepository questionRepository;

	@Override
	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question addAnswer(Long questionId, Question questionWithAnswer) {
		Question question = questionRepository.findById(questionId).orElse(null);
		if (question != null) {
			question.setAnswer(questionWithAnswer.getAnswer());
			questionRepository.save(question);
			return question;
		}
		return null;
	}

	@Override
	public List<Question> getQuestionsByUsername(String username) {
		return questionRepository.findByUsername(username);
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public Question getQuestionById(Long questionId) {
		return questionRepository.findById(questionId).orElse(null);
	}

}
