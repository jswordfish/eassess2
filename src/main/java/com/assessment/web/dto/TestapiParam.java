package com.assessment.web.dto;

public class TestapiParam {
	String date;

	String[] emails;

	String SendResultToParticipant;
	
	String message;

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public String getSendResultToParticipant() {
		return SendResultToParticipant;
	}

	public void setSendResultToParticipant(String sendResultToParticipant) {
		SendResultToParticipant = sendResultToParticipant;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
