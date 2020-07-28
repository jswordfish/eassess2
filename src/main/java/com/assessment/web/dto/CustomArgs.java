package com.assessment.web.dto;

public class CustomArgs {

	String courseId;
	
	String enrollmentId;
	
	String userId;
	
	String learningPathId;
	
	String sectionModuleInstanceId;
	
	String tenantId;
	
	String messagingQueueUrl;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLearningPathId() {
		return learningPathId;
	}

	public void setLearningPathId(String learningPathId) {
		this.learningPathId = learningPathId;
	}

	public String getSectionModuleInstanceId() {
		return sectionModuleInstanceId;
	}

	public void setSectionModuleInstanceId(String sectionModuleInstanceId) {
		this.sectionModuleInstanceId = sectionModuleInstanceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMessagingQueueUrl() {
		return messagingQueueUrl;
	}

	public void setMessagingQueueUrl(String messagingQueueUrl) {
		this.messagingQueueUrl = messagingQueueUrl;
	}
	
	
}
