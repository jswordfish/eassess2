package com.assessment.services.impl;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.TestDomainMapping;
import com.assessment.repositories.TestDomainMappingRepository;
import com.assessment.services.TestDomainMappingService;

@Service
@Transactional
public class TestDomainMappingServiceImpl implements TestDomainMappingService{
	@Autowired
	TestDomainMappingRepository rep;

	@Override
	public TestDomainMapping findByPrimaryKey(String companyId, String testName, String domainName) {
		// TODO Auto-generated method stub
		return rep.findTestDomainMappingByTestandDomain(companyId, testName, domainName);
	}

	@Override
	public TestDomainMapping getById(Long id) {
		// TODO Auto-generated method stub
		return rep.findById(id).get();
	}

	@Override
	public void removeById(TestDomainMapping mapping) {
		// TODO Auto-generated method stub
		rep.deleteById(mapping.getId());
	}

	@Override
	public TestDomainMapping saveOrUpdate(TestDomainMapping testDomainMapping) {
		TestDomainMapping testDomainMapping2 = findByPrimaryKey(testDomainMapping.getCompanyId(), testDomainMapping.getTestName(), testDomainMapping.getDomainName());
		if(testDomainMapping2 == null){
			//create
			testDomainMapping.setCreateDate(new Date());
			rep.save(testDomainMapping);
			return testDomainMapping;
		}
		else{
			Mapper mapper = new DozerBeanMapper();
			testDomainMapping.setId(testDomainMapping2.getId());
			testDomainMapping.setUpdateDate(new Date());
			mapper.map(testDomainMapping, testDomainMapping2);
			rep.save(testDomainMapping2);
			return testDomainMapping2;
		}
	}

	@Override
	public List<TestDomainMapping> getByCompany(String companyId) {
		// TODO Auto-generated method stub
		return rep.findTestDomainMappingByCompanyId(companyId);
	}

}
