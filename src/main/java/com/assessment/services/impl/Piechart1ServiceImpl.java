package com.assessment.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.LearnersProfileParam;
import com.assessment.data.PieChart1;
import com.assessment.repositories.PieChart1Repository;
import com.assessment.services.Piechart1Service;

@Service
@Transactional
public class Piechart1ServiceImpl implements Piechart1Service {

	@Autowired
	PieChart1Repository chart1Repository;

	@Override
	public void saveOrUpdate(List<PieChart1> pichart1) {
		// TODO Auto-generated method stub
		chart1Repository.deleteAll();
		for (PieChart1 li : pichart1) {
			chart1Repository.save(li);
		}
	}
}
