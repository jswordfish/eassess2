package com.assessment.web.dto;

import java.util.ArrayList;
import java.util.List;

public class TestSummary {
	
	String email;
	
	String firstName;
	
	String lastName;
	
	String testTakenDate;
	
	String testName;
	
	String companyId;
	
	String companyName;
	
	Float averageScore;
	
	Float weightedScore;
	
	Boolean testStatus;
	
	Boolean testComplete;
	
	List<TestAnswerData>  list = new ArrayList<TestAnswerData>();
	
	String errorMsg;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTestTakenDate() {
		return testTakenDate;
	}

	public void setTestTakenDate(String testTakenDate) {
		this.testTakenDate = testTakenDate;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<TestAnswerData> getList() {
		return list;
	}

	public void setList(List<TestAnswerData> list) {
		this.list = list;
	}

	public Float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Float averageScore) {
		this.averageScore = averageScore;
	}

	public Float getWeightedScore() {
		return weightedScore;
	}

	public void setWeightedScore(Float weightedScore) {
		this.weightedScore = weightedScore;
	}

	public Boolean getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Boolean testStatus) {
		this.testStatus = testStatus;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getTestComplete() {
		return testComplete;
	}

	public void setTestComplete(Boolean testComplete) {
		this.testComplete = testComplete;
	}
	
	

}
