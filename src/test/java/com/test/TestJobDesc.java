package com.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.JobDescription;
import com.assessment.services.JobDescriptionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestJobDesc {
	@Autowired
	JobDescriptionService jobDescService;
	
	@Test
	@Rollback(value=false)
	public void testCreateCompany() {
		List<JobDescription> descs = jobDescService.findByCompanyId("IH", PageRequest.of(0, 20)).getContent();
		System.out.println(descs.size());
	}

}
