package com.assessment.services;

import java.util.List;

import com.assessment.data.CheCluster;

public interface CheService {
	
	public List<CheCluster> getAllClusters(String companyId);
	
	
	public void saveOrUpdate(CheCluster cheCluster);
	
	public String getApiURLAndSaveDetailsForCluster(String companyId);

}
