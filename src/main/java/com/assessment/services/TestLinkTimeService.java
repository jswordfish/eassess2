package com.assessment.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.assessment.data.Tenant;
import com.assessment.data.TestLinkTime;

public interface TestLinkTimeService {
	
	public void saveOrUpdate(TestLinkTime testLinkTime);
	
	public List<TestLinkTime> searchTestLinkTimes(String companyId, String testName);
	
	public void deleteTestLink(Long id);
	
	public Page<TestLinkTime> findAllLinks(Pageable pageable);

}
