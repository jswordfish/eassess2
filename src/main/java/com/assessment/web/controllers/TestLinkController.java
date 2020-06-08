package com.assessment.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.CommonUtil;
import com.assessment.data.Tenant;
import com.assessment.data.TestLinkTime;
import com.assessment.data.User;
import com.assessment.repositories.TestLinkTimeRepository;
import com.assessment.services.TestLinkTimeService;
import com.assessment.services.TestService;
import com.assessment.web.dto.TestLinkDTO;

@Controller
public class TestLinkController {
	@Autowired
	TestLinkTimeService testLinkService;
	
	@Autowired
	TestLinkTimeRepository testLinkRep;
	
	@Autowired
	TestService testService;

	
	@RequestMapping(value = "/listTestLinks", method = RequestMethod.GET)
	public ModelAndView listTenants(@RequestParam(name= "page", required = false) Integer pageNumber, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("list_link2");
		if(pageNumber == null) {
			pageNumber = 0;
		}
		Page<TestLinkTime> testlinks = testLinkService.findAllLinks(PageRequest.of(pageNumber, 15));
		mav.addObject("links", testlinks.getContent());
		CommonUtil.setCommonAttributesOfPagination(testlinks, mav.getModelMap(), pageNumber, "listTestLinks", null);
		return mav;
	}
	
	@RequestMapping(value = "/addTestLink", method = RequestMethod.GET)
	public ModelAndView addTestLink(@RequestParam(name= "linkId", required = false) Long linkId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView("add_link2");
		TestLinkTime testLinkTime;
		if(linkId == null) {
			testLinkTime = new TestLinkTime();
		}
		else{
			testLinkTime = testLinkRep.findById(linkId).get();
		}
		//List<TestLinkDTO> tests = testService.getAllTests(user.getCompanyId());
		List<String> tests = testService.getTestNames(user.getCompanyId());
		mav.addObject("tests", tests);
		mav.addObject("link", testLinkTime);
		return mav;
	}
	
	//deleteTestLink
	@RequestMapping(value = "/deleteTestLink", method = RequestMethod.GET)
	public ModelAndView deleteTestLink(@RequestParam(name= "linkId", required = true) Long linkId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("list_link2");
		testLinkService.deleteTestLink(linkId);
		mav.addObject("message", "Delete Successful!!!");// later put it as label
		mav.addObject("msgtype", "success");
		Integer pageNumber = 0;
		Page<TestLinkTime> testlinks = testLinkService.findAllLinks(PageRequest.of(pageNumber, 15));
		mav.addObject("links", testlinks.getContent());
		CommonUtil.setCommonAttributesOfPagination(testlinks, mav.getModelMap(), pageNumber, "listTestLinks", null);
		return mav;
	}
	
	@RequestMapping(value = "/saveTestLink", method = RequestMethod.POST)
	public ModelAndView saveTestLink(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("link") TestLinkTime link) throws Exception {
		ModelAndView mav = new ModelAndView("list_link2");
		User user = (User) request.getSession().getAttribute("user");
		
			link.setCompanyId(user.getCompanyId());
			link.setCompanyName(user.getCompanyName());
			com.assessment.data.Test test = testService.findbyTest(link.getTestName(), user.getCompanyId());
			link.setTestId(test.getId());
		testLinkService.saveOrUpdate(link);
		mav.addObject("message", "Public Test Link for test - "+link.getTestName()+"!!!");// later put it as label
		mav.addObject("msgtype", "success");
		Integer pageNumber = 0;
		Page<TestLinkTime> testlinks = testLinkService.findAllLinks(PageRequest.of(pageNumber, 15));
		mav.addObject("links", testlinks.getContent());
		CommonUtil.setCommonAttributesOfPagination(testlinks, mav.getModelMap(), pageNumber, "listTestLinks", null);
		return mav;
	}
	
}
