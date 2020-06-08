package com.assessment.data;

import javax.persistence.Entity;

@Entity
public class CheCluster extends Base{

	String clusterName;
	
	String url;
	
	Integer noOfWorkspaces = 0;
	
	Boolean capacityReached = false;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getNoOfWorkspaces() {
		return noOfWorkspaces;
	}

	public void setNoOfWorkspaces(Integer noOfWorkspaces) {
		this.noOfWorkspaces = noOfWorkspaces;
		if(noOfWorkspaces != null && noOfWorkspaces <28){
			setCapacityReached(false);
		}
	}

	public Boolean getCapacityReached() {
		return capacityReached;
	}

	public void setCapacityReached(Boolean capacityReached) {
		this.capacityReached = capacityReached;
	}
	
	
}
