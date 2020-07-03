package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.LearnersProfileParam;

public interface LearnersProfileParamRepository extends JpaRepository<LearnersProfileParam, Long> {

	List<LearnersProfileParam> findByuserEmail(String email);

	@Query(value = "from LearnersProfileParam lp where lp.userEmail=:userEmail and lp.qualifier1=:qualifier1")
	List<LearnersProfileParam> getQValue1(@Param("userEmail") String email, @Param("qualifier1") String qualifer1);

	@Query(value = "from LearnersProfileParam lp where lp.userEmail=:userEmail and lp.qualifier1=:qualifier1 and lp.qualifier2=:qualifier2")
	List<LearnersProfileParam> getQValue2(@Param("userEmail") String email, @Param("qualifier1") String qualifer1,
			@Param("qualifier2") String qualifer2);

	@Query(value = "from LearnersProfileParam lp where lp.userEmail=:userEmail and lp.qualifier1=:qualifier1 and lp.qualifier2=:qualifier2 and lp.qualifier3=:qualifier3")
	List<LearnersProfileParam> getQValue3(@Param("userEmail") String email, @Param("qualifier1") String qualifer1,
			@Param("qualifier2") String qualifer2, @Param("qualifier3") String qualifer3);

	@Query(value = "from LearnersProfileParam lp where lp.userEmail=:userEmail and lp.qualifier1=:qualifier1 and lp.qualifier2=:qualifier2 and lp.qualifier3=:qualifier3 and lp.qualifier4=:qualifier4")
	List<LearnersProfileParam> getQValue4(@Param("userEmail") String email, @Param("qualifier1") String qualifer1,
			@Param("qualifier2") String qualifer2, @Param("qualifier3") String qualifer3,
			@Param("qualifier4") String qualifer4);

	@Query(value = "from LearnersProfileParam lp where lp.userEmail=:userEmail and lp.qualifier1=:qualifier1 and lp.qualifier2=:qualifier2 and lp.qualifier3=:qualifier3 and lp.qualifier4=:qualifier4 and lp.qualifier5=:qualifier5")
	List<LearnersProfileParam> getQValue5(@Param("userEmail") String email, @Param("qualifier1") String qualifer1,
			@Param("qualifier2") String qualifer2, @Param("qualifier3") String qualifer3,
			@Param("qualifier4") String qualifer4, @Param("qualifier5") String qualifer5);
}
