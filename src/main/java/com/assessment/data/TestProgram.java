package com.assessment.data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class TestProgram extends Base {
	
	@Lob
	String code;
	
	
	String lang;
	
	String langid;
	
	String input;
	
	@Transient
	String output;
	
	
	String user;
	
	String name;
	
	@Transient
	String style = "font-size:100%;color:blue";
	

	public String getCode() {
	
			if(this.code == null){
				return "//start coding a new Program"+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+" //Click Enter to expand window";
			}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLangid() {
		return langid;
	}

	public void setLangid(String langid) {
		this.langid = langid;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	
	
}
