package com.assessment.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ModuleItem extends Base {

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PARENTID", nullable = false)
	private Module parentModule;

	String itemName;

	String testName;

	String testDescription;

	Boolean mandatory = false;

	String imageUrl;

	String videoUrl;

	Integer itemLevelWeight;

	String externalImageUrl;

	String externalVideoUrl;

	@Transient
	String userSpecificLink;

	public ModuleItem() {

	}

	public ModuleItem(Module parentModule, String itemName, String testName, String testDescription,
			Boolean mandatory, String imageUrl, String videoUrl, Integer itemLevelWeight,
			String externalImageUrl, String externalVideoUrl) {
		this.parentModule = parentModule;
		this.itemName = itemName;
		this.testName = testName;
		this.testDescription = testDescription;
		this.mandatory = mandatory;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
		this.itemLevelWeight = itemLevelWeight;
		this.externalImageUrl = externalImageUrl;
		this.externalVideoUrl = externalVideoUrl;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
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

	public Integer getItemLevelWeight() {
		return itemLevelWeight;
	}

	public void setItemLevelWeight(Integer itemLevelWeight) {
		this.itemLevelWeight = itemLevelWeight;
	}

	public Module getParentModule() {
		return parentModule;
	}

	public void setParentModule(Module parentModule) {
		this.parentModule = parentModule;
	}

	public String getExternalImageUrl() {
		return externalImageUrl;
	}

	public void setExternalImageUrl(String externalImageUrl) {
		this.externalImageUrl = externalImageUrl;
	}

	public String getExternalVideoUrl() {
		return externalVideoUrl;
	}

	public void setExternalVideoUrl(String externalVideoUrl) {
		this.externalVideoUrl = externalVideoUrl;
	}

	@Override
	public int hashCode() {
		return getId() == null ? (getItemName() == null ? "-1" : getItemName()).hashCode()
				: getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModuleItem)) {
			return false;
		}

		ModuleItem moduleItem = (ModuleItem) obj;
		Long idtemp = getId() != null ? getId() : getItemName().hashCode();
		return this.hashCode() == moduleItem.hashCode();
	}

	public String getUserSpecificLink() {
		return userSpecificLink;
	}

	public void setUserSpecificLink(String userSpecificLink) {
		this.userSpecificLink = userSpecificLink;
	}

}
