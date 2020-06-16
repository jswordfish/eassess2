package com.assessment.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.assessment.data.License;

public interface LicenseService {

	License findByPrimaryKey(String licenseName,  String companyId);
	
	License getById(Long id);
	
	List<License> getLicensesByCompany(String companyId);
	
	List<String> getLicensesInString(String companyId);
	
	License saveOrUpdate(License license);
	
	Page<License> getLicenses(String companyId, Pageable pageable);
	
	void deleteLicenseById(Long id);

}
