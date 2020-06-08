package com.assessment.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.data.Question;
import com.assessment.data.TestDomainMapping;
import com.assessment.data.User;
import com.assessment.services.TestDomainMappingService;
import com.assessment.services.TestService;

@Controller
public class TestDomainMappingController {
	
	@Autowired
	TestDomainMappingService domainMappingService;
	
	@Autowired
	TestService testService;
	
	@RequestMapping(value = "/listDomainMappings", method = RequestMethod.GET)
	public ModelAndView listDomainMappings(@RequestParam(name= "page", required = false) Integer pageNumber,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("test_domainmapping");
		User user = (User) request.getSession().getAttribute("user");
		List<TestDomainMapping> list =  getAllTestDomainMappingForCompany(user.getCompanyId());
		mav.addObject("list", list);
		return mav;
	}
	
	
	@RequestMapping(value = "/addMapping", method = RequestMethod.GET)
	public ModelAndView addMapping(@RequestParam(name= "did", required= false) Long did,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("test_domainmapping_add");
		User user = (User) request.getSession().getAttribute("user");
		TestDomainMapping domainMapping;
		if(did == null){
			domainMapping = new  TestDomainMapping();
		}
		else{
			domainMapping = domainMappingService.getById(did);
		}
		List<String> testNames = testService.getTestNames(user.getCompanyId());
		mav.addObject("mapping", domainMapping);
		mav.addObject("testNames", testNames);
		return mav;
	}
	
	@RequestMapping(value = "/removedid", method = RequestMethod.GET)
	public ModelAndView removedid(@RequestParam(name= "did", required= false) Long did,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("test_domainmapping");
		User user = (User) request.getSession().getAttribute("user");
		TestDomainMapping domainMapping = new TestDomainMapping();
		domainMapping.setId(did);
		domainMappingService.removeById(domainMapping);
		List<TestDomainMapping> list =  getAllTestDomainMappingForCompany(user.getCompanyId());
		mav.addObject("list", list);
		mav.addObject("message", "Test Domain Mapping Remove Success");// later put it as label
		mav.addObject("msgtype", "Success");
		return mav;
	}
	
	@RequestMapping(value = "/saveDomainMapping", method = RequestMethod.POST)
	public ModelAndView saveDomainMapping(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("mapping") TestDomainMapping mapping) throws Exception {
		ModelAndView mav = new ModelAndView("test_domainmapping");
		User user = (User) request.getSession().getAttribute("user");
		mapping.setCompanyId(user.getCompanyId());
		mapping.setCompanyName(user.getCompanyName());
		domainMappingService.saveOrUpdate(mapping);
		mav.addObject("message", "Test Domain Mapping Save Success");// later put it as label
		mav.addObject("msgtype", "Success");
		List<TestDomainMapping> list =  getAllTestDomainMappingForCompany(user.getCompanyId());
		mav.addObject("list", list);
		return mav;
	}
	
	public List<TestDomainMapping> getAllTestDomainMappingForCompany(String companyId){
		return domainMappingService.getByCompany(companyId);
	}

}
