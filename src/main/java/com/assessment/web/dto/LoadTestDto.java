package com.assessment.web.dto;

public class LoadTestDto {
	
	String testName;
	
	String email;
	
	Integer sessionCount;
	
	Integer answersCount;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSessionCount() {
		return sessionCount;
	}

	public void setSessionCount(Integer sessionCount) {
		this.sessionCount = sessionCount;
	}

	public Integer getAnswersCount() {
		return answersCount;
	}

	public void setAnswersCount(Integer answersCount) {
		this.answersCount = answersCount;
	}
	
	

}
