package com.assessment.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.CommonUtil;
import com.assessment.data.License;
import com.assessment.data.Module;
import com.assessment.data.User;
import com.assessment.services.LicenseService;
import com.assessment.services.ModuleService;

@Controller
public class LicenseController {
	@Autowired
	LicenseService licenseService;
	
	@Autowired
	ModuleService moduleService;
	
	@RequestMapping(value = "/licenses", method = RequestMethod.GET)
	public ModelAndView licenses(String msgType, String msg, @RequestParam(name= "page", required = false) Integer pageNumber, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndView mav = new ModelAndView("licenses");
		
		if(pageNumber == null) {
			pageNumber = 0;
		}
		
		User user = (User) request.getSession().getAttribute("user");
		Page<License> licenses = licenseService.getLicenses(user.getCompanyId(), PageRequest.of(pageNumber, 15));
		mav.addObject("licenses", licenses.getContent());
		CommonUtil.setCommonAttributesOfPagination(licenses, modelMap, pageNumber, "licenses", null);
			if(msgType != null && msg != null){
				mav.addObject("message", msg);// later put it as label
				mav.addObject("msgtype", msgType);
			}
		return mav;
	}
	
	@RequestMapping(value = "/modulesbylicense", method = RequestMethod.GET)
	public ModelAndView modulesbylicense(@RequestParam(name= "license", required = true) String license, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndView mav = new ModelAndView("modulesbylicense");
		User user = (User) request.getSession().getAttribute("user");
		List<Module> modules = moduleService.findModulesByLicense(license, user.getCompanyId());
		mav.addObject("modules", modules);
		mav.addObject("licenseName", license);
		return mav;
	}
	
	@RequestMapping(value = "/license", method = RequestMethod.GET)
	public ModelAndView addlicense(@RequestParam(name= "licenseId", required = false) Long licenseId,  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("license");
		License license = null;
			if(licenseId != null){
				license = licenseService.getById(licenseId);
				mav.addObject("licenseNameReadOnly", true);
			}
			else{
				license = new License(); 
				mav.addObject("licenseNameReadOnly", false);
			}
			request.getSession().setAttribute("license", license);
			
		
		User user = (User) request.getSession().getAttribute("user");
		
		mav.addObject("license", license);
		return mav;
	}
	
	@RequestMapping(value = "/savelicense", method = RequestMethod.POST)
	public ModelAndView savelicense(@ModelAttribute("license") License license,  HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
			if(license.getId() == null){
				//check if name is distinct and one of saved
				License lic = licenseService.findByPrimaryKey(license.getLicenseName(), user.getCompanyId());
				if(lic != null){
					return licenses("Information", "License "+license.getLicenseName()+" can not be saved. Use a different license name. ", 0, request, response, modelMap);
				}
			}
		license.setCompanyId(user.getCompanyId());
		license.setCompanyName(user.getCompanyName());
		licenseService.saveOrUpdate(license);
		return licenses("Information", "License "+license.getLicenseName()+" saved.", 0, request, response, modelMap);
	}
	
	@RequestMapping(value = "/deleteLicense", method = RequestMethod.GET)
	public ModelAndView deleteModule(@RequestParam(name= "licenseId", required = true) Long licenseId,  HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
			if(user != null){
				licenseService.deleteLicenseById(licenseId);	
				return licenses("Information", "License Deleted", 0, request, response, modelMap);
			}
		
		return licenses("Information", "License Not Deleted. Log in again. Session Expired", 0, request, response, modelMap);
	}

}
