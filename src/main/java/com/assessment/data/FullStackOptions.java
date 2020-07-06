package com.assessment.data;

public enum FullStackOptions {
	

NONE("NONE"), JAVA_FULLSTACK("JAVA_FULLSTACK"), DOTNET_FULLSTACK("DOTNET_FULLSTACK"), JAVASCRIPT_FULLSTACK("JAVASCRIPT_FULLSTACK"), PHP_FULLSTACK("PHP_FULLSTACK"), ANGULARJS_FULLSTACK("ANGULARJS_FULLSTACK"), JAVA_MONGODB("JAVA_MONGODB"), DOTNET_MONGODB("DOTNET_MONGODB"), PYTHON_FULLSTACK("PYTHON_FULLSTACK");
	
	String stack;
	private FullStackOptions(String stack) {
		this.stack = stack;
	}
	public String getStack() {
		return stack;
	}
	
	

}

