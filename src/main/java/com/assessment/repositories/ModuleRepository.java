package com.assessment.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {
	
	@Query(value="SELECT m FROM Module m WHERE m.companyId=:companyId and m.moduleName=:moduleName")
	public Module findUniqueModuleByName(@Param("companyId") String companyId, @Param("moduleName") String moduleName);
	
	@Query(value="SELECT m FROM Module m WHERE m.companyId=:companyId")
	public Page<Module> getModules(@Param("companyId") String companyId, Pageable pageable);
	
	
	
	@Query(value="SELECT m FROM Module m WHERE m.companyId=:companyId and upper(m.licenseNames) LIKE %:licenseName% ")
	public List<Module> findModulesByLicense(@Param("companyId") String companyId, @Param("licenseName") String licenseName);
}
