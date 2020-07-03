package com.assessment.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.assessment.data.Module;

public interface ModuleService {
	
	Module findUniqueModule(String moduleName, String companyId);
	
	Module saveOrUpdate(Module module);
	
	List<Module> findModulesByLicense(String licenseName, String companyId);
	
	Module getModuleById(Long id);
	
	Page<Module> getModules(String companyId, Pageable pageable);
	
	void deleteModule(Long id);
	
	 Page<Module>findByLicenseNamesIn(List<String> licenseName,String companyId,Pageable pageable);


}
