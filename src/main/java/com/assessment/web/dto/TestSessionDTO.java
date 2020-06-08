package com.assessment.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSessionDTO {
Boolean testComplete;
	
	Integer noOfQuestionsAnswered;
	
	Integer totalQuestions;
	
	List<QualifierSkillLevelDto> skills = new ArrayList<QualifierSkillLevelDto>();
	
	Float averageScore;
	
	Float weightedScore;
	
	Boolean pass;
	
	Float passThresholdPercent;
	
	Integer noOfAttempts;
	
	Integer noOfAttemptsavailable;
	
	String start;
	
	String end;
	
	

	public Boolean getTestComplete() {
		return testComplete;
	}

	public void setTestComplete(Boolean testComplete) {
		this.testComplete = testComplete;
	}

	public Integer getNoOfQuestionsAnswered() {
		return noOfQuestionsAnswered;
	}

	public void setNoOfQuestionsAnswered(Integer noOfQuestionsAnswered) {
		this.noOfQuestionsAnswered = noOfQuestionsAnswered;
	}

	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public List<QualifierSkillLevelDto> getSkills() {
		return skills;
	}

	public void setSkills(List<QualifierSkillLevelDto> skills) {
		this.skills = skills;
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

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Float getPassThresholdPercent() {
		return passThresholdPercent;
	}

	public void setPassThresholdPercent(Float	 passThresholdPercent) {
		this.passThresholdPercent = passThresholdPercent;
	}

	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public Integer getNoOfAttemptsavailable() {
		return noOfAttemptsavailable;
	}

	public void setNoOfAttemptsavailable(Integer noOfAttemptsavailable) {
		this.noOfAttemptsavailable = noOfAttemptsavailable;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	
	
	
}
