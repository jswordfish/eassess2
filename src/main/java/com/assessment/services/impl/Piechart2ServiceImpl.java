package com.assessment.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.LearnersProfileParam;
import com.assessment.data.PieChart1;
import com.assessment.data.PieChart2;
import com.assessment.repositories.PieChart1Repository;
import com.assessment.repositories.PieChart2Repository;
import com.assessment.services.Piechart1Service;
import com.assessment.services.Piechart2Service;

@Service
@Transactional
public class Piechart2ServiceImpl implements Piechart2Service {

	@Autowired
	PieChart2Repository chart2Repository;

	@Override
	public void saveOrUpdate(List<PieChart2> pichart2) {
		// TODO Auto-generated method stub
		chart2Repository.deleteAll();
		for (PieChart2 li : pichart2) {
			chart2Repository.save(li);
		}
	}
}
