package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.data.PieChart1;

public interface PieChart1Repository extends JpaRepository<PieChart1, Long> {

	List<PieChart1> findByuserEmail(String email);
}
