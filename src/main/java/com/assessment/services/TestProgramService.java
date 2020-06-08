package com.assessment.services;

import java.util.List;

import com.assessment.data.TestProgram;

public interface TestProgramService {

	public void add(TestProgram program);
	
	public void update(TestProgram program);
	
	public List<TestProgram> getTestProgramsForUser(String companyId, String user);
	
	public TestProgram getById(Long Id);
}
