package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.data.PieChart1;
import com.assessment.data.PieChart2;

public interface PieChart2Repository extends JpaRepository<PieChart2, Long> {

	List<PieChart2> findByUserEmailAndQualifier2(String email,String qualifier2);
}
