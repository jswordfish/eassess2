package com.assessment.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
public class JobDescription extends Base {

	
	
	@Column(length=200)
	String name;
	
	String testName;
	
	@Column(length=3000)
	String description;

	@OneToMany(cascade=CascadeType.ALL, targetEntity=JobDescriptionSkill.class, orphanRemoval=true, fetch=FetchType.EAGER)
	@JoinColumn(name = "skill_id")
	List<JobDescriptionSkill> skills = new ArrayList<JobDescriptionSkill>();
	
	@Transient
	List<CandidateProfileParams> params = new ArrayList<>();
	
	@Transient
	String skillsDisplay;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public List<JobDescriptionSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<JobDescriptionSkill> skills) {
		this.skills = skills;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CandidateProfileParams> getParams() {
		return params;
	}

	public void setParams(List<CandidateProfileParams> params) {
		this.params = params;
	}

	public String getSkillsDisplay() {
		String ret = "";
		if(getSkills() != null && getSkills().size() > 0){
			int count = 0;
			for(JobDescriptionSkill skill: getSkills()){
				if(count < getSkills().size()-1){
					ret += skill.getQualifier1()+" - "+skill.getQualifier2()+" - "+skill.getQualifier3()+" - "+skill.getQualifier4()+" - "+skill.getQualifier5()+"<br/>";
				}
				else{
					ret += skill.getQualifier1()+" - "+skill.getQualifier2()+" - "+skill.getQualifier3()+" - "+skill.getQualifier4()+" - "+skill.getQualifier5();
				}
				
			}
		}
		
		return ret;
	}

	public void setSkillsDisplay(String skillsDisplay) {
		this.skillsDisplay = skillsDisplay;
	}
	
	
	
}
