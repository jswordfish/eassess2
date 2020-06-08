package com.assessment.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class JobDescriptionSkill extends Base {
	
	private String qualifier1;
	
	private String qualifier2 = "NA";
	
	private String qualifier3 = "NA";
	
	private String qualifier4 = "NA";
	
	private String qualifier5 = "NA";
	
	Integer weight;
	
	public JobDescriptionSkill(){
		
	}
	
	public JobDescriptionSkill(String qualifier1, String qualifier2, String qualifier3, String qualifier4, String qualifier5){
		this.qualifier1 = qualifier1==null?"NA":qualifier1;
		this.qualifier2 = qualifier2==null?"NA":qualifier2;
		this.qualifier3 = qualifier3==null?"NA":qualifier3;
		this.qualifier4 = qualifier4==null?"NA":qualifier4;
		this.qualifier5 = qualifier5==null?"NA":qualifier5;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	@Override
	public int hashCode(){
		String all = (getQualifier1()!= null?getQualifier1():"NA")+(getQualifier2()!= null?getQualifier2():"NA")+(getQualifier3()!= null?getQualifier3():"NA")+(getQualifier4()!= null?getQualifier4():"NA")+(getQualifier5()!= null?getQualifier5():"NA");
		return all.hashCode();
	}
	

}
