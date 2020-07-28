package com.assessment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.assessment.data.ProspectMessage;

public interface ProspectMessageRepository extends JpaRepository<ProspectMessage, Long> {

	
	@Query("SELECT p FROM ProspectMessage p order by p.createDate DESC")
	public List<ProspectMessage> getMessages();
	
}

