package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.CheCluster;

public interface CheClusterRepository extends JpaRepository<CheCluster, Long> {
	
	@Query(value="SELECT q FROM CheCluster q WHERE q.companyId=:companyId and q.url=:url")
	public CheCluster findUniqueCheCluster(@Param("companyId") String companyId, @Param("url") String url);
	
	@Query(value="SELECT q FROM CheCluster q WHERE q.companyId=:companyId")
	public List<CheCluster> findCheClusters(@Param("companyId") String companyId);



}