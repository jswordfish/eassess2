package com.assessment.web.dto;

import com.assessment.data.Module;

public class ModuleDTO {
	
	Module module;
	
	String sharedByEmail;
	
	String sharedByFullName;
	
	String learnerFullName;
	
	String learnerEmail;
	
	String sharedDate;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getSharedByEmail() {
		return sharedByEmail;
	}

	public void setSharedByEmail(String sharedByEmail) {
		this.sharedByEmail = sharedByEmail;
	}

	public String getSharedByFullName() {
		return sharedByFullName;
	}

	public void setSharedByFullName(String sharedByFullName) {
		this.sharedByFullName = sharedByFullName;
	}

	public String getLearnerFullName() {
		return learnerFullName;
	}

	public void setLearnerFullName(String learnerFullName) {
		this.learnerFullName = learnerFullName;
	}

	public String getLearnerEmail() {
		return learnerEmail;
	}

	public void setLearnerEmail(String learnerEmail) {
		this.learnerEmail = learnerEmail;
	}

	public String getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(String sharedDate) {
		this.sharedDate = sharedDate;
	}
	
	

}
