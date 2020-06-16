package com.assessment.services.impl;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.License;
import com.assessment.repositories.LicenseRepository;
import com.assessment.services.LicenseService;
@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {
	
	@Autowired
	LicenseRepository licenseRep;

	@Override
	public License findByPrimaryKey(String licenseName, String companyId) {
		// TODO Auto-generated method stub
		return licenseRep.findByPrimaryKey(licenseName, companyId);
	}

	@Override
	public List<License> getLicensesByCompany(String companyId) {
		// TODO Auto-generated method stub
		return licenseRep.getLicensesByCompany(companyId);
	}

	@Override
	public License saveOrUpdate(License license) {
		// TODO Auto-generated method stub
		license.setLicenseName(license.getLicenseName().trim());
		License license2 = findByPrimaryKey(license.getLicenseName(), license.getCompanyId());
			if(license2 == null){
				//create
				license.setCreateDate(new Date());
				return licenseRep.save(license);
			}
			else{
				//update
				Mapper mapper = new DozerBeanMapper();
				license.setId(license2.getId());
				license.setCreateDate(license2.getCreateDate());
				license.setUpdateDate(new Date());
				mapper.map(license, license2);
				return licenseRep.save(license2);
			}
	}

	@Override
	public List<String> getLicensesInString(String companyId) {
		// TODO Auto-generated method stub
		return licenseRep.getLicensesInStringByCompany(companyId);
	}

	@Override
	public Page<License> getLicenses(String companyId, Pageable pageable) {
		// TODO Auto-generated method stub
		return licenseRep.getLicenses(companyId, pageable);
	}

	@Override
	public License getById(Long id) {
		// TODO Auto-generated method stub
		return licenseRep.findById(id).get();
	}

	@Override
	public void deleteLicenseById(Long id) {
		// TODO Auto-generated method stub
		licenseRep.deleteById(id);
	}
}
