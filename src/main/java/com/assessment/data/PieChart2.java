package com.assessment.data;

import javax.persistence.Entity;

@Entity
public class PieChart2 extends Base {

	String qualifier2;
	String qualifier3;
	Long percent;
	String userEmail;

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

	@Override
	public String toString() {
		return "PieChart2 [qualifier2=" + qualifier2 + ", qualifier3=" + qualifier3 + ", percent=" + percent + ", userEmail=" + userEmail + "]";
	}

}
