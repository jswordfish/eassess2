package com.assessment.data;

import javax.persistence.Entity;

@Entity
public class PieChart1 extends Base {

	String qualifer1;
	String qualifier2;
	Long percent;
	String userEmail;

	public Long getPercent() {
		return percent;
	}

	public void setPercent(Long percent) {
		this.percent = percent;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getQualifer1() {
		return qualifer1;
	}

	public void setQualifer1(String qualifer1) {
		this.qualifer1 = qualifer1;
	}

	public String getQualifier2() {
		return qualifier2;
	}

	public void setQualifier2(String qualifier2) {
		this.qualifier2 = qualifier2;
	}

	@Override
	public String toString() {
		return "PieChart1 [qualifer1=" + qualifer1 + ", qualifier2=" + qualifier2 + ", percent=" + percent + ", userEmail=" + userEmail + "]";
	}

}
