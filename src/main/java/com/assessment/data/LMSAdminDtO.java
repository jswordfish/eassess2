package com.assessment.data;

public class LMSAdminDtO {
	
	String meetingId;
	
	String classifierfull;
	
	String email;
	
	String collegeName;
	
	String grade;
	
	String classifier;
	
	public LMSAdminDtO(){
		
	}
	
	public LMSAdminDtO(String meetingId, String classifier){
		this.meetingId = meetingId;
		this.classifier = classifier;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	public String getClassifierfull() {
		return classifierfull;
	}

	public void setClassifierfull(String classifierfull) {
		this.classifierfull = classifierfull;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	

}
