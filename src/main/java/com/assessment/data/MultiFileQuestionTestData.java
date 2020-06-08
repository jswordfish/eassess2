package com.assessment.data;

public class MultiFileQuestionTestData {
	
	String testType;
	
	Integer weight;
	
	String testCaseDesc;
	
	String output;
	
	Throwable expectedException;

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getTestCaseDesc() {
		return testCaseDesc;
	}

	public void setTestCaseDesc(String testCaseDesc) {
		this.testCaseDesc = testCaseDesc;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Throwable getExpectedException() {
		return expectedException;
	}

	public void setExpectedException(Throwable expectedException) {
		this.expectedException = expectedException;
	}
	
	

}
