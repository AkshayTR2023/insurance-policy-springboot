package com.insurance.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionWithCustomer {
	private Long questionId;
	private String question;
	private String answer;
	private Long customerId;
	private String customerName;
	private String customerEmail;

	public QuestionWithCustomer(Question questionObject, Customer customer) {
		this.questionId = questionObject.getQuestionId();
		this.question = questionObject.getQuestion();
		this.answer = questionObject.getAnswer();
		this.customerId = questionObject.getCustomerId();
		this.customerName = customer.getCustomerName();
		this.customerEmail = customer.getCustomerEmail();
	}
}
