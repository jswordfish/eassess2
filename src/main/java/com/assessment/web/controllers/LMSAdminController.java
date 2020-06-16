package com.assessment.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.data.User;

//@Controller
public class LMSAdminController {

	
//	@GetMapping("/lmsAdminDashboard")
//	public ModelAndView gotolmsAdminDashboard(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("user_profile_index");
//		User user = (User) request.getSession().getAttribute("user");
//		System.out.println("::::::::::          "+user.getLicenses()+"                :::::::::::::              "+user.getLic());
//		mav.addObject("licenses", user.getLic());
//		
//		return mav;
//	}
}
