package com.assessment.services.impl;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.TestProgram;
import com.assessment.repositories.TestProgramRepository;
import com.assessment.services.TestProgramService;

@Service
@Transactional
public class TestProgramServiceImpl implements TestProgramService {
	
	@Autowired
	TestProgramRepository rep;
	
	Logger logger = LoggerFactory.getLogger(TestProgramServiceImpl.class);
	
	@Override
	public void add(TestProgram program) {
		// TODO Auto-generated method stub
		logger.info("adding");
		rep.save(program);
	}

	@Override
	public void update(TestProgram program) {
		// TODO Auto-generated method stub
		logger.info("updating");
		TestProgram program2 = rep.findById(program.getId()).get();
		Mapper mapper = new DozerBeanMapper();
		program.setId(program2.getId());
		program.setName(program2.getName());
		program.setCreateDate(program2.getCreateDate());
		mapper.map(program, program2);
		program2.setUpdateDate(new Date());
		rep.save(program2);
	}

	@Override
	public List<TestProgram> getTestProgramsForUser(String companyId, String user) {
		// TODO Auto-generated method stub
		return rep.getTestProgramsForUser(companyId, user);
	}

	@Override
	public TestProgram getById(Long Id) {
		// TODO Auto-generated method stub
		return rep.findById(Id).get();
	}

}
