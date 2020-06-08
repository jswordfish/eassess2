package com.assessment.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.JobDescription;
import com.assessment.data.Question;

public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long>
{
	
	@Query("SELECT j FROM JobDescription j WHERE j.name=:name and j.companyId=:companyId")
	JobDescription findByPrimaryKey(@Param("name") String name, @Param("companyId") String companyId);
	
	
	@Query("SELECT j FROM JobDescription j WHERE j.companyId=:companyId")
	List<JobDescription> findByCompanyId(@Param("companyId") String companyId);
	
	
	@Query("SELECT j FROM JobDescription j WHERE j.companyId=:companyId")
	public Page<JobDescription> findByCompanyId(@Param("companyId") String companyId, Pageable pageable);
	
	@Query(value="SELECT j FROM JobDescription j WHERE j.companyId=:companyId and j.name LIKE %:searchText%")
	public Page<JobDescription> searchJobDescriptions(@Param("companyId") String companyId, @Param("searchText")  String searchText, Pageable pageable);

	
	
}