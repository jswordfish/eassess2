package com.assessment.data;

import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class License extends Base{
	
	@NotNull
	String licenseName;
	
	String licenseDesc;
	
	String licenseImage;

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getLicenseDesc() {
		return licenseDesc;
	}

	public void setLicenseDesc(String licenseDesc) {
		this.licenseDesc = licenseDesc;
	}

	public String getLicenseImage() {
		return licenseImage;
	}

	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
	}
	
	public String getFormattedCreationDate(){
			if(getCreateDate() != null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
				return dateFormat.format(getCreateDate());
			}
		return "NA";
		
	}
	
	public String getFormattedUpdationDate(){
		if(getUpdateDate() != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
			return dateFormat.format(getUpdateDate());
		}
	return "NA";
	
}

}
