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

import com.assessment.Exceptions.AssessmentGenericException;
import com.assessment.data.CheCluster;
import com.assessment.data.Skill;
import com.assessment.data.SkillLevel;
import com.assessment.data.User;
import com.assessment.repositories.CheClusterRepository;
import com.assessment.services.CheService;

@Controller
public class CheClusterController {
	
	@Autowired
	CheService cheService;
	
	@Autowired
	CheClusterRepository clusterRep;
	
	@RequestMapping(value = "/showClusters", method = RequestMethod.GET)
	public ModelAndView showClusters(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("clusters");
		User user = (User) request.getSession().getAttribute("user");
		List<CheCluster> cheClusters = cheService.getAllClusters(user.getCompanyId());
		mav.addObject("clusters", cheClusters);
		return mav;
	}
	
	
	@RequestMapping(value = "/addCluster", method = RequestMethod.GET)
	  public ModelAndView addCluster(@RequestParam(name= "clusterId", required = false) Long clusterId, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		 ModelAndView mav = new ModelAndView("addCluster");
		 CheCluster cluster = null;
		 	if(clusterId == null){
		 		cluster = new CheCluster();
		 		cluster.setCompanyId(user.getCompanyId());
		 		cluster.setCompanyName(user.getCompanyName());
				mav.addObject("label", "Create");
		 	}
		 	else{
		 		cluster = clusterRep.findById(clusterId).get();
		 		mav.addObject("label", "Update");
		 	}
		 mav.addObject("cluster", cluster);
		 return mav;
	}
	
	
	@RequestMapping(value = "/saveCluster", method = RequestMethod.POST)
	  public ModelAndView saveCluster(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("cluster") CheCluster cluster) {
		User user = (User) request.getSession().getAttribute("user");
		cluster.setCompanyId(user.getCompanyId());
		cluster.setCompanyName(user.getCompanyName());
		cheService.saveOrUpdate(cluster);
		List<CheCluster> cheClusters = cheService.getAllClusters(user.getCompanyId());
		ModelAndView mav = new ModelAndView("clusters");
		mav.addObject("clusters", cheClusters);
		mav.addObject("message", "Cluster Info "+cluster.getClusterName()+" saved" );// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;
	}
	
	@RequestMapping(value = "/deleteCluster", method = RequestMethod.GET)
	  public ModelAndView deleteCluster(@RequestParam(name= "clusterId", required = true) Long clusterId, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		clusterRep.deleteById(clusterId);
		List<CheCluster> cheClusters = cheService.getAllClusters(user.getCompanyId());
		ModelAndView mav = new ModelAndView("clusters");
		mav.addObject("clusters", cheClusters);
		mav.addObject("message", "Cluster deleted" );// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;
	}

}
