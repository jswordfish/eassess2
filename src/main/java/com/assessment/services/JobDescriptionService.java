package com.assessment.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.assessment.data.JobDescription;

public interface JobDescriptionService {
	
	public JobDescription findbyPrimaryKey(String name, String companyId);
	
	public void saveOrUpdate(JobDescription description);
	
	public List<JobDescription> getJDsByCompanyId(String companyId);
	
	
	public Page<JobDescription> findByCompanyId(String companyId, Pageable pageable);
	
	public void deleteById(Long jid);

}
