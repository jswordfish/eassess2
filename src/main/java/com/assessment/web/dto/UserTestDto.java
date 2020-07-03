package com.assessment.web.dto;

import java.util.Date;

public class UserTestDto {

	String firstName;
	String lastName;
	String testName;
	Integer totalMarks;
	Integer totalMarksReceived;
	String result;
	Boolean pass = false;
	Date date;
	String fullName;

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

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Integer getTotalMarksReceived() {
		return totalMarksReceived;
	}

	public void setTotalMarksReceived(Integer totalMarksReceived) {
		this.totalMarksReceived = totalMarksReceived;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "UserTestDto [firstName=" + firstName + ", lastName=" + lastName + ", testName=" + testName
				+ ", totalMarks=" + totalMarks + ", totalMarksReceived="
				+ totalMarksReceived + ", result=" + result + ", pass=" + pass + ", date="
				+ date + ", fullName=" + fullName + "]";
	}
}
