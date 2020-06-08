package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.TestDomainMapping;

public interface TestDomainMappingRepository extends JpaRepository<TestDomainMapping, Long> {
	
	@Query(value="SELECT q FROM TestDomainMapping q WHERE q.companyId=:companyId and q.testName=:testName and q.domainName=:domainName")
	public TestDomainMapping findTestDomainMappingByTestandDomain(@Param("companyId") String companyId, @Param("testName") String testName, @Param("domainName") String domainName);
	
	
	@Query(value="SELECT q FROM TestDomainMapping q WHERE q.companyId=:companyId")
	public List<TestDomainMapping> findTestDomainMappingByCompanyId(@Param("companyId") String companyId);

}
