package com.assessment.web.dto;

public class PayLoad {
	
	CustomArgs customArgs;
	
	TestSessionDTO results;
	
	String user;
	
	String testName;
	
	String testUrl;

	public CustomArgs getCustomArgs() {
		return customArgs;
	}

	public void setCustomArgs(CustomArgs customArgs) {
		this.customArgs = customArgs;
	}

	public TestSessionDTO getResults() {
		return results;
	}

	public void setResults(TestSessionDTO results) {
		this.results = results;
	}

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

	public String getTestUrl() {
		return testUrl;
	}

	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
	
	

}
