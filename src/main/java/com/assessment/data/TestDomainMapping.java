package com.assessment.data;

import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class TestDomainMapping extends Base{
	
	String testName;
	
	String domainName;
	
	Integer noOfAttempts;
	
	@Transient
	String ctDate;
	
	@Transient
	String upDate;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public String getCtDate() {
		
		if(getCreateDate() == null){
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		return dateFormat.format(getCreateDate());
	}

	public void setCtDate(String ctDate) {
		this.ctDate = ctDate;
	}

	public String getUpDate() {
		if(getUpdateDate() == null){
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		return dateFormat.format(getUpdateDate());
	}

	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}

	



	
	

}
