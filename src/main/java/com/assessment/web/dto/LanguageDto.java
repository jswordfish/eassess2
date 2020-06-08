package com.assessment.web.dto;

public class LanguageDto {
	
	String langid;
	
	String lang;
	
	public LanguageDto(){
		
	}
	
	public LanguageDto(String langid, String lang){
		this.lang = lang;
		this.langid = langid;
	}

	public String getLangid() {
		return langid;
	}

	public void setLangid(String langid) {
		this.langid = langid;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

}
