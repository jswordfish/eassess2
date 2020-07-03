package com.assessment.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.LearnersProfileParam;
import com.assessment.repositories.LearnersProfileParamRepository;
import com.assessment.services.LearnersProfileService;

@Service
@Transactional
public class LearnersProfileServiceImpl implements LearnersProfileService {

	@Autowired
	LearnersProfileParamRepository paramRepo;

	@Override
	public void saveOrUpdate(List<LearnersProfileParam> learners) {
//		List<LearnersProfileParam> list = paramRepo.findByuserEmail(learners.get(0).getUserEmail());

//		for (LearnersProfileParam ll : list) {
//			paramRepo.deleteById(ll.getId());
			paramRepo.deleteAll();
//		}
		for (LearnersProfileParam li : learners) {
			paramRepo.save(li);
		}
	}

}
