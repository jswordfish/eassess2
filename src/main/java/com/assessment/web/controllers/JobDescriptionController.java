package com.assessment.web.controllers;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.CommonUtil;
import com.assessment.data.CandidateProfileParams;
import com.assessment.data.JobDescription;
import com.assessment.data.JobDescriptionSkill;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.User;
import com.assessment.data.UserTestSession;
import com.assessment.reports.manager.UserSkillArea;
import com.assessment.reports.manager.UserTrait;
import com.assessment.reports.manager.detailedreports.ReportManagerTrait;
import com.assessment.repositories.JobDescriptionRepository;
import com.assessment.services.CandidateProfileParamsService;
import com.assessment.services.JobDescriptionService;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.TestService;
import com.assessment.services.UserService;
import com.assessment.services.UserTestSessionService;

@Controller
public class JobDescriptionController {
	
	@Autowired
	CandidateProfileParamsService profileService;
	
	@Autowired
	JobDescriptionService jobDescService;
	
	@Autowired
	JobDescriptionRepository jobDescRep;
	
	@Autowired
	TestService testService;
	
	
	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;
	
	@Autowired
	CandidateProfileParamsService candidateProfileParamsService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserTestSessionService userTestSessionService;
	
	
	@RequestMapping(value = "/searchjobDescriptions", method = RequestMethod.GET)
	  public ModelAndView searchjobDescriptions(@RequestParam(name= "page", required = false) Integer pageNumber, @RequestParam(name= "searchText", required = true) String searchText,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
	    ModelAndView mav = new ModelAndView("jobdescs");
	    User user = (User) request.getSession().getAttribute("user");
	    if(pageNumber == null) {
			pageNumber = 0;
		}
		//Page<Question> questions = questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<JobDescription> descs = jobDescRep.searchJobDescriptions(user.getCompanyId(), searchText, PageRequest.of(pageNumber, 20));
		mav.addObject("descs", descs.getContent());
		
		CommonUtil.setCommonAttributesOfPagination(descs, modelMap, pageNumber, "searchjobDescriptions", null);
		return mav;
	  }
	
	
	
	
	@RequestMapping(value = "/showjobDescriptions", method = RequestMethod.GET)
	  public ModelAndView showjobDescriptions(@RequestParam(name= "page", required = false) Integer pageNumber, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
	    ModelAndView mav = new ModelAndView("jobdescs");
	    User user = (User) request.getSession().getAttribute("user");
	    if(pageNumber == null) {
			pageNumber = 0;
		}
		//Page<Question> questions = questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<JobDescription> descs = jobDescService.findByCompanyId(user.getCompanyId(), PageRequest.of(pageNumber, 20));
		mav.addObject("descs", descs.getContent());
		
		CommonUtil.setCommonAttributesOfPagination(descs, modelMap, pageNumber, "showjobDescriptions", null);
		return mav;
	  }
	
	
	@RequestMapping(value = "/addJobDesc", method = RequestMethod.GET)
	  public ModelAndView addJobDesc(@RequestParam(required=false) Long jid, HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("jobdesc");
	    User user = (User) request.getSession().getAttribute("user");
	    JobDescription jobDescription;
	    if(jid != null){
	    	jobDescription = jobDescRep.findById(jid).get();
	    }
	    else{
	    	jobDescription = new JobDescription();
	    }
	    
	    List<CandidateProfileParams> params = profileService.findCandidateProfileParamsByCompanyId(user.getCompanyId());
	    
	    for(CandidateProfileParams profileParams : params){
	    	for(JobDescriptionSkill skill: jobDescription.getSkills()){
	    		if(profileParams.hashCode() == skill.hashCode()){
	    			profileParams.setPresent(true);
	    			profileParams.setWeight(skill.getWeight());
	    		}
	    	}
	    	
	    }
	    Collections.sort(params, new Comparator<CandidateProfileParams>() {
	
			@Override
			public int compare(CandidateProfileParams o1, CandidateProfileParams o2) {
				// TODO Auto-generated method stub
				if(o1.getPresent() && o2.getPresent()){
					return 10000;
				}
				return o1.getQualifier1().compareTo(o2.getQualifier1());
			}
		});
	    jobDescription.setParams(params);
	    mav.addObject("jobDescription", jobDescription);
	  //List<TestLinkDTO> tests = testService.getAllTests(user.getCompanyId());
		List<String> tests = testService.getTestNames(user.getCompanyId());
		mav.addObject("tests", tests);
	    
	    return mav;
	  }
	
	@RequestMapping(value = "/saveJobdesc", method = RequestMethod.POST)
	public ModelAndView saveJobdesc(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("jobDescription") JobDescription jobDescription, ModelMap modelMap) {
	  ModelAndView mav = new ModelAndView("jobdescs");
	  User user = (User) request.getSession().getAttribute("user");
	  List<CandidateProfileParams> params = jobDescription.getParams();
	  jobDescription.getSkills().clear();
	  jobDescription.setCompanyId(user.getCompanyId());
	  jobDescription.setCompanyName(user.getCompanyName());
	  for(CandidateProfileParams profileParams : params){
		  	if(profileParams.getPresent()){
		  		JobDescriptionSkill skill = new JobDescriptionSkill(profileParams.getQualifier1(), profileParams.getQualifier2(), profileParams.getQualifier3(), profileParams.getQualifier4(), profileParams.getQualifier5());
		  		skill.setWeight(profileParams.getWeight());
		  		jobDescription.getSkills().add(skill);
		  		skill.setCompanyId(user.getCompanyId());
		  		skill.setCompanyName(user.getCompanyName());
		  	}
	  }
	  jobDescService.saveOrUpdate(jobDescription);
	  
	  Page<JobDescription> descs = jobDescService.findByCompanyId(user.getCompanyId(), PageRequest.of(0, 20));
	  mav.addObject("descs", descs.getContent());
	  String name = jobDescription.getName().replaceAll("[\"']", "");
	  mav.addObject("message", "Job Description "+name+" saved!!!");// later put it as label
	  mav.addObject("msgtype", "Information");
	  CommonUtil.setCommonAttributesOfPagination(descs, modelMap, 1, "showjobDescriptions", null);
	  return mav;
	}
		
	@RequestMapping(value = "/deleteJobdesc", method = RequestMethod.GET)
	public ModelAndView deleteJobdesc(@RequestParam(name= "jid", required = true) Long jid, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("jobDescription") JobDescription jobDescription, ModelMap modelMap) {
	  ModelAndView mav = new ModelAndView("jobdescs");
	  User user = (User) request.getSession().getAttribute("user");
	 
	  
	  Page<JobDescription> descs = jobDescService.findByCompanyId(user.getCompanyId(), PageRequest.of(0, 20));
	  mav.addObject("descs", descs.getContent());
	  String name = jobDescription.getName().replaceAll("[\"']", "");
	  mav.addObject("message", "Job Description "+name);// later put it as label
	  mav.addObject("msgtype", "Information");
	  CommonUtil.setCommonAttributesOfPagination(descs, modelMap, 1, "showjobDescriptions", null);
	  return mav;
	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public @ResponseBody  String verify( @RequestParam(name= "email", required = true) String email, @RequestParam(name= "jid", required = true) Long jid, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("jobDescription") JobDescription jobDescription, ModelMap modelMap) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		JobDescription description = jobDescRep.findById(jid).get();
		UserTestSession session =  userTestSessionService.findUserTestSession(email, description.getTestName(), user.getCompanyId());
		if(session == null){
			return "not ok";
		}
		else{
			return "ok";
		}
	}

	@RequestMapping(value = "/downloadTestSpecificReport", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadTestSpecificReport( @RequestParam(name= "email", required = true) String email, @RequestParam(name= "jid", required = true) Long jid, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("jobDescription") JobDescription jobDescription, ModelMap modelMap) throws Exception {
	  User user = (User) request.getSession().getAttribute("user");
	  JobDescription description = jobDescRep.findById(jid).get();
	  String file = generatedetailedReportForCompositeTest(jid, user.getCompanyId(), description.getTestName(), email);
	  byte[] data = FileUtils.readFileToByteArray(new File(file));
	  org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
	    headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
	    // Here you have to set the actual filename of your pdf
	    String filename = "output.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response2 = new ResponseEntity<>(data, headers, HttpStatus.OK);
	    return response2;
	}
	
	
	private String generatedetailedReportForCompositeTest(Long jid, String companyId, String testName, String user) throws Exception{
		
		JobDescription description = jobDescRep.findById(jid).get();
		List<CandidateProfileParams> candidateProfileParams = new ArrayList<>();
		Map<CandidateProfileParams, Integer> temp_weight = new HashMap<>();
		for(JobDescriptionSkill skill : description.getSkills()){
			CandidateProfileParams param = candidateProfileParamsService.findUniqueCandidateProfileParams(companyId, skill.getQualifier1(), skill.getQualifier2(), skill.getQualifier3(), skill.getQualifier4(), skill.getQualifier5());
			param.setWeight(skill.getWeight());
			candidateProfileParams.add(param);
			temp_weight.put(param, skill.getWeight()==null?1:skill.getWeight());
		}
		List<QuestionMapperInstance> answers = questionMapperInstanceService.findQuestionMapperInstancesForUserForTest(testName, user, companyId);
		Map<CandidateProfileParams, List<QuestionMapperInstance>> map = new HashMap<>();
		for(QuestionMapperInstance ans : answers){
			CandidateProfileParams param = new CandidateProfileParams(ans.getQuestionMapper().getQuestion().getQualifier1(), ans.getQuestionMapper().getQuestion().getQualifier2(), ans.getQuestionMapper().getQuestion().getQualifier3(), ans.getQuestionMapper().getQuestion().getQualifier4(), ans.getQuestionMapper().getQuestion().getQualifier5()); 
				if(candidateProfileParams.contains(param)){
					if(map.get(param) == null){
						List<QuestionMapperInstance> ins = new ArrayList<>();
						ins.add(ans);
						param.setWeight(temp_weight.get(param));
						map.put(param, ins);
					}
					else{
						map.get(param).add(ans);
					}
					
				}
			
			
		}
		DecimalFormat df = new DecimalFormat("#.##");
		Map<CandidateProfileParams,Float> mapPer = new HashMap<>();
		Map<CandidateProfileParams, String> mapTrait = new HashMap<>();
		
		Integer totalWeight = 0;
		Float totalScore = 0f;
		Float totalAverageScore = 0f;
		Integer totalSkillCount = 0;//for average
		for(CandidateProfileParams param : map.keySet()){
			List<QuestionMapperInstance> answersForQualifier = map.get(param);
			int noOfCorrect = 0;
			for(QuestionMapperInstance ans :answersForQualifier ){
				if(ans.getCorrect()){
					noOfCorrect++;
				}
			}
			
			Float percentForSkill = (float) (100 * noOfCorrect / answersForQualifier.size());
			totalWeight += param.getWeight()==null?1:param.getWeight();
			totalScore += percentForSkill * (param.getWeight()==null?1:param.getWeight());
			
			totalSkillCount ++;
			totalAverageScore += percentForSkill;
			
			Float percent = Float.parseFloat(df.format(noOfCorrect * 100 / answersForQualifier.size()));
			mapPer.put(param, percent);
			int index = candidateProfileParams.indexOf(param);
				if(index != -1){
					CandidateProfileParams paramWithData = candidateProfileParams.get(index);
					String trait = "";
					if(percent < 20){
						trait = paramWithData.getLESS_THAN_TWENTY_PERCENT();
					}
					else if(percent >= 20 && percent < 50){
						trait = paramWithData.getBETWEEN_TWENTY_AND_FIFTY();
					}
					else if(percent >= 50 && percent < 75){
						trait = paramWithData.getBETWEEN_FIFTY_AND_SEVENTYFIVE();
					}
					else if(percent >= 75 && percent < 90){
						trait = paramWithData.getBETWEEN_SEVENTYFIVE_AND_NINETY();
					}
					else if(percent > 90){
						trait = paramWithData.getMORE_THAN_NINETY();
					}
					mapTrait.put(param, trait);
				}
			
		}
		Float weightedScoreForTest = totalScore / totalWeight;
		Float averageScoreForTest = totalAverageScore / totalSkillCount;
				
				
		List<UserTrait> traits = new ArrayList<>();
		for(CandidateProfileParams param : mapTrait.keySet()){
			UserTrait trait = new UserTrait();
			String qual = param.getQualifier1();
			if(param.getQualifier2()!= null && !param.getQualifier2().equals("NA")){
				qual += "-"+param.getQualifier2();
			}
			if(param.getQualifier3()!= null && !param.getQualifier3().equals("NA")){
				qual += "-"+param.getQualifier3();
			}
			if(param.getQualifier4()!= null && !param.getQualifier4().equals("NA")){
				qual += "-"+param.getQualifier4();
			}
			if(param.getQualifier5()!= null && !param.getQualifier5().equals("NA")){
				qual += "-"+param.getQualifier5();
			}
			
			trait.setTrait(qual);
			trait.setDescription(mapTrait.get(param));
			traits.add(trait);
		}
		
		List<UserSkillArea> skillAreas = new ArrayList<>();
			for(CandidateProfileParams param : mapPer.keySet()){
				UserSkillArea area = new UserSkillArea();
				String qual = param.getQualifier1();
				if(param.getQualifier2()!= null && !param.getQualifier2().equals("NA")){
					qual += "-"+param.getQualifier2();
				}
				if(param.getQualifier3()!= null && !param.getQualifier3().equals("NA")){
					qual += "-"+param.getQualifier3();
				}
				if(param.getQualifier4()!= null && !param.getQualifier4().equals("NA")){
					qual += "-"+param.getQualifier4();
				}
				if(param.getQualifier5()!= null && !param.getQualifier5().equals("NA")){
					qual += "-"+param.getQualifier5();
				}
				area.setSkillarea(qual);
				Float percent = mapPer.get(param);
				area.setPercentage(percent);
				skillAreas.add(area);
			}
		User usr = 	userService.findByPrimaryKey(user, companyId);
		ReportManagerTrait managerTrait = new ReportManagerTrait();
		String fileName = managerTrait.buildComprehensiveReport(traits, skillAreas, testName, usr.getFirstName()+" "+usr.getLastName(), ""+averageScoreForTest, ""+weightedScoreForTest);//this should not be hard coded
		return fileName;
	}

}
