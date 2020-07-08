package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.LMSUserModuleMapping;

public interface LMSUserModuleMappingRepository extends JpaRepository<LMSUserModuleMapping,Long>{

	
	@Query("SELECT c FROM LMSUserModuleMapping c WHERE c.moduleId=:moduleId and c.user=:user  and c.companyId=:companyId")
	LMSUserModuleMapping findByPrimaryKey(@Param("moduleId") Long moduleId,  @Param("user") String user, @Param("companyId") String companyId);
	
	@Query("SELECT c FROM LMSUserModuleMapping c WHERE c.companyId=:companyId and c.user=:user")
	List<LMSUserModuleMapping> getMappingsByUserAndCompany(@Param("user") String user, @Param("companyId") String companyId);
	
	
	
	

}
