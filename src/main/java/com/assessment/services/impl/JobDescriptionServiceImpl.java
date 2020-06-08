package com.assessment.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.JobDescription;
import com.assessment.repositories.JobDescriptionRepository;
import com.assessment.services.JobDescriptionService;
@org.springframework.stereotype.Service
@Transactional
public class JobDescriptionServiceImpl implements JobDescriptionService{
	
	@Autowired
	JobDescriptionRepository rep;

	@Override
	public JobDescription findbyPrimaryKey(String name, String companyId) {
		// TODO Auto-generated method stub
		return rep.findByPrimaryKey(name, companyId);
	}

	@Override
	public void saveOrUpdate(JobDescription description) {
		// TODO Auto-generated method stub
		JobDescription description2 = findbyPrimaryKey(description.getName(), description.getCompanyId());
		if(description2 == null){
			rep.save(description);
		}
		else{
			//update
			JobDescription description3 = new JobDescription();
			description3.setCompanyId(description.getCompanyId());
			description3.setCompanyName(description.getCompanyName());
			description3.setName(description.getName());
			description3.setDescription(description.getDescription());
			description3.setSkills(description.getSkills());
			description3.setTestName(description.getTestName());
			rep.deleteById(description2.getId());
			rep.save(description3);
		}
	}

	@Override
	public List<JobDescription> getJDsByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return rep.findByCompanyId(companyId);
	}

	@Override
	public Page<JobDescription> findByCompanyId(String companyId, Pageable pageable) {
		// TODO Auto-generated method stub
		return rep.findByCompanyId(companyId, pageable);
	}

	@Override
	public void deleteById(Long jid) {
		// TODO Auto-generated method stub
		rep.deleteById(jid);
	}

}
