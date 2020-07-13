package com.assessment.services.impl;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.LMSUserModuleMapping;
import com.assessment.repositories.LMSUserModuleMappingRepository;
import com.assessment.services.LmsUserModuleMappingService;
@Service
@Transactional
public class LmsUserModuleMappingServiceImpl implements LmsUserModuleMappingService{

	@Autowired
	LMSUserModuleMappingRepository rep;
	
	@Override
	public List<LMSUserModuleMapping> getAllModulesForUser(String companyId, String user) {
		// TODO Auto-generated method stub
		return rep.getMappingsByUserAndCompany(user, companyId);
	}

	@Override
	public void saveOrUpdate(LMSUserModuleMapping moduleMapping) {
		// TODO Auto-generated method stub
		LMSUserModuleMapping moduleMapping2 = getByPrimaryKey(moduleMapping.getUser(), moduleMapping.getModuleId(), moduleMapping.getCompanyId());
		if(moduleMapping2 == null){
			moduleMapping.setCreateDate(new Date());
			rep.save(moduleMapping);
		}
		else{
			moduleMapping.setId(moduleMapping2.getId());
			moduleMapping.setCreateDate(moduleMapping2.getCreateDate());
			moduleMapping.setUpdateDate(new Date());
			Mapper mapper = new DozerBeanMapper();
			mapper.map(moduleMapping, moduleMapping2);
			rep.save(moduleMapping2);
			
		}
	}

	@Override
	public LMSUserModuleMapping getByPrimaryKey(String email, Long moduleId, String companyId) {
		// TODO Auto-generated method stub
		return rep.findByPrimaryKey(moduleId, email, companyId);
	}

	

}
