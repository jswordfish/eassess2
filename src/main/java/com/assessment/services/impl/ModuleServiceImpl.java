package com.assessment.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.Exceptions.AssessmentGenericException;
import com.assessment.data.Module;
import com.assessment.data.ModuleItem;
import com.assessment.repositories.ModuleItemRepository;
import com.assessment.repositories.ModuleRepository;
import com.assessment.services.ModuleService;
@Service
@Transactional
public class ModuleServiceImpl implements ModuleService{
	
	@Autowired
	ModuleRepository moduleRep;
	
	@Autowired
	ModuleItemRepository moduleItemRep;

	@Override
	public Module findUniqueModule(String moduleName, String companyId) {
		// TODO Auto-generated method stub
		return moduleRep.findUniqueModuleByName(companyId, moduleName);
	}

	@Override
	@Transactional
	public Module saveOrUpdate(Module module) {
		// TODO Auto-generated method stub
		if(module.getId() == null){
			
			Module module2 = moduleRep.findUniqueModuleByName(module.getCompanyId(), module.getModuleName());
				if(module2 != null){
					throw new AssessmentGenericException("MODULE_NAME_EXISTING");
				}
			//create
			module.setCreateDate(new Date());
			for(ModuleItem item : module.getItems()){
				item.setParentModule(module);
				item.setId(null);
			}
			return moduleRep.save(module);
		}
		else{
			//update
			Module module2 = moduleRep.findById(module.getId()).get();
				if(module2 == null){
					throw new AssessmentGenericException("ID_IS_NULL");
				}
				else{
					module.setCreateDate(module2.getCreateDate());
					moduleRep.delete(module2);
				}
				
				module.setUpdateDate(new Date());
				module.setId(null);
			Module modulenew = new Module(module.getModuleName(), module.getModuleDescription(), module.getImageUrl(), module.getVideoUrl(), new HashSet<ModuleItem>(), module.getLicenseNames(), module.getLics());
			modulenew.setCompanyId(module.getCompanyId());
			modulenew.setCompanyName(module.getCompanyName());
			
			Set<ModuleItem> items = module.getItems();
			
				for(ModuleItem item : items){
					item.setParentModule(module);
					item.setId(null);
				}
				//module.setItems(null);
				//modulenew.setItems(new HashSet<>());
				
				for(ModuleItem item : items){
					ModuleItem itemNew = new ModuleItem(modulenew, item.getItemName(), item.getTestName(), item.getTestDescription(), item.getMandatory(), item.getImageUrl(), item.getVideoUrl(), item.getItemLevelWeight(), item.getExternalImageUrl(), item.getExternalVideoUrl());
					itemNew.setCompanyId(module.getCompanyId());
					itemNew.setCompanyName(module.getCompanyName());
					modulenew.getItems().add(itemNew);
				}
			
			
			
			return moduleRep.save(modulenew);
		}
	}
	

	@Override
	public List<Module> findModulesByLicense(String licenseName, String companyId) {
		// TODO Auto-generated method stub
		return moduleRep.findModulesByLicense(companyId, licenseName);
	}

	@Override
	public Module getModuleById(Long id) {
		// TODO Auto-generated method stub
		return moduleRep.findById(id).get();
	}

	@Override
	public Page<Module> getModules(String companyId, Pageable pageable) {
		// TODO Auto-generated method stub
		return moduleRep.getModules(companyId, pageable);
	}

	@Override
	@Transactional
	public void deleteModule(Long id) {
		// TODO Auto-generated method stub
		Module module2 = moduleRep.findById(id).get();
		moduleRep.deleteById(id);
	}
	

}
