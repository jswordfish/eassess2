package com.assessment.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HealthCheckController {
	
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
	  public ModelAndView hackathon(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("healthcheck");
	    return mav;
	  }

}
