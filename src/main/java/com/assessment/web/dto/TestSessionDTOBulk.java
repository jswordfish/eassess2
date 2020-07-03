package com.assessment.web.dto;

import java.util.Date;

public class TestSessionDTOBulk {

	
	String user;
	
	String testName;
	
	
	
	Float percentageMarksRecieved;
	
	Integer totalMarksRecieved;
	
	Integer totalMarks;
	
	String sectionResults;
	
	Boolean pass = false;
	
	Date timeOfCompletion;
	
	Integer noOfQuestionsAnswered;
	
	String sectionsNoOfQuestionsNotAnswered;
	
	Float weightedScorePercentage;
	
	Integer noOfNonCompliances = 0;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Float getPercentageMarksRecieved() {
		return percentageMarksRecieved;
	}

	public void setPercentageMarksRecieved(Float percentageMarksRecieved) {
		this.percentageMarksRecieved = percentageMarksRecieved;
	}

	public Integer getTotalMarksRecieved() {
		return totalMarksRecieved;
	}

	public void setTotalMarksRecieved(Integer totalMarksRecieved) {
		this.totalMarksRecieved = totalMarksRecieved;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public String getSectionResults() {
		return sectionResults;
	}

	public void setSectionResults(String sectionResults) {
		this.sectionResults = sectionResults;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Date getTimeOfCompletion() {
		return timeOfCompletion;
	}

	public void setTimeOfCompletion(Date timeOfCompletion) {
		this.timeOfCompletion = timeOfCompletion;
	}

	public Integer getNoOfQuestionsAnswered() {
		return noOfQuestionsAnswered;
	}

	public void setNoOfQuestionsAnswered(Integer noOfQuestionsAnswered) {
		this.noOfQuestionsAnswered = noOfQuestionsAnswered;
	}

	public String getSectionsNoOfQuestionsNotAnswered() {
		return sectionsNoOfQuestionsNotAnswered;
	}

	public void setSectionsNoOfQuestionsNotAnswered(String sectionsNoOfQuestionsNotAnswered) {
		this.sectionsNoOfQuestionsNotAnswered = sectionsNoOfQuestionsNotAnswered;
	}

	public Float getWeightedScorePercentage() {
		return weightedScorePercentage;
	}

	public void setWeightedScorePercentage(Float weightedScorePercentage) {
		this.weightedScorePercentage = weightedScorePercentage;
	}

	public Integer getNoOfNonCompliances() {
		return noOfNonCompliances;
	}

	public void setNoOfNonCompliances(Integer noOfNonCompliances) {
		this.noOfNonCompliances = noOfNonCompliances;
	}
	
	
	
}
