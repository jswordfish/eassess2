package com.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.Module;
import com.assessment.services.ModuleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestModule {
	
	@Autowired
	ModuleService moduleService;
	
	@Test
	@Rollback(value=false)
	public void testFindModules(){
		List<Module> mods = moduleService.findFreeModules("IH");
		System.out.println(mods.size());
	}

}
