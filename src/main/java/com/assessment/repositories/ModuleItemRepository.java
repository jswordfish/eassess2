package com.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.data.ModuleItem;

public interface ModuleItemRepository extends JpaRepository<ModuleItem,Long>{

}
