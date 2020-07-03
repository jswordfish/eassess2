package com.assessment.data;

import javax.persistence.Entity;

@Entity
public class LearnersProfileParam extends Base {

	String userEmail;
	String qualifier1;
	String qualifier2;
	String qualifier3;
	String qualifier4;
	String qualifier5;
	String qparamValue;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getQualifier1() {
		return qualifier1;
	}
	public void setQualifier1(String qualifier1) {
		this.qualifier1 = qualifier1;
	}
	public String getQualifier2() {
		return qualifier2;
	}
	public void setQualifier2(String qualifier2) {
		this.qualifier2 = qualifier2;
	}
	public String getQualifier3() {
		return qualifier3;
	}
	public void setQualifier3(String qualifier3) {
		this.qualifier3 = qualifier3;
	}
	public String getQualifier4() {
		return qualifier4;
	}
	public void setQualifier4(String qualifier4) {
		this.qualifier4 = qualifier4;
	}
	public String getQualifier5() {
		return qualifier5;
	}
	public void setQualifier5(String qualifier5) {
		this.qualifier5 = qualifier5;
	}
	public String getQparamValue() {
		return qparamValue;
	}
	public void setQparamValue(String qparamValue) {
		this.qparamValue = qparamValue;
	}
	
	@Override
	public String toString() {
		return "LearnersProfileParam [userEmail=" + userEmail + ", qualifier1=" + qualifier1 + ", qualifier2="
				+ qualifier2 + ", qualifier3=" + qualifier3 + ", qualifier4=" + qualifier4 + ", qualifier5="
				+ qualifier5 + ", qparamValue=" + qparamValue + "]";
	}
	
       

}