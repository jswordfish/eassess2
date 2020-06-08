package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.TestProgram;

public interface TestProgramRepository extends JpaRepository<TestProgram, Long> {
	
	@Query("SELECT t FROM TestProgram t WHERE t.companyId=:companyId and t.user=:user")
	public List<TestProgram> getTestProgramsForUser(@Param("companyId") String companyId, @Param("user") String user);
	
	

}
