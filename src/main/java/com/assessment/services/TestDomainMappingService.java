package com.assessment.services;

import java.util.List;

import com.assessment.data.TestDomainMapping;

public interface TestDomainMappingService {
	
	public TestDomainMapping findByPrimaryKey(String companyId, String testName, String domainName);
	
	
	public TestDomainMapping getById(Long id);
	
	
	public void removeById(TestDomainMapping mapping);
	
	public TestDomainMapping saveOrUpdate(TestDomainMapping testDomainMapping);
	
	public List<TestDomainMapping> getByCompany(String companyId);

}
