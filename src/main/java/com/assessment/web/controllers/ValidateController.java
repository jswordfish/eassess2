package com.assessment.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ValidateController {

	    //Fill in BlanksQ and Separated blank answers Field does not match
	@RequestMapping(value = "/errorUpload", method = RequestMethod.GET)
	public ModelAndView errorUpload() {
		ModelAndView mav = new ModelAndView("validate");
		String errormsg = "Fill in BlanksQ and Separated blank answers Field does not match";
		mav.addObject("error", errormsg);
		return mav;
	}
	// Excel DAta Not Fill
	@RequestMapping(value = "/errorUpload1", method = RequestMethod.GET)
	public ModelAndView errorUpload1() {
		ModelAndView mav = new ModelAndView("validate");
		String errormsg1 = "Excel Data Not Found";
		mav.addObject("error1", errormsg1);
		return mav;
	}
}
