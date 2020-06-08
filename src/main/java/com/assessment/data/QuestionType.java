package com.assessment.data;

public enum QuestionType {
	
MCQ("MCQ"), CODING("CODING"), FULL_STACK_JAVA("FULL_STACK_JAVA"), FULLSTACK("FULLSTACK"), 
FILL_BLANKS_MCQ("FILL_BLANKS_MCQ"), MATCH_FOLLOWING_MCQ("MATCH_FOLLOWING_MCQ");
	
	String type;
	private QuestionType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	
	

}
