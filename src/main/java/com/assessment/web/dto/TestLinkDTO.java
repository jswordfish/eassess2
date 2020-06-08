package com.assessment.web.dto;

public class TestLinkDTO {
	
	String testName;
	
	Long testId;
	
	public TestLinkDTO(){
		
	}
	
	public TestLinkDTO( Long testId, String testName){
		this.testName = testName;
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}
	
	

}
