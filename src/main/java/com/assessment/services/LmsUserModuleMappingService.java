package com.assessment.services;

import java.util.List;

import com.assessment.data.LMSUserModuleMapping;

public interface LmsUserModuleMappingService {

	
	public List<LMSUserModuleMapping> getAllModulesForUser(String companyId, String user);
	
	
	public void saveOrUpdate(LMSUserModuleMapping moduleMapping);
	
	public LMSUserModuleMapping getByPrimaryKey(String email, Long moduleId, String companyId);
	
	
}
