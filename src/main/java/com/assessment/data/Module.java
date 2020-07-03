package com.assessment.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Primary key companyId
 * @author jsutaria
 *
 */
@Entity
public class Module extends Base{
	@Column(nullable=false)
	String moduleName;
	
	@Column(length=5000)
	String moduleDescription;
	
	String imageUrl;
	
	String videoUrl;
	
	@JsonIgnore
	@OneToMany (cascade = {CascadeType.ALL}, mappedBy = "parentModule", orphanRemoval=true, fetch = FetchType.EAGER) 
	private Set<ModuleItem> items = new HashSet();
	
	/**
	 * ### sepaarted license names
	 */
	String licenseNames;
	
	@Transient
	List<String> lics;
	
	public Module(){
		
	}
	
	public Module(String moduleName, String moduleDescription, String imageUrl, String videoUrl, Set<ModuleItem> items, String licenseNames, List<String> lics){
		this.moduleName = moduleName;
		this.moduleDescription = moduleDescription;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
		this.items = items;
		this.licenseNames = licenseNames;
		this.lics = lics;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Set<ModuleItem> getItems() {
		return items;
	}

	public void setItems(Set<ModuleItem> items) {
		this.items = items;
	}

	public String getLicenseNames() {
		return licenseNames;
	}

	public void setLicenseNames(String licenseNames) {
		this.licenseNames = licenseNames;
	}

	public List<String> getLics() {
			if(getLicenseNames() != null && getLicenseNames().trim().length() > 0){
				String[] elements = getLicenseNames().split(",");
					for(String s: elements){
						s = s.trim();
					}
				List<String> fixedLenghtList = Arrays.asList(elements);
				return fixedLenghtList;
			}
		return lics;
	}

	public void setLics(List<String> lics) {
		this.lics = lics;
			if(lics != null && lics.size() != 0){
				 String string = String.join(",", lics);  
				 setLicenseNames(string);
			}
	}

	@Override
	public String toString() {
		return "Module [moduleName=" + moduleName + ", moduleDescription=" + moduleDescription + ", imageUrl="
				+ imageUrl + ", videoUrl=" + videoUrl + ", items=" + items + ", licenseNames="
				+ licenseNames + ", lics=" + lics + "]";
	}

	

	
	
	

}
