package com.assessment.services;

import java.util.List;

import com.assessment.data.LearnersProfileParam;

public interface LearnersProfileService {

	void saveOrUpdate(List<LearnersProfileParam> learners);
}
