package com.assessment.web.controllers;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.AssessmentGenericException;
import com.assessment.common.CompileData;
import com.assessment.common.CompileOutput;
import com.assessment.common.LanguageCodes;
import com.assessment.common.PropertyConfig;
import com.assessment.common.QuestionSequence;
import com.assessment.common.SectionSequence;
import com.assessment.common.util.EmailGenericMessageThread;
import com.assessment.common.util.FullStackWorkspaceJsonTemplate;
import com.assessment.data.FullStackOptions;
import com.assessment.data.ProgrammingLanguage;
import com.assessment.data.Question;
import com.assessment.data.QuestionMapper;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.QuestionType;
import com.assessment.data.Section;
import com.assessment.data.SectionInstance;
import com.assessment.data.Test;
import com.assessment.data.TestDomainMapping;
import com.assessment.data.TestLinkTime;
import com.assessment.data.User;
import com.assessment.data.UserNonCompliance;
import com.assessment.data.UserTestSession;
import com.assessment.data.UserTestTimeCounter;
import com.assessment.eclipseche.config.response.WorkspaceResponse;
import com.assessment.eclipseche.services.EclipseCheService;
import com.assessment.reports.manager.UserTrait;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.QuestionMapperRepository;
import com.assessment.repositories.TestLinkTimeRepository;
import com.assessment.repositories.UserTestSessionRepository;
import com.assessment.services.CheService;
import com.assessment.services.CompanyService;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.QuestionMapperService;
import com.assessment.services.SQLCodingAutomationService;
import com.assessment.services.SectionInstanceService;
import com.assessment.services.SectionService;
import com.assessment.services.StudentService;
import com.assessment.services.TestDomainMappingService;
import com.assessment.services.TestService;
import com.assessment.services.UserNonComplianceService;
import com.assessment.services.UserService;
import com.assessment.services.UserTestSessionService;
import com.assessment.services.UserTestTimeCounterService;
import com.assessment.services.impl.CompilerService;
import com.assessment.services.impl.ReportsService;
import com.assessment.web.dto.MTFdto;
import com.assessment.web.dto.QuestionInstanceDto;
import com.assessment.web.dto.SectionInstanceDto;
import com.assessment.web.forms.StudentTestForm;


@Controller
public class StudentController {
	
	//Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TestService testService;
	
	@Autowired
	SectionService sectionService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	QuestionMapperService questionMapperService;
	
	@Autowired
	SectionInstanceService sectionInstanceService;
	
	@Autowired
	QuestionMapperInstanceService questionMapperInstaceService;
	
	@Autowired
	UserTestSessionService userTestSessionService;
	
	@Autowired
	PropertyConfig propertyConfig;
	
	@Autowired
	UserNonComplianceService userNonComplianceService;
	
	@Autowired
	CompilerService compiler;
	
	@Autowired
	ReportsService reportsService;
	@Autowired
	QuestionMapperInstanceRepository  questionMapperInstanceRep;
	
	@Autowired
	UserTestSessionRepository testSessionRepository;
	
	@Autowired
	QuestionMapperInstanceRepository questionMapperInstanceRepository;
	
	@Autowired
	QuestionMapperRepository questionMapperRep;
	
	@Autowired
	UserTestTimeCounterService counterService;
	
	@Autowired
	SQLCodingAutomationService automationService;
	
	@Autowired
	TestDomainMappingService domainMappingService;
	
	@Autowired
	CheService cheService;
	
	
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	public String getUserAfterCheckingNoOfAttempts(String user, String companyId, Test test, HttpServletRequest request){
		UserTestSession session = testSessionRepository.findByPrimaryKey(user, test.getTestName(), test.getCompanyId());
		  String userNameNew = "";
		  if(session == null){
			  userNameNew = user;
			  request.getSession().setAttribute("noOfAttempts", 0);
			  return userNameNew;
		  }
		  else{
			  /**
			   * Step 2 - find out how many sessions for the given test the user has taken
			   */
			  List<UserTestSession> sessions = testSessionRepository.findByTestNamePart(user+"["+test.getId(), test.getTestName(), test.getCompanyId());
			  //int noOfConfAttempts = test.getNoOfConfigurableAttempts() ==null?50:test.getNoOfConfigurableAttempts();
			  /**
			   * Check whether test is configured with a domain having configurable no of attempts
			   */
			  String domain = user.substring(user.indexOf("@")+1, user.length());
			  int noOfConfAttempts = getNoOfConfigurableAttempts(test, domain);
			  /**
			   * End above check
			   */
			  	if(noOfConfAttempts <= (sessions.size()+1)){
			  		return "fail";
			  	}
			  
			  userNameNew = user+"["+test.getId()+"-"+(sessions.size()+1+"]");
			  request.getSession().setAttribute("noOfAttempts", (sessions.size()+1));
			  return userNameNew;
		  }
	}
	
	private Integer getNoOfConfigurableAttempts(Test test, String domain){
		TestDomainMapping domainMapping = domainMappingService.findByPrimaryKey(test.getCompanyId(), test.getTestName(), domain);
			if(domainMapping != null){
				return domainMapping.getNoOfAttempts();
			}
		int noOfConfAttempts = test.getNoOfConfigurableAttempts() ==null?50:test.getNoOfConfigurableAttempts();
		return noOfConfAttempts;  
	}
	
//	@RequestMapping(value = "/startTestSession", method = RequestMethod.GET)
//    public ModelAndView studentHome(@RequestParam String startDate,@RequestParam String endDate,@RequestParam(required = false) String sharedDirect,
//@RequestParam(required = false) String inviteSent,
//                    @RequestParam String userId, @RequestParam String companyId,
//@RequestParam String testId,
//                    HttpServletRequest request, HttpServletResponse response) {
//            ModelAndView mav = new ModelAndView("intro2");
//            mav.addObject("startTime", startDate);
//            mav.addObject("endTime", endDate);
//            mav.addObject("inviteSent", inviteSent);
//            mav.addObject("companyId", companyId);
//            mav.addObject("testId", testId);
//            mav.addObject("sharedDirect", sharedDirect);
//            mav.addObject("inviteSent", inviteSent);
//            mav.addObject("userId", userId);
//            return mav;
//    }
	
	 @RequestMapping(value = "/startTestSession", method = RequestMethod.GET)
	  public ModelAndView studentHome2(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required=false) String sharedDirect,@RequestParam(required=false) String inviteSent, @RequestParam String userId, @RequestParam String companyId, @RequestParam String testId, HttpServletRequest request, HttpServletResponse response) {
		 /**
		  * Add code begin for test link time verification
		  */
		 ModelAndView mav;
		 
		    if(startDate == null || endDate == null){
		    	mav = new ModelAndView("testLinkNotEnabled");
		    	mav.addObject("message", "This is a old test link and no longer used. Contact Test Admin to get a new test link.");
		    	return mav;
		    }
		    
		   // String url = propertyConfig.getBaseUrl()+"publicTest?"+request.getQueryString();
		   //TestLinkTime linkTime = linkTimeRepository.findUniquetestLink(companyId, url);
		  //  Boolean dontCheckTimeValidity = (linkTime==null?false:linkTime.getDontCheckTimeValidity());
		    Boolean dontCheckTimeValidity = (Boolean) request.getSession().getAttribute("dontCheckTimeValidity");
		    dontCheckTimeValidity = (dontCheckTimeValidity==null)?false:dontCheckTimeValidity;
	    	if(!dontCheckTimeValidity){
	    		startDate = URLDecoder.decode(startDate);
			    endDate = URLDecoder.decode(endDate);
			    startDate = new String(Base64.getDecoder().decode(startDate.getBytes()));
			    endDate = new String(Base64.getDecoder().decode(endDate.getBytes()));
			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
			    try {
					Date sDate = new Date(Long.parseLong(startDate));
					Date eDate = new Date(Long.parseLong(endDate));
					Long now = System.currentTimeMillis();
					Long start = sDate.getTime();
					Long end = eDate.getTime();
					String view = "testLinkNotEnabled";
					String message = "";
					Boolean inactive = false;
					if(start > now){
						message = "Link is not yet active. It will be activated at "+dateFormat.format(sDate)+". Try later.";
						inactive = true;
					}
					if(now > end){
						message = "Link has expired at "+dateFormat.format(eDate)+". Contact Test Admin.";
						inactive = true;
					}
					
					if(inactive){
						mav = new ModelAndView(view);
						mav.addObject("message", message);
						return mav;
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					mav = new ModelAndView("testLinkNotEnabled");
			    	mav.addObject("message", "This is a invalid test link. Make sure you have correctly copied it. Contact Test Admin to get more help if it still doesn't work.");
			    	return mav;
				}
			 /**
			  * Add Code end for test link time verification
			  */
	    	}
	    	request.getSession().setAttribute("dontCheckTimeValidity", null);   
		 
		 
		 StudentTestForm studentTest= new StudentTestForm();
		 userId=decodeUserId((String)request.getParameter("userId"));
		 companyId=(String)request.getParameter("companyId");
		// ModelAndView model= new ModelAndView("intro");
		 ModelAndView model= new ModelAndView("intro");
		 SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
	     String time = localDateFormat.format(new Date());
	     studentTest.setCurrentTime(time);
	     User userDetails=userService.findByPrimaryKey(userId, companyId);
	     request.getSession().setAttribute("user", userDetails);
	     	if(inviteSent != null) {
	     		Long timeInvite = Long.valueOf(inviteSent);
	     		Date date = new Date(timeInvite);
	     		studentTest.setTestInviteSent(date);
	     	}
	     	
	     	if(sharedDirect != null && sharedDirect.equalsIgnoreCase("yes")) {
	     		studentTest.setSharedDirect(true);
	     	}
	     
		// User userDetails=userService.findUserById(Long.parseLong(userId),companyId);
		if(userDetails!=null)
		{
			Test testDetails=testService.findTestById(Long.parseLong(testId),companyId);
			/**
			 * Get no of attempts for the email id and make configurabled attempts work for test givers
			 */
			String email = "";
			 	if(userDetails.getEmail().lastIndexOf("[") > 0 ){
			 		email = userDetails.getEmail().substring(0, userDetails.getEmail().lastIndexOf("["));
			 	}
			 	else{
			 		email = userDetails.getEmail();
			 	}
			UserTestSession session2 = testSessionRepository.findByPrimaryKey(email, testDetails.getTestName(), testDetails.getCompanyId());
				if(session2 != null){
					email =  getUserAfterCheckingNoOfAttempts(email, testDetails.getCompanyId(), testDetails, request);
					if(email.equals("fail")){
						mav = new ModelAndView("studentNoTest_ExceededAttempts");
				  		mav.addObject("firstName", userDetails.getFirstName());
				  		mav.addObject("lastName", userDetails.getLastName());
				  		mav.addObject("attempts", testDetails.getNoOfConfigurableAttempts() == null?50:testDetails.getNoOfConfigurableAttempts());
				  		return mav;
					}
					else{
						userDetails.setEmail(email);
						userDetails.setId(null);
						userService.saveOrUpdate(userDetails);
						request.getSession().setAttribute("user", userDetails);
					}
				}
				userDetails.setEmail(email);
			/**
			 * End code put to check configurabled attempts work for test givers who are send private test links through email
			 */
				
			
			studentTest.setUserName(userDetails.getFirstName()+" "+userDetails.getLastName());
			studentTest.setEmailId(userDetails.getEmail());
			testId=(String)request.getParameter("testId");
			
			/**
			 * Add code for image logo mapping
			 */
			if(testDetails.getTestType() == null){
				studentTest.setTechLogo("https://yaksha.online/images/Java.png");
			}
			
			if(testDetails.getTestType().equals("Java")){
				studentTest.setTechLogo("https://yaksha.online/images/Java.png");
			}
			else if(testDetails.getTestType().equals("Microsoft technologies")){
				studentTest.setTechLogo("https://yaksha.online/images/Microsoft.png");
			}
			else if(testDetails.getTestType().equals("C/C++")){
				studentTest.setTechLogo("https://yaksha.online/images/C.png");
			}
			else if(testDetails.getTestType().equals("Python")){
				studentTest.setTechLogo("https://yaksha.online/images/Python.png");
			}
			else if(testDetails.getTestType().equals("Python")){
				studentTest.setTechLogo("https://yaksha.online/images/C.png");
			}
			else if(testDetails.getTestType().equals("General Knowledge")){
				studentTest.setTechLogo("https://yaksha.online/images/GK.png");
			}
			else if(testDetails.getTestType().equals("Composite Test")){
				studentTest.setTechLogo("https://yaksha.online/images/All_In_1.png");
			}
			else{
				studentTest.setTechLogo("https://yaksha.online/images/All_In_1.png");
			}
			//
			
			/**
			 * End code for image logo mapping
			 */
			
			
			User createTestUser = userService.findByPrimaryKey(testDetails.getCreatedBy(), companyId);
			studentTest.setTestCreatorName(createTestUser.getFirstName()+" "+createTestUser.getLastName());
			request.getSession().setAttribute("test", testDetails);
			//List<Section> sections = sectionService.getSectionsForTest(testDetails.getTestName(),companyId);
			studentTest.setTestCreatedBy(testDetails.getCreatedBy());
			
				if(testDetails.getTotalMarks() == null){
					model= new ModelAndView("studentMessageTest_WithNoQs");
					model.addObject("studentTestForm", studentTest);
					return model;
				}
			int questionsCountInAllSections=testDetails.getTotalMarks();
			int allQuestionsTimeInMin=0;
//			if(sections.size()>0)
//			{
//				for (Section section : sections) {
//					List<QuestionMapper> questionMappers = questionMapperService.getQuestionsForSection(testDetails.getTestName(), section.getSectionName(), companyId);
//					questionsCountInAllSections+=questionMappers.size();
//					//allQuestionsTimeInMin+=section.getSectionTimeInMinutes()==null?30:section.getSectionTimeInMinutes();
//				}
//			}
//			
			
			if(testDetails.getTestTimeInMinutes() == null || testDetails.getTestTimeInMinutes() == 0) {
				allQuestionsTimeInMin = 45;
			}
			else {
				allQuestionsTimeInMin = testDetails.getTestTimeInMinutes();
			}
			
			studentTest.setCompanyId(companyId);
			studentTest.setEmailId(userDetails.getEmail());	
			studentTest.setTestName(testDetails.getTestName());
			studentTest.setTotalQuestions(questionsCountInAllSections);
			studentTest.setDuration(allQuestionsTimeInMin);
			studentTest.setPublishedDate(testDetails.getCreateDate());
			studentTest.setFirstName(userDetails.getFirstName());
			studentTest.setLastName(userDetails.getLastName());
			studentTest.setTestCreatedBy(testDetails.getCreatedBy());
			String pattern = "dd-MM-yyyy HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			studentTest.setTestCreationDate(simpleDateFormat.format(testDetails.getCreateDate()));
			//Integer noOfAttempts = userTestSessionService.findNoOfAttempsByUserForTest(userDetails.getEmail(), testDetails.getTestName(), userDetails.getCompanyId());
		//	studentTest.setNoOfAttempts(noOfAttempts == null || noOfAttempts == 0 ?1:noOfAttempts +1);
			UserTestSession session = userTestSessionService.findUserTestSession(userDetails.getEmail(), testDetails.getTestName(), userDetails.getCompanyId());
			if(session != null && session.getComplete()) {
				
				studentTest.setLastUpdated(simpleDateFormat.format(session.getUpdateDate() == null?session.getCreateDate():session.getUpdateDate()));
				studentTest.setNoOfAttempts(session.getNoOfAttempts());
				model= new ModelAndView("studentNoTest");
				model.addObject("studentTestForm", studentTest);
				return model;
			}
			else if(session != null && !session.getComplete()) {
				studentTest.setNoOfAttempts(session.getNoOfAttempts());
			}
			else if(session == null){
				studentTest.setNoOfAttempts(1);
				if(!userDetails.getEmail().contains("[")){
					request.getSession().setAttribute("noOfAttempts", 0);
				}
				
			}
			studentTest.setNoOfAttemptsAvailable(testDetails.getNoOfConfigurableAttempts() ==null?50:testDetails.getNoOfConfigurableAttempts());
		}
		
		
		model.addObject("studentTestForm", studentTest);
		request.getSession().setAttribute("studentTestForm", studentTest);
		model.addObject("userName", userDetails.getFirstName()+" "+userDetails.getLastName());
		putMiscellaneousInfoInModel(model, request);
		return model;
	  }
	 
	 private void setTimeInCounter(HttpServletRequest request, Long timeElapsed) {
		 StudentTestForm studentTest = (StudentTestForm) request.getSession().getAttribute("studentTestForm");
		 studentTest.setTotalTestTimeElapsedInSeconds(studentTest.getTotalTestTimeElapsedInSeconds() + timeElapsed);
	 }
	 
	 private void putMiscellaneousInfoInModel(ModelAndView model, HttpServletRequest request) {
		 StudentTestForm studentTest = (StudentTestForm) request.getSession().getAttribute("studentTestForm");
		 model.addObject("studentTestForm", studentTest);
		 /**
		 	 * Add the time counter part - retrieved time counter for sessions that were disrupted.
		 	 */
		 Test test = (Test) request.getSession().getAttribute("test");
		 	UserTestTimeCounter counter = counterService.findByPrimaryKey(test.getId(), studentTest.getEmailId(), studentTest.getCompanyId());
		 	if(counter == null){
		 		model.addObject("timeCounter", new Long(0));
		 	}
		 	else{
		 		model.addObject("timeCounter", counter.getTimeCounter());
		 	}
	 }
	
	 
	 @RequestMapping(value = "/timecounterUpdate", method = RequestMethod.POST)
	 public @ResponseBody String timecounterUpdate(@RequestParam Long timecounter,  @RequestParam Long testId, @RequestParam String companyId, @RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
		 UserTestTimeCounter counter = new UserTestTimeCounter();
		 counter.setCompanyId(companyId);
		 counter.setEmail(email);
		 counter.setTestId(testId);
		 counter.setTimeCounter(timecounter);
		 
		 Test test = (Test) request.getSession().getAttribute("test");
		 counter.setTestName(test.getTestName());
		 counter.setCompanyName(test.getCompanyName());
		 counterService.saveOrUpdate(counter);
		 return "ok";
	 }
 
	 
	 @RequestMapping(value = "/studentJourney", method = RequestMethod.POST)
	 public ModelAndView studentStartExam(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("studentTestForm") StudentTestForm studentForm) throws Exception {
		// ModelAndView model= new ModelAndView("test_cognizant");
		 System.out.println("new changes");
		 ModelAndView model;
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 	if(test.getFullStackTest() != null && test.getFullStackTest()){
		 		//model= new ModelAndView("test_fstk");
		 		model= new ModelAndView("test_fstk_new");
		 	}
		 	else{
		 		model= new ModelAndView("test_cognizant");
//		 		model = new ModelAndView("test_new");
		 	}
		 request.getSession().setAttribute("testStartDate", new Date());
		 List<Section> sections = sectionService.getSectionsForTest(test.getTestName(),test.getCompanyId());
		
		 int count = 0;
		 List<SectionInstanceDto> sectionInstanceDtos = new ArrayList<>();
		 int totalQuestions = test.getTotalMarks();
		 for (Section section : sections) {
			 //from the sections creating an instance of section mapping with test
			 SectionInstanceDto sectionInstanceDto = new SectionInstanceDto();
			 sectionInstanceDtos.add(sectionInstanceDto);
			// sectionInstanceDto.setCurrent(current);
			 if(count == 0) {
				 sectionInstanceDto.setCurrent(true);
				 
				 List<QuestionMapper> questionMappers = questionMapperService.getQuestionsForSection(test.getTestName(), section.getSectionName(), user.getCompanyId());
				 //Collections.shuffle(questionMappers);
				 List<QuestionMapper> questionMappersActual = questionMappers.subList(0, section.getNoOfQuestionsToBeAsked());
				 Collections.shuffle(questionMappersActual);
					List<QuestionInstanceDto> questionMapperInstances=new ArrayList<QuestionInstanceDto>();
					int pos = 0;
					for (QuestionMapper questionMapper : questionMappersActual) {
						//creating the instances of question mapper instance entity
						QuestionInstanceDto questionInstanceDto= new QuestionInstanceDto();
						pos++;
						questionInstanceDto.setPosition(pos);
						QuestionMapperInstance questionMapperInstance = null;
						if(section.getPercentQuestionsAsked() == 100){
							questionMapperInstance = questionMapperInstaceService.removeDublicateAndGetInstance(questionMapper.getQuestion().getQuestionText(), test.getTestName(), section.getSectionName(), user.getEmail(), user.getCompanyId());

//							try {
//								questionMapperInstance = 	questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUser(questionMapper.getQuestion().getQuestionText(), test.getTestName(), section.getSectionName(), user.getEmail(), user.getCompanyId());
//							} catch (javax.persistence.NonUniqueResultException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								logger.error("duplicate anss for "+questionMapper.getQuestion().getQuestionText()+"-"+ test.getTestName()+"-"+  section.getSectionName()+"-"+  user.getEmail()+"-"+  user.getCompanyId());
//								List<QuestionMapperInstance> qms = questionMapperInstanceRepository.findDuplicateQuestionMapperInstanceForUser(questionMapper.getQuestion().getQuestionText(), test.getTestName(), section.getSectionName(), user.getEmail(), user.getCompanyId());
//								for(QuestionMapperInstance q : qms){
//									questionMapperInstanceRepository.delete(q);
//								}
//							}
						}
						else{
							questionMapperInstance = questionMapperInstaceService.removeDublicateAndGetInstance(questionMapper.getQuestion().getQuestionText(), test.getTestName(), section.getSectionName(), user.getEmail(), user.getCompanyId());
						}
						
						if(questionMapperInstance == null){
							questionMapperInstance = new QuestionMapperInstance();
							questionMapperInstance.setQuestionMapper(questionMapper);
						}
						
						questionInstanceDto.setQuestionMapperInstance(questionMapperInstance);
						
						
						questionMapperInstance.setQuestionMapper(questionMapper);
						questionMapperInstances.add(questionInstanceDto);
						if(questionMapper.getQuestion().getQuestionType() != null && questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
							questionInstanceDto.setCode((questionMapperInstance.getCodeByUser()==null||questionMapperInstance.getCodeByUser().trim().length() == 0) ?questionMapper.getQuestion().getInputCode():questionMapperInstance.getCodeByUser());
						}
						
						if(questionMapper.getQuestion().getQuestionType() != null && questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.FILL_BLANKS_MCQ.getType())){
							processDtoForFillInBlankAnswers(questionMapperInstance, questionInstanceDto, questionMapper);
						}
						
						if(questionMapper.getQuestion().getQuestionType() != null && questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())){
							processDtoForMTFAnswers(questionMapperInstance, questionInstanceDto, questionMapper);
						}
					}
					sectionInstanceDto.setFirst(true);
					sectionInstanceDto.setQuestionInstanceDtos(questionMapperInstances);
					
					/**
					 * For only 1 Q and 1 section..adding this
					 */
					if(sections.size() == 1){
						if(questionMappersActual.size() == 1){
							sectionInstanceDto.setLast(true);
						}
					}
					
					/**
					 * End For only 1 Q and 1 section..adding this
					 */
					
					
					model.addObject("currentSection", sectionInstanceDto);

					model.addObject("currentQuestion", questionMapperInstances.get(0));  // problem area
					request.getSession().setAttribute("currentSection", sectionInstanceDto);
					 /**
					  * Get the fullstack for Q if type is full stack.
					  * 
					  */
					if(questionMapperInstances.size() > 0){
						if(!questionMapperInstances.get(0).getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.NONE.getStack())){
							System.out.println("setting workspace"); 
							setWorkspaceIDEForFullStackQ(request, questionMapperInstances.get(0));
						 }
					}
					 
					 /**
					  * End full stack check
					  */
			 }
			 sectionInstanceDto.setNoOfQuestions(section.getNoOfQuestionsToBeAsked());
			 sectionInstanceDto.setSection(section);
			 count++;
			 //fetch the questions based on the associated sections
				
			if(sectionInstanceDto.getQuestionInstanceDtos() != null && sectionInstanceDto.getQuestionInstanceDtos().size() == 0){
				populateWithQuestions(sectionInstanceDto, test.getTestName(), sectionInstanceDto.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
			}
		}
		
		 	request.getSession().setAttribute("sectionInstanceDtos", sectionInstanceDtos);
		 	putMiscellaneousInfoInModel(model, request);
		 	processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
		 	model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		 	//model.addObject("percentage", "0");
		 	//model.addObject("totalQuestions", ""+totalQuestions);
		 //	model.addObject("noAnswered", "0");
		 	model.addObject("confidenceFlag", test.getConsiderConfidence());
		 	/**
		 	 * Add the time counter part - retrieved time counter for sessions that were disrupted.
		 	 */
		 	UserTestTimeCounter counter = counterService.findByPrimaryKey(test.getId(), user.getEmail(), user.getCompanyId());
		 	if(counter == null){
		 		model.addObject("timeCounter", new Long(0));
		 	}
		 	else{
		 		model.addObject("timeCounter", counter.getTimeCounter());
		 	}
		 	model.addObject("firstpage", "yes");
		    return model;
	 }
	 
	 
	 private QuestionInstanceDto processDtoForMTFAnswers(QuestionMapperInstance questionMapperInstance, QuestionInstanceDto questionInstanceDto,  QuestionMapper questionMapper){
		 if(questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())){
			 MTFdto mtf = new MTFdto();
			 mtf.setMatchLeft1(questionMapper.getQuestion().getMatchLeft1());
			 mtf.setMatchLeft2(questionMapper.getQuestion().getMatchLeft2());
			 mtf.setMatchLeft3(questionMapper.getQuestion().getMatchLeft3());
			 mtf.setMatchLeft4(questionMapper.getQuestion().getMatchLeft4());
			 mtf.setMatchLeft5(questionMapper.getQuestion().getMatchLeft5());
			 mtf.setMatchLeft6(questionMapper.getQuestion().getMatchLeft6());
			 List<String> rightSideOptions = new ArrayList<>();
			 if(questionMapper.getQuestion().getMatchRight1() != null && questionMapper.getQuestion().getMatchRight1().trim().length() > 0){
				 rightSideOptions.add(questionMapper.getQuestion().getMatchRight1());
			 }
			 if(questionMapper.getQuestion().getMatchRight2() != null && questionMapper.getQuestion().getMatchRight2().trim().length() > 0){
				 rightSideOptions.add(questionMapper.getQuestion().getMatchRight2());
			 }
			 if(questionMapper.getQuestion().getMatchRight3() != null && questionMapper.getQuestion().getMatchRight3().trim().length() > 0){
				 rightSideOptions.add(questionMapper.getQuestion().getMatchRight3());
			 }
			 
			 if(questionMapper.getQuestion().getMatchRight4() != null && questionMapper.getQuestion().getMatchRight4().trim().length() > 0){
				 rightSideOptions.add(questionMapper.getQuestion().getMatchRight4());
			 }
			 if(questionMapper.getQuestion().getMatchRight5() != null && questionMapper.getQuestion().getMatchRight5().trim().length() > 0){
				 rightSideOptions.add(questionMapper.getQuestion().getMatchRight5());
			 }
			 if(questionMapper.getQuestion().getMatchRight6() != null && questionMapper.getQuestion().getMatchRight6().trim().length() > 0){
				 rightSideOptions.add(questionMapper.getQuestion().getMatchRight6());
			 }
			 
			 Collections.shuffle(rightSideOptions);
			 mtf.setMatchRight1Display(rightSideOptions.get(0));
			 mtf.setMatchRight2Display(rightSideOptions.get(1));
			 if(rightSideOptions.size()>2){
				 mtf.setMatchRight3Display(rightSideOptions.get(2)); 
			 }
			 if(rightSideOptions.size()>3){
				 mtf.setMatchRight4Display(rightSideOptions.get(3)); 
			 }
			 if(rightSideOptions.size()>4){
				 mtf.setMatchRight5Display(rightSideOptions.get(4)); 
			 }
			 if(rightSideOptions.size()>5){
				 mtf.setMatchRight6Display(rightSideOptions.get(5)); 
			 }
			 
			 questionInstanceDto.setMtf(mtf);
			 
		 }
		 
		
		 	
		return  questionInstanceDto;
		 
	 }
	 
	 private QuestionInstanceDto processDtoForFillInBlankAnswers(QuestionMapperInstance questionMapperInstance, QuestionInstanceDto questionInstanceDto,  QuestionMapper questionMapper){
		 if(questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.FILL_BLANKS_MCQ.getType())){
			 questionInstanceDto.setFillInBlanks(new ArrayList<>());
			 for(int i=0;i< (questionMapper.getQuestion().getNoOfFillBlanks()==null?0:questionMapper.getQuestion().getNoOfFillBlanks());i++){
				 questionInstanceDto.getFillInBlanks().add(new String());
			 }
		 }
		 
		 String blanks  = questionMapperInstance.getUserChoices();
		 String lines[] = null;
		 if(blanks != null){
			 lines = blanks.split("\\r?\\n");
			 	for(int i=0;i<questionInstanceDto.getFillInBlanks().size();i++){
			 		if(i < lines.length){
			 			questionInstanceDto.getFillInBlanks().set(i, lines[i]);
			 		}
			 	}
		 }
		 
		 	
		return  questionInstanceDto;
		 
	 }
	 
	 private ModelAndView processPercentages(ModelAndView model, List<SectionInstanceDto> sectionInstanceDtos, int noOfQs) {
		 int noOfQuestionsNotAnswered = 0;
		 int noOfQuestions = noOfQs;
		 for(SectionInstanceDto dto: sectionInstanceDtos) {
			 if(dto.getQuestionInstanceDtos().size() == 0) {
				 noOfQuestionsNotAnswered = noOfQuestionsNotAnswered + dto.getNoOfQuestions();//making sure no of qs not answered are considered for the test when it begins or lese the progress would be wrong
			 }
			 for(QuestionInstanceDto questionInstanceDto : dto.getQuestionInstanceDtos()) {
				 String qType = "";
				 if(questionInstanceDto.getQuestionMapperInstance() == null || questionInstanceDto.getQuestionMapperInstance().getQuestionMapper() == null || questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion() == null || questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() == null){
					 qType = QuestionType.MCQ.getType();
				 }
				 else{
					 qType = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType();
				 }
				 
			 
				if(qType.equalsIgnoreCase(QuestionType.MCQ.getType())){
					if( questionInstanceDto.getOne() == false && questionInstanceDto.getTwo() == false && questionInstanceDto.getThree() == false && questionInstanceDto.getFour() == false && questionInstanceDto.getFive() == false && questionInstanceDto.getSix() == false) {
						noOfQuestionsNotAnswered ++;
					}
				}
				
				if(qType.equalsIgnoreCase(QuestionType.FILL_BLANKS_MCQ.getType())){
					if(questionInstanceDto.getFillInBlanks() != null && questionInstanceDto.getFillInBlanks().size() >  0){
						//noOfQuestionsNotAnswered ++;
						Boolean notans = false;
						for(String s : questionInstanceDto.getFillInBlanks()){
							if(s.trim().length() == 0){
								notans = true;
								break;
							}
						}
						if(notans){
							noOfQuestionsNotAnswered ++;
						}
					}
					else{
						noOfQuestionsNotAnswered ++;
					}
				}
				
				if(qType.equalsIgnoreCase(QuestionType.MATCH_FOLLOWING_MCQ.getType())){
					//always answered//later work on it
					MTFdto mtFdto = questionInstanceDto.getMtf();
					if(mtFdto == null){
						mtFdto  = new MTFdto();
					}
					
					if(mtFdto.getMatchRight1() == null || mtFdto.getMatchRight2() == null){
						noOfQuestionsNotAnswered ++;
					}
					else if(mtFdto.getMatchRight1() != null && mtFdto.getMatchRight1().trim().length() == 0 || mtFdto.getMatchRight2() != null && mtFdto.getMatchRight2().trim().length() == 0){
						noOfQuestionsNotAnswered ++;
					}
				}
				
				if(qType.equalsIgnoreCase(QuestionType.CODING.getType())){
					//do nothing
					//always answered by default
					String code = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode();
					String codeByUser = questionInstanceDto.getCode();
						if(code == null || codeByUser == null ){
							noOfQuestionsNotAnswered ++;
						}
						else if(code.equals(codeByUser)){
							noOfQuestionsNotAnswered ++;
						}
				}
				
				if(qType.equalsIgnoreCase(QuestionType.SUBJECTIVE_TEXT.getType())) {
					String txt=questionInstanceDto.getSubjectiveText();
					if(txt==null) {
						noOfQuestionsNotAnswered++;
					}
				}
				if(qType.equalsIgnoreCase(QuestionType.VIDEO_UPLOAD_BY_USER.getType())||qType.equalsIgnoreCase(QuestionType.IMAGE_UPLOAD_BY_USER.getType())) {
					String url=questionInstanceDto.getImageUploadUrl();
					if(url==null) {
						noOfQuestionsNotAnswered++;
					}
				}
				
				
				
				
				
				
				//noOfQuestions++;
			 }
		 }
		 float per = (100 * (noOfQuestions - noOfQuestionsNotAnswered))/noOfQuestions;
		 DecimalFormat df = new DecimalFormat();
		 df.setMaximumFractionDigits(2);
		 String percentage = df.format(per);
		 model.addObject("percentage", percentage);
		 	model.addObject("totalQuestions", ""+noOfQuestions);
		 	model.addObject("noAnswered", ""+(noOfQuestions - noOfQuestionsNotAnswered) );
		 return model;
	 }
	 
	 
	 @RequestMapping(value = "/changeSection", method = RequestMethod.GET)
	  public ModelAndView changeSection(@RequestParam String sectionName, @RequestParam String timeCounter, HttpServletRequest request, HttpServletResponse response,@ModelAttribute("studentTestForm") StudentTestForm studentForm) {
		// ModelAndView model= new ModelAndView("test_cognizant");
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 ModelAndView model;
		 if(test.getFullStackTest() != null && test.getFullStackTest()){
		 		//model= new ModelAndView("test_fstk");
			 model= new ModelAndView("test_fstk_new");
		 	}
		 	else{
		 		model= new ModelAndView("test_cognizant");
//		 		model= new ModelAndView("test_new");
		 	}
		 
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 
		 int count = 0;
		 for(SectionInstanceDto sectionInstanceDto :  sectionInstanceDtos) {
			 count++;
			 sectionInstanceDto.setCurrent(false);
			// sectionInstanceDto.getQuestionInstanceDtos().clear();
			 if(sectionInstanceDto.getSection().getSectionName().equals(sectionName)) {
				 	if(count == 1) {
				 		sectionInstanceDto.setFirst(true);
				 		sectionInstanceDto.setLast(false);
				 	}
				 	
				 	if(count ==sectionInstanceDtos.size() ) {
						 sectionInstanceDto.setFirst(false);
						 sectionInstanceDto.setLast(false);
					 }
				 	
				 	if(count == 1 && sectionInstanceDtos.size() == 1){
				 		sectionInstanceDto.setFirst(true);
						 sectionInstanceDto.setLast(true);
				 	}
				 sectionInstanceDto.setCurrent(true);
				 sectionInstanceDto = populateWithQuestions(sectionInstanceDto, test.getTestName(),sectionInstanceDto.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				 model.addObject("currentSection", sectionInstanceDto);
				 QuestionInstanceDto currentQuestion = sectionInstanceDto.getQuestionInstanceDtos().get(0);
				// if(sectionInstanceDto.getQuestionInstanceDtos().size() == 1){
				 if(sectionInstanceDto.getQuestionInstanceDtos().size() == 1 && count == sectionInstanceDtos.size()){
					 sectionInstanceDto.setLast(true);
				 }
				 else{
					 sectionInstanceDto.setLast(false);
				 }
				 
				 	if(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() != null && currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
				 		if(currentQuestion.getCode() == null || currentQuestion.getCode().trim().length() == 0){
				 			currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode());
				 		}
				 		
				 	}
				 model.addObject("currentQuestion", sectionInstanceDto.getQuestionInstanceDtos().get(0));
				 request.getSession().setAttribute("currentSection", sectionInstanceDto);
			 }
		 }
		 
		 
		 	request.getSession().setAttribute("sectionInstanceDtos", sectionInstanceDtos);
		 	model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		 	putMiscellaneousInfoInModel(model, request);
		 	setTimeInCounter(request, Long.valueOf(timeCounter));
		 	processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
		    return model;
	 }
	 
	 private SectionInstanceDto populateWithQuestions(SectionInstanceDto sectionInstanceDto, String testName, String sectionName, String companyId, String email) {
		 	if(sectionInstanceDto.getQuestionInstanceDtos().size() > 0) {
		 		//its already populated
		 		return sectionInstanceDto;
		 	}
		 List<QuestionMapper> questionMappers = questionMapperService.getQuestionsForSection(testName, sectionName, companyId);
		// Collections.shuffle(questionMappers);
		 List<QuestionMapper> questionMappersActual = questionMappers.subList(0, sectionInstanceDto.getNoOfQuestions());
		 int pos = 0;
		 for (QuestionMapper questionMapper : questionMappersActual) {
				//creating the instances of question mapper instance entity
				QuestionInstanceDto questionInstanceDto= new QuestionInstanceDto();
				pos++;
				questionInstanceDto.setPosition(pos);
				//QuestionMapperInstance questionMapperInstance = new QuestionMapperInstance();
				QuestionMapperInstance questionMapperInstance = null;
				if(sectionInstanceDto.getSection().getPercentQuestionsAsked() == 100){
					//questionMapperInstance = 	questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUser(questionMapper.getQuestion().getQuestionText(), testName, sectionName, email, companyId);
//					try {
//						questionMapperInstance = 	questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUser(questionMapper.getQuestion().getQuestionText(), testName, sectionName, email, companyId);
//					} catch (javax.persistence.NonUniqueResultException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						logger.error("duplicate anss for "+questionMapper.getQuestion().getQuestionText()+"-"+ testName+"-"+  sectionName+"-"+  email+"-"+  companyId);
//						List<QuestionMapperInstance> qms = questionMapperInstanceRepository.findDuplicateQuestionMapperInstanceForUser(questionMapper.getQuestion().getQuestionText(), testName, sectionName, email, companyId);
//						for(QuestionMapperInstance q : qms){
//							questionMapperInstanceRepository.delete(q);
//						}
//					}
					questionMapperInstance = questionMapperInstaceService.removeDublicateAndGetInstance(questionMapper.getQuestion().getQuestionText(), testName, sectionName, email, companyId);
				}
				
				if(questionMapperInstance == null){
					questionMapperInstance = new QuestionMapperInstance();
				}
				
				if(questionMapper.getQuestion().getQuestionType() != null && questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.FILL_BLANKS_MCQ.getType())){
					processDtoForFillInBlankAnswers(questionMapperInstance, questionInstanceDto, questionMapper);
				}
				
				if(questionMapper.getQuestion().getQuestionType() != null && questionMapper.getQuestion().getQuestionType().getType().equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())){
					processDtoForMTFAnswers(questionMapperInstance, questionInstanceDto, questionMapper);
				}
				
				
				questionInstanceDto.setQuestionMapperInstance(questionMapperInstance);
				questionMapperInstance.setQuestionMapper(questionMapper);
				sectionInstanceDto.getQuestionInstanceDtos().add(questionInstanceDto);
			}	
		return sectionInstanceDto;
	 }
	 
	 private void evaluateMTFQuestion(QuestionInstanceDto currentQuestion, QuestionInstanceDto questionInstanceDto){
		 Map<String, String> correctCombinations = new HashMap<String, String>();
		 String left1 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchLeft1();
		 String right1 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchRight1();
		 correctCombinations.put(left1, right1);
		 
		 String left2 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchLeft2();
		 String right2 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchRight2();
		 correctCombinations.put(left2, right2);
		 
		 String left3 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchLeft3();
		 String right3 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchRight3();
		 if(left3 != null && right3 != null && left3.trim().length()!= 0 && right3.trim().length() != 0){
			 correctCombinations.put(left3, right3);
		 }
		 
		 
		 String left4 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchLeft4();
		 String right4 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchRight4();
		 if(left4 != null && right4 != null && left4.trim().length()!= 0 && right4.trim().length() != 0){
			 correctCombinations.put(left4, right4);
		 }
		 
		 String left5 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchLeft5();
		 String right5 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchRight5();
		 if(left5 != null && right5 != null && left5.trim().length()!= 0 && right5.trim().length() != 0){
			 correctCombinations.put(left5, right5);
		 }
		 
		 String left6 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchLeft6();
		 String right6 = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getMatchRight6();
		 if(left6 != null && right6 != null && left6.trim().length()!= 0 && right6.trim().length() != 0){
			 correctCombinations.put(left6, right6);
		 }
		 
		 
		 
		 String rightA = currentQuestion.getMtf().getMatchRight1();
		 questionInstanceDto.getMtf().setMatchRight1(rightA);
		 questionInstanceDto.getMtf().setMatchLeft1(left1);
		 questionInstanceDto.getMtf().setMatchRight2(currentQuestion.getMtf().getMatchRight2());
		 questionInstanceDto.getMtf().setMatchLeft2(left2);
		 
		 questionInstanceDto.getMtf().setMatchRight3(currentQuestion.getMtf().getMatchRight3());
		 questionInstanceDto.getMtf().setMatchLeft3(left3);
		 
		 questionInstanceDto.getMtf().setMatchRight4(currentQuestion.getMtf().getMatchRight4());
		 questionInstanceDto.getMtf().setMatchLeft4(left4);
		 
		 questionInstanceDto.getMtf().setMatchRight5(currentQuestion.getMtf().getMatchRight5());
		 questionInstanceDto.getMtf().setMatchLeft5(left5);
		 
		 questionInstanceDto.getMtf().setMatchRight6(currentQuestion.getMtf().getMatchRight6());
		 questionInstanceDto.getMtf().setMatchLeft6(left6);
		 
		 questionInstanceDto.getQuestionMapperInstance().setMatchRight1(currentQuestion.getMtf().getMatchRight1());
		 questionInstanceDto.getQuestionMapperInstance().setMatchRight2(currentQuestion.getMtf().getMatchRight2());
		 questionInstanceDto.getQuestionMapperInstance().setMatchRight3(currentQuestion.getMtf().getMatchRight3());
		 questionInstanceDto.getQuestionMapperInstance().setMatchRight4(currentQuestion.getMtf().getMatchRight4());
		 questionInstanceDto.getQuestionMapperInstance().setMatchRight5(currentQuestion.getMtf().getMatchRight5());
		 questionInstanceDto.getQuestionMapperInstance().setMatchRight6(currentQuestion.getMtf().getMatchRight6());
		 
//		 questionInstanceDto.getMtf().setMatchRight1Display(currentQuestion.getMtf().getMatchRight1Display());
//		 questionInstanceDto.getMtf().setMatchRight2Display(currentQuestion.getMtf().getMatchRight2Display());
//		 questionInstanceDto.getMtf().setMatchRight3Display(currentQuestion.getMtf().getMatchRight3Display());
//		 questionInstanceDto.getMtf().setMatchRight4Display(currentQuestion.getMtf().getMatchRight4Display());
//		 questionInstanceDto.getMtf().setMatchRight5Display(currentQuestion.getMtf().getMatchRight5Display());
//		 questionInstanceDto.getMtf().setMatchRight6Display(currentQuestion.getMtf().getMatchRight6Display());
		 
		 currentQuestion.getMtf().initAnswerColours();
		 questionInstanceDto.getMtf().initAnswerColours();
		 
		 if(rightA != null){
			 String expected = correctCombinations.get(left1);
			 if(expected == null){
				 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
				 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
				 return;
			 }
			 else if(!expected.equals(rightA)){
				 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
				 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
				 return;
			 }
		 }
		 
		 rightA = currentQuestion.getMtf().getMatchRight2();
		 questionInstanceDto.getMtf().setMatchRight2(rightA);
		 questionInstanceDto.getMtf().setMatchLeft2(left2);
		 if(rightA != null){
			 String expected = correctCombinations.get(left2);
			 if(expected == null){
				 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
				 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
				 return;
			 }
			 else if(!expected.equals(rightA)){
				 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
				 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
				 return;
			 }
		 }
		 
		 rightA = currentQuestion.getMtf().getMatchRight3();
		 questionInstanceDto.getMtf().setMatchRight3(rightA);
		 questionInstanceDto.getMtf().setMatchLeft3(left3);
		 if(rightA != null){
			 if(left3 != null){
				 String expected = correctCombinations.get(left3);
				 if(expected == null){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
				 else if(!expected.equals(rightA)){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
			 }
			 
		 }
		 
		 rightA = currentQuestion.getMtf().getMatchRight4();
		 questionInstanceDto.getMtf().setMatchRight4(rightA);
		 questionInstanceDto.getMtf().setMatchLeft4(left4);
		 if(rightA != null){
			 if(left4 != null){
				 String expected = correctCombinations.get(left4);
				 if(expected == null){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
				 else if(!expected.equals(rightA)){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
			 }
			 
		 }
		 
		 rightA = currentQuestion.getMtf().getMatchRight5();
		 questionInstanceDto.getMtf().setMatchRight5(rightA);
		 questionInstanceDto.getMtf().setMatchLeft5(left5);
		 if(rightA != null){
			 if(left5 != null){
				 String expected = correctCombinations.get(left5);
				 if(expected == null){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
				 else if(!expected.equals(rightA)){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
			 }
			 
		 }
		 
		 rightA = currentQuestion.getMtf().getMatchRight6();
		 questionInstanceDto.getMtf().setMatchRight6(rightA);
		 questionInstanceDto.getMtf().setMatchLeft6(left6);
		 if(rightA != null){
			 if(left6 != null){
				 String expected = correctCombinations.get(left6);
				 if(expected == null){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
				 else if(!expected.equals(rightA)){
					 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
					 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
					 return;
				 }
			 }
			 
		 }
		 
		// currentQuestion.getMtf().initAnswerColours();
		// questionInstanceDto.getMtf().initAnswerColours();
		 
		 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
		 questionInstanceDto.getQuestionMapperInstance().setCorrect(true);
		 
	 }
	 
	
	 
	 private void evaluateFillInBlankQuestion(QuestionInstanceDto currentQuestion, QuestionInstanceDto questionInstanceDto){
		 String choices = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFillInBlankOptions();
		 if(choices != null){
			 String[] systemchoices = choices.split(System.lineSeparator());
		
			 List<String> fibs = currentQuestion.getFillInBlanks();
			 if(fibs.size() == 0){
				 questionInstanceDto.getQuestionMapperInstance().setAnswered(false);
				 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
				 return;
			 }
			 
			 String userEnteredChoices = String.join(System.lineSeparator(), fibs);
			 questionInstanceDto.getQuestionMapperInstance().setUserChoices(userEnteredChoices);
			 questionInstanceDto.setFillInBlanks(fibs);
			 if(systemchoices.length != fibs.size()){
				 questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
				 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
				 return;
			 }
			 else{
				 
				for(int i=0;i<fibs.size();i++){
					if(!fibs.get(i).equals(systemchoices[i])){
						questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
						 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
						 return;
					}
				}
				questionInstanceDto.getQuestionMapperInstance().setAnswered(true);
				questionInstanceDto.getQuestionMapperInstance().setCorrect(true);
			 }
			 
			 //if()
			 
		 }
		 
	 }
	 
	 private void setAnswers(HttpServletRequest request, SectionInstanceDto currentSection, QuestionInstanceDto currentQuestion, String questionMapperId, Boolean calledFromSubmit) {
		 /**
		  * Get the corresponding section from session object
		  */
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 	for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
		 		if(sectionInstanceDto.getSection().getSectionName().equals(currentSection.getSection().getSectionName())) {
		 			
		 			
		 			/**
		 			 * Get the corresponding Question from section from the session object
		 			 */
		 			for(QuestionInstanceDto questionInstanceDto : currentSection.getQuestionInstanceDtos()) {
		 				
		 				
		 				
		 				
		 				if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getId().equals(Long.valueOf(questionMapperId))) {
		 					
		 					
		 					/**
		 					 * adding mandatory info on questionInstanceDto.getQuestionMapperInstance() 
		 					 */
		 					 User user = (User) request.getSession().getAttribute("user");
		 					 Test test = (Test) request.getSession().getAttribute("test");
		 					questionInstanceDto.getQuestionMapperInstance().setCompanyId(user.getCompanyId());
		 					questionInstanceDto.getQuestionMapperInstance().setCompanyName(user.getCompanyName());
		 					questionInstanceDto.getQuestionMapperInstance().setUser(user.getEmail());
		 					questionInstanceDto.getQuestionMapperInstance().setUerFullName(user.getFirstName()+" "+user.getLastName());
		 					questionInstanceDto.getQuestionMapperInstance().setSectionName(currentSection.getSection().getSectionName());
		 					questionInstanceDto.getQuestionMapperInstance().setTestName(test.getTestName());
		 					/**
		 					 * end adding mandatory info on questionInstanceDto.getQuestionMapperInstance() 
		 					 */
		 					
		 					
		 					/**	
				 			 * Add code for evaluating coding engine Q
				 			 */
		 					if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() == null){
		 						questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().setQuestionType(QuestionType.MCQ);
		 					}
				 			String type = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType();
				 			Question q = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion();
				 			if(type != null && type.equals(QuestionType.CODING.getType())){
				 				evaluateCodingAnswer(currentQuestion, questionInstanceDto);//here questionInstanceDto is updated with compilation results
				 				sectionInstanceDto.setQuestionInstanceDtos(currentSection.getQuestionInstanceDtos());
				 				/**
				 				 * Question level persistence code added
				 				 */
				 				sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
				 				/**
				 				 * End Question level persistence
				 				 */
				 				break;
				 			}
				 			if(type != null && type.equals(QuestionType.FILL_BLANKS_MCQ.getType())){
				 				evaluateFillInBlankQuestion(currentQuestion, questionInstanceDto);
				 				sectionInstanceDto.setQuestionInstanceDtos(currentSection.getQuestionInstanceDtos());
				 				/**
				 				 * Question level persistence code added
				 				 */
				 				sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
				 				/**
				 				 * End Question level persistence
				 				 */
				 				break;
				 			}
				 			
				 			if(type != null && type.equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())){
				 				evaluateMTFQuestion(currentQuestion, questionInstanceDto);
				 				sectionInstanceDto.setQuestionInstanceDtos(currentSection.getQuestionInstanceDtos());
				 				/**
				 				 * Question level persistence code added
				 				 */
				 				sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
				 				/**
				 				 * End Question level persistence
				 				 */
				 				break;
				 			}
				 			if(type != null && type.equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 				questionInstanceDto.getQuestionMapperInstance().setSubjective(true);
				 				questionInstanceDto.getQuestionMapperInstance().setImageUploadUrl(currentQuestion.getImageUploadUrl());
				 				/**
				 				 * Question level persistence code added
				 				 */
				 				sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
				 				/**
				 				 * End Question level persistence
				 				 */
				 				break;
				 			}
				 			
				 			if(type != null && type.equals(QuestionType.VIDEO_UPLOAD_BY_USER.getType())){
				 				questionInstanceDto.getQuestionMapperInstance().setSubjective(true);
				 				questionInstanceDto.getQuestionMapperInstance().setVideoUploadUrl(currentQuestion.getVideoUploadUrl());
				 				/**
				 				 * Question level persistence code added
				 				 */
				 				sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
				 				/**
				 				 * End Question level persistence
				 				 */
				 				break;
				 			}
				 			
				 			if(type != null && type.equals(QuestionType.SUBJECTIVE_TEXT.getType())){
				 				questionInstanceDto.getQuestionMapperInstance().setSubjective(true);
				 				questionInstanceDto.getQuestionMapperInstance().setSubjectiveText(currentQuestion.getQuestionMapperInstance().getSubjectiveText());
				 				
				 				/**
				 				 * Question level persistence code added
				 				 */
				 				sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
				 				/**
				 				 * End Question level persistence
				 				 */
				 				break;
				 			}
				 			
				 			/**
				 			 * End Add code for evaluating coding engine Q
				 			 */
		 					
		 					
		 					String userChoices = "";
		 					if(currentQuestion.getOne()) {
		 						userChoices = "Choice 1";
		 						questionInstanceDto.setOne(true);
		 					}
		 					else {
		 						questionInstanceDto.setOne(false);
		 					}
		 					
		 					if(currentQuestion.getTwo()) {
		 						if(userChoices.length() > 0) {
		 							userChoices += "-Choice 2";
		 							questionInstanceDto.setTwo(true);
		 						}
		 						else {
		 							userChoices = "Choice 2";
		 							questionInstanceDto.setTwo(true);
		 						}
		 					}
		 					else {
		 						questionInstanceDto.setTwo(false);
		 					}
		 					
		 					if(currentQuestion.getThree()) {
		 						if(userChoices.length() > 0) {
		 							userChoices += "-Choice 3";
		 							questionInstanceDto.setThree(true);
		 						}
		 						else {
		 							userChoices = "Choice 3";
		 							questionInstanceDto.setThree(true);
		 						}
		 					}
		 					else {
		 						questionInstanceDto.setThree(false);
		 					}
		 					
		 					if(currentQuestion.getFour()) {
		 						if(userChoices.length() > 0) {
		 							userChoices += "-Choice 4";
		 							questionInstanceDto.setFour(true);
		 						}
		 						else {
		 							userChoices = "Choice 4";
		 							questionInstanceDto.setFour(true);
		 						}
		 					}
		 					else {
		 						questionInstanceDto.setFour(false);
		 					}
		 					
		 					
		 					if(currentQuestion.getFive()) {
		 						if(userChoices.length() > 0) {
		 							userChoices += "-Choice 5";
		 							questionInstanceDto.setFive(true);
		 						}
		 						else {
		 							userChoices = "Choice 5";
		 							questionInstanceDto.setFive(true);
		 						}
		 					}
		 					else {
		 						questionInstanceDto.setFive(false);
		 					}
		 					
		 					if(currentQuestion.getSix()) {
		 						if(userChoices.length() > 0) {
		 							userChoices += "-Choice 6";
		 							questionInstanceDto.setSix(true);
		 						}
		 						else {
		 							userChoices = "Choice 6";
		 							questionInstanceDto.setSix(true);
		 						}
		 					}
		 					else {
		 						questionInstanceDto.setSix(false);
		 					}
		 					
		 					
		 					questionInstanceDto.getQuestionMapperInstance().setUserChoices(userChoices);
		 					/**
		 					 * Confidence based assessment
		 					 */
		 					questionInstanceDto.getQuestionMapperInstance().setConfidence(currentQuestion.getConfidence());
		 					questionInstanceDto.setConfidence(currentQuestion.getConfidence());
		 					questionInstanceDto.setRadioAnswer(currentQuestion.getRadioAnswer());
		 					sectionInstanceDto.setQuestionInstanceDtos(currentSection.getQuestionInstanceDtos());
		 					/**
			 				 * Question level persistence code added
			 				 */
		 					
		 					sectionInstanceService.saveOrUpdateAnswer(questionInstanceDto.getQuestionMapperInstance());
			 				/**
			 				 * End Question level persistence
			 				 */
		 					break;
		 				}
		 			}
		 		}
		 	}
	 }
	 
	 private void evaluateMySQLCoding(QuestionInstanceDto currentQuestion,  QuestionInstanceDto questionInstanceDto){
		 List results = null;
		 try {
			results =  automationService.fireDirectQuery(currentQuestion.getCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
		}
		 String ret = "";
		 List<String> ress = new ArrayList();
		 if(results != null && results.size() > 0){
			 if(results.get(0) instanceof String){
				 ress = (List<String>) results;
				 
				 for(String s : ress){
					 ret += s + "   ";
				 }
				 
			 }
			 else if(results.get(0) instanceof String[]){
			List<String[]> op = (List<String[]> ) results;
				 for(String[] row : op){
					 for(Object col : row){
						 ret += col +"    ";
					 }
					 ret += "\n";
				 }
			 }
			 else if(results.get(0) instanceof Object[]){
				 //System.out.println("multiple results 111");
			List<Object[]> op =  results;
				 for(Object[] row : op){
					 for(Object col : row){
						 ret += col.toString() +"    ";
					 }
					 ret += "\n";
				 }
			 }
			 else{
				// System.out.println("multiple results but no where");
				 ret = "failed";
			 }
		 }
		 
		// System.out.println("hidden neg "+questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputNegative());
		// System.out.println("ret "+ret);
		 if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputNegative().equals(ret!= null?ret.trim():"")){
			// System.out.println("ccccccccccccccccccorect");
			 
			 questionInstanceDto.getQuestionMapperInstance().setCodingOuputBySystemTestCase(ret);
			 questionInstanceDto.getQuestionMapperInstance().setCorrect(true);
			// currentQuestion.getQuestionMapperInstance().setCorrect(true);
			// currentQuestion.getQuestionMapperInstance().setCodingOuputBySystemTestCase(ret);
		 }
		 else{
			 //System.out.println("faaaaaaaaaaaaaail");
			 questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
			 questionInstanceDto.getQuestionMapperInstance().setCodingOuputBySystemTestCase(ret);
		 }
	 }
	 
	 private void evaluateCodingAnswer(QuestionInstanceDto currentQuestion,  QuestionInstanceDto questionInstanceDto){
		 Question q = questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion();
		 boolean answered = questionInstanceDto.getQuestionMapperInstance().getAnswered() == null?false:questionInstanceDto.getQuestionMapperInstance().getAnswered();
			if(answered){
				if(currentQuestion.getInput() == null){
					answered = true; // this situation should not occur
				}
				
				if(questionInstanceDto.getQuestionMapperInstance().getCodeByUser() == null){
					answered = true; // again a rar sitiation
				}
				
				if(!currentQuestion.getCode().equals(questionInstanceDto.getQuestionMapperInstance().getCodeByUser())){
					answered = false; // if the code has changed then need to recompile the code
				}
				else{
					answered = true;
				}
			}
			if(!answered){
			String lang = LanguageCodes.getLanguageCode(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getLanguage().getLanguage());
				if(lang.equals("13")){
					evaluateMySQLCoding(currentQuestion, questionInstanceDto);
					return;
				}
			
			logger.info("compiling lang is "+lang);
			//System.out.println("compiling lang is "+lang);
			CompileData compileData = new CompileData();
			compileData.setLanguage(lang);
			String code = currentQuestion.getCode();
			code = code.replaceAll("\\\\n", System.lineSeparator());
			code = code.replaceAll("\\\\t", "   ");
			compileData.setCode(code);
			compileData.setStdin(q.getHiddenInputNegative());
			/**
			 * Negative Test Case
			 */
			CompileOutput op = compiler.compile(compileData);
			op.setOutput((op.getOutput() == null)?op.getOutput():op.getOutput().replaceAll("\n", ""));
			currentQuestion.setOutput(op.getOutput() == null?"wrong":op.getOutput());
			questionInstanceDto.setCode(currentQuestion.getCode());
			questionInstanceDto.setOutput(op.getOutput() == null?"wrong":op.getOutput());
			questionInstanceDto.getQuestionMapperInstance().setCodeByUser(currentQuestion.getCode().replaceAll("\r", ""));
			questionInstanceDto.getQuestionMapperInstance().setCodingOuputBySystemTestCase(op.getOutput() == null?"wrong":op.getOutput());
			
			boolean compilationError = false;
			if(op.getErrors() != null && op.getErrors().trim().length() > 0){
				
				if(op.getErrors().contains("error")){
					questionInstanceDto.getQuestionMapperInstance().setCodeCompilationErrors(true);
					compilationError = true;
				}
				else{
					questionInstanceDto.getQuestionMapperInstance().setCodeRunTimeErrors(true);
				}
				
			}
			else{
				compilationError = false;
				questionInstanceDto.getQuestionMapperInstance().setCodeCompilationErrors(false);
			}
			
		
			
			/**
			 * Positive Test case
			 */
			if(q.getHiddenInputPositive() != null && q.getHiddenInputPositive().trim().length() != 0 && !compilationError){
				compileData.setStdin(q.getHiddenInputPositive());
				op = compiler.compile(compileData);
				op.setOutput((op.getOutput() == null)?op.getOutput():op.getOutput().replaceAll("\n", ""));
				currentQuestion.setOutput(op.getOutput() == null?"wrong":op.getOutput());
				questionInstanceDto.setCode(currentQuestion.getCode());
					if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputPositive() != null && op.getOutput() != null){
questionInstanceDto.getQuestionMapperInstance().setTestCaseInputPositive(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputPositive().trim().equals(op.getOutput().trim())?true:false);
					}
					else{
						questionInstanceDto.getQuestionMapperInstance().setTestCaseInputPositive(false);
					}
			}
			
			
			/**
			 * set correct coding answer based on both positive and negative test case output being successful
			 */
			if(questionInstanceDto.getQuestionMapperInstance().getTestCaseInputNegative() && questionInstanceDto.getQuestionMapperInstance().getTestCaseInputPositive()){
				questionInstanceDto.getQuestionMapperInstance().setCorrect(true);
			}
			else{
				questionInstanceDto.getQuestionMapperInstance().setCorrect(false);
			}
			
				/**
				 * Extreme Minimal Value Test Case
				 */
				if(q.getHiddenInputExtremeMinimalValue() != null && q.getHiddenInputExtremeMinimalValue().trim().length() > 0 && !compilationError){
					compileData.setStdin(q.getHiddenInputExtremeMinimalValue());
	 				op = compiler.compile(compileData);
	 				op.setOutput((op.getOutput() == null)?op.getOutput():op.getOutput().replaceAll("\n", ""));
	 				//currentQuestion.setOutput(op.getOutput() == null?"wrong":op.getOutput());
	 				questionInstanceDto.setCode(currentQuestion.getCode());
	 					if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputExtremeMinimalValue() != null && op.getOutput() != null){
questionInstanceDto.getQuestionMapperInstance().setTestCaseMinimalValue(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputExtremeMinimalValue().trim().equals(op.getOutput().trim())?true:false);
	 					}
	 					else{
	 						questionInstanceDto.getQuestionMapperInstance().setTestCaseMinimalValue(false);
	 					}
				}
				
					
					/**
					 * Extreme Maximum Value Test Case
					 */
				if(q.getHiddenInputExtremePositiveValue() != null && q.getHiddenInputExtremePositiveValue() .trim().length() > 0 && !compilationError){
					compileData.setStdin(q.getHiddenInputExtremePositiveValue());
	 				op = compiler.compile(compileData);
	 				op.setOutput((op.getOutput() == null)?op.getOutput():op.getOutput().replaceAll("\n", ""));
	 				//currentQuestion.setOutput(op.getOutput() == null?"wrong":op.getOutput());
	 				questionInstanceDto.setCode(currentQuestion.getCode());
	 					if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputExtremePositiveValue() != null && op.getOutput() != null){
questionInstanceDto.getQuestionMapperInstance().setTestCaseMaximumValue(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputExtremePositiveValue().trim().equals(op.getOutput().trim())?true:false);
	 					}
	 					else{
	 						questionInstanceDto.getQuestionMapperInstance().setTestCaseMaximumValue(false);
	 					}
				}
				
					
					/**
					 * Invalid Data Value Test Case
					 */
				if(q.getHiddenInputInvalidDataValue() != null && q.getHiddenInputInvalidDataValue().trim().length() > 0 && !compilationError){
					compileData.setStdin(q.getHiddenInputInvalidDataValue());
	 				op = compiler.compile(compileData);
	 				op.setOutput((op.getOutput() == null)?op.getOutput():op.getOutput().replaceAll("\n", ""));
	 				//currentQuestion.setOutput(op.getOutput() == null?"wrong":op.getOutput());
	 				questionInstanceDto.setCode(currentQuestion.getCode());
	 					if(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputInvalidDataValue() != null && op.getOutput() != null){
questionInstanceDto.getQuestionMapperInstance().setTestCaseInvalidData(questionInstanceDto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getHiddenOutputInvalidDataValue().trim().equals(op.getOutput().trim())?true:false);
	 					}
	 					else{
	 						questionInstanceDto.getQuestionMapperInstance().setTestCaseInvalidData(false);
	 					}
				}
				
			
			//sectionInstanceDto.setQuestionInstanceDtos(currentSection.getQuestionInstanceDtos());
			}
	 }
	 
	 private void saveSection(SectionInstanceDto currentSection, HttpServletRequest request) {
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 	for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
		 		int totalSectionQuestions = sectionInstanceDto.getQuestionInstanceDtos().size() ;
				int correctAnswersPerSection = 0;
				int noOfQuestionsNotAnswered = 0;
		 		if(sectionInstanceDto.getSection().getSectionName().equals(currentSection.getSection().getSectionName())) {
		 			SectionInstance sectionInstance = new SectionInstance();
		 			sectionInstance.setCompanyId(user.getCompanyId());
		 			sectionInstance.setCompanyName(user.getCompanyName());
		 			sectionInstance.setTestName(test.getTestName());
		 			sectionInstance.setSectionName(sectionInstanceDto.getSection().getSectionName());
		 			sectionInstance.setStartTime(System.currentTimeMillis());
					sectionInstance.setEndTime(System.currentTimeMillis() + 200000);
					sectionInstance.setUser(user.getEmail());
					List<QuestionMapperInstance> questionMapperInstances = new ArrayList<>();
					
					/**
					 * Added on 13 May2020 because if in a multi section test, user directly goes to final section and then submit test
					 * then its possible then sections between 1 and last may not have QuestionInstanceDtos with them
					 */
					 if(sectionInstanceDto.getQuestionInstanceDtos().size() == 0){
						 populateWithQuestions(currentSection, test.getTestName(), currentSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
					 }
					 /**
					  * End Added on 13 May2020 
					  */
						
					
					for(QuestionInstanceDto questionInstanceDto : sectionInstanceDto.getQuestionInstanceDtos() ) {
						questionInstanceDto.getQuestionMapperInstance().setCompanyId(user.getCompanyId());
						questionInstanceDto.getQuestionMapperInstance().setCompanyName(user.getCompanyName());
						questionInstanceDto.getQuestionMapperInstance().setTestName(test.getTestName());
						questionInstanceDto.getQuestionMapperInstance().setSectionName(sectionInstanceDto.getSection().getSectionName());
						questionInstanceDto.getQuestionMapperInstance().setUser(user.getEmail());
						questionMapperInstances.add(questionInstanceDto.getQuestionMapperInstance());
						if(questionInstanceDto.getQuestionMapperInstance().getCorrect()) {
							correctAnswersPerSection ++;
						}
						
						if(!questionInstanceDto.getQuestionMapperInstance().getAnswered()) {
							noOfQuestionsNotAnswered ++;
						}
					}
					
					sectionInstance.setResults("total-"+totalSectionQuestions+",correct-"+correctAnswersPerSection);
					sectionInstance.setNoOfQuestionsNotAnswered(noOfQuestionsNotAnswered);
		 			//sectionInstanceService.saveSection(sectionInstance, questionMapperInstances);
					/**
	 				 * Save only section instance
	 				 */
	 				//sectionInstanceService.saveSectionOnly(sectionInstance);
					sectionInstanceService.addOnlyIfAnswersNotPresent(sectionInstance, questionMapperInstances);
	 				/**
	 				 * End Save only section instance
	 				 */
		 			
		 			sectionInstanceDto.setNoOfQuestions(totalSectionQuestions);
		 			sectionInstanceDto.setTotalCorrectAnswers(correctAnswersPerSection);
		 			sectionInstanceDto.setNoOfQuestionsNotAnswered(noOfQuestionsNotAnswered);
		 		}
		 	}
	 }
	 
	 private void setValuesInSession(SectionInstanceDto currentSection, List<SectionInstanceDto> sectionInstanceDtos) {
		 for(SectionInstanceDto dto : sectionInstanceDtos) {
			 if(dto.getSection().getSectionName().equals(currentSection.getSection().getSectionName())) {
				 Mapper mapper = new DozerBeanMapper();
				 mapper.map(currentSection, dto);
				 break;
			 }
		 }
	 }
	 
	 private SectionInstanceDto getCurrentSection(Long qMapperId,  List<SectionInstanceDto> sectionInstanceDtos, SectionInstanceDto currentSection){
		 QuestionMapper mapper = questionMapperRep.findById(qMapperId).get();
		 for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos){
			 if(sectionInstanceDto.getSection().getSectionName().equals(mapper.getSectionName())){
				 return sectionInstanceDto;
			 }
		 }
		return currentSection; 
	 }
	 
	 @RequestMapping(value = "/nextQuestion", method = RequestMethod.POST)
	  public ModelAndView nextQuestion(@RequestParam(name="imageVideoData", required=false) MultipartFile imageVideoData, @RequestParam String questionId, @RequestParam String timeCounter,HttpServletRequest request, HttpServletResponse response,@ModelAttribute("currentQuestion") QuestionInstanceDto currentQuestion) throws Exception {
		// ModelAndView model= new ModelAndView("test_cognizant");
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 ModelAndView model;
		 if(test.getFullStackTest() != null && test.getFullStackTest()){
		 		//model= new ModelAndView("test_fstk");
			 model= new ModelAndView("test_fstk_new");
		 	}
		 	else{
		 		model= new ModelAndView("test_cognizant");
//		 		model= new ModelAndView("test_new");
		 	}
		 
		 if(imageVideoData != null &&  imageVideoData.getSize() != 0){
			 String baseFolder = "";
			 if(currentQuestion.getType().equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 baseFolder = propertyConfig.getImageQuestionFolder();
				 
			 }
			 else if(currentQuestion.getType().equals(QuestionType.VIDEO_UPLOAD_BY_USER.getType())){
				 baseFolder = propertyConfig.getVideoQuestionFolder();
			 }
			 
			 Long questionMapperId = currentQuestion.getQuestionMapperId();
			 QuestionMapper mapper = questionMapperRep.findById(questionMapperId).get();
			 
			 baseFolder += File.separator+user.getEmail()+File.separator+"test"+test.getId()+"qid"+mapper.getQuestion().getId(); 
			 //+File.separator+imageVideoData.getName()
			 File file = new File(baseFolder);
			 file.mkdirs();
			 File actual = new File(baseFolder+File.separator+imageVideoData.getOriginalFilename());
			 FileUtils.copyInputStreamToFile(imageVideoData.getInputStream(), actual);
			 
			
			 if(currentQuestion.getType().equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 currentQuestion.setImageUploadUrl(propertyConfig.getFileServerWebUrl()+"/images/"+user.getEmail()+"/test"+test.getId()+"qid"+mapper.getQuestion().getId()+"/"+imageVideoData.getOriginalFilename());
			 }
			 else{
				 currentQuestion.setVideoUploadUrl(propertyConfig.getFileServerWebUrl()+"/videos/"+user.getEmail()+"/test"+test.getId()+"qid"+mapper.getQuestion().getId()+"/"+imageVideoData.getOriginalFilename());
			 }
			 
			// FileUtils.
		 }
		 
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		 SectionInstanceDto currentSection = (SectionInstanceDto) request.getSession().getAttribute("currentSection");
		 /**
		  * Trying to solve john doe prob
		  */
		 currentSection = getCurrentSection(Long.valueOf(questionId), sectionInstanceDtos, currentSection);
		 if(currentSection.getQuestionInstanceDtos().size() == 0){
			 populateWithQuestions(currentSection, test.getTestName(), currentSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
		 }
		 request.getSession().setAttribute("currentSection", currentSection);
		 /**
		  * End john doe prob
		  */
		 
		//just in case a Q is of coding type value that comes from jsp has \r characters.so removng them so they can be rendered next time
		 	if(currentQuestion.getCode() != null){
		 		 currentQuestion.setCode(currentQuestion.getCode().replaceAll("\r", ""));
		 		 String rep = "\\\\n";
		 		 String rept = "\\\\t";
		 		currentQuestion.setCode(currentQuestion.getCode().replaceAll("\n", rep));
		 		currentQuestion.setCode(currentQuestion.getCode().replaceAll("\t", rept));
		 	}
		
		 setAnswers(request, currentSection, currentQuestion, questionId, false);
		// setValuesInSession(currentSection, sectionInstanceDtos);
		
		QuestionSequence questionSequence = new QuestionSequence(currentSection.getQuestionInstanceDtos());
		SectionSequence sectionSequence = new SectionSequence(sectionInstanceDtos);
		
		currentQuestion = questionSequence.nextQuestion(Long.valueOf(questionId));
		
		if(currentQuestion == null) {
			/**
			 * That means we are navigating into next section. Before doing that lets save the current section
			 */
			/**
			 * In Question persistence - this may not be needed. We will save sections at submit
			 */
			//saveSection(currentSection, request);
			/**
			 * End In Question persistence - this may not be needed. We will save sections at submit
			 */
			
			SectionInstanceDto nextSection = sectionSequence.nextSection(currentSection.getSection().getSectionName());
		
			if(nextSection != null) {
				synchronized(this){
				nextSection = populateWithQuestions(nextSection, test.getTestName(), nextSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				}
				//currentSection.getQuestionInstanceDtos().clear();
				currentQuestion = nextSection.getQuestionInstanceDtos().get(0);
				if(currentQuestion.getCode() == null || currentQuestion.getCode().trim().length() == 0){
		 			currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode());
		 		}
				
				/**
				 * Making sure next and prev button behave for the first and last event
				 */
				questionSequence = new QuestionSequence(nextSection.getQuestionInstanceDtos());
				if(isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
					nextSection.setLast(true);
				 }
				else {
					nextSection.setLast(false);
				}
				 
				 if(isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
					 nextSection.setFirst(true);
				 }
				 else {
					 nextSection.setFirst(false);
				 }
				
				 model.addObject("currentSection", nextSection);
				 nextSection.setCurrent(true);
				 currentSection.setCurrent(false);
				 model.addObject("currentQuestion", currentQuestion);
				 model.addObject("confidenceFlag", test.getConsiderConfidence());
				 request.getSession().setAttribute("currentSection", nextSection);
				 putMiscellaneousInfoInModel(model, request);
				 processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
				 /**
				  * Get the fullstack for Q if type is full stack.
				  * 
				  */
				 if(!currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.NONE.getStack())){
					 setWorkspaceIDEForFullStackQ(request, currentQuestion);
				 }
				 /**
				  * End full stack check
				  */
				 return model;
			}
			else {
				//Save test and generate result
				//questionSequence.nextQuestion(Long.valueOf(questionId));
				System.out.println("user "+user.getEmail()+" testname "+test.getTestName()+" current section name  "+currentSection.getSection().getSectionName()+" ques id "+questionId +" currentSection.getQuestionInstanceDtos().size "+currentSection.getQuestionInstanceDtos().size());
				questionSequence.scan(Long.valueOf(questionId));
				logger.info("user "+user.getEmail()+" testname "+test.getTestName()+" current section name  "+currentSection.getSection().getSectionName()+" sectionInstanceDtos size "+sectionInstanceDtos.size()+"ques id "+questionId);
				logger.debug("user "+user.getEmail()+" testname "+test.getTestName()+" current section name  "+currentSection.getSection().getSectionName()+" sectionInstanceDtos size "+sectionInstanceDtos.size()+"ques id "+questionId);
				for(SectionInstanceDto dto : sectionInstanceDtos){
					logger.info("section name "+dto.getSection().getSectionName());
					logger.debug("section name "+dto.getSection().getSectionName());		
				
				}
				model= new ModelAndView("report");
				putMiscellaneousInfoInModel(model, request);
				
				return model;
			}
		}
		else {
			 if(isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
				 currentSection.setLast(true);
			 }
			 else {
				 currentSection.setLast(false);
			 }
			 
			 if(isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
				 currentSection.setFirst(true);
			 }
			 else {
				 currentSection.setFirst(false);
			 }
//			 if(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() != null && currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
//			 		currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode().replaceAll("\r", ""));
//			 	}
			 model.addObject("currentSection", currentSection);
			 
			 if(currentQuestion.getCode() == null || currentQuestion.getCode().trim().length() == 0){
		 			currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode());
		 		}
			 model.addObject("currentQuestion", currentQuestion);
			 request.getSession().setAttribute("currentSection", currentSection);
			 putMiscellaneousInfoInModel(model, request);
			 setTimeInCounter(request, Long.valueOf(timeCounter));
			 processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
			 /**
			  * Get the fullstack for Q if type is full stack.
			  * 
			  */
			 if(!currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.NONE.getStack())){
				 setWorkspaceIDEForFullStackQ(request, currentQuestion);
			 }
			 /**
			  * End full stack check
			  */
			 model.addObject("confidenceFlag", test.getConsiderConfidence());
			 return model;
		}
		    
	 }
	 
	 private Boolean isQuestionLast(QuestionInstanceDto current, QuestionSequence questionSequence, SectionSequence sectionSequence) {
		 if(sectionSequence.nextSection(current.getQuestionMapperInstance().getQuestionMapper().getSectionName() )== null) {
			 //means this is the last section
			 if(questionSequence.nextQuestion(current.getQuestionMapperInstance().getQuestionMapper().getId()) == null) {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 private Boolean isQuestionFirst(QuestionInstanceDto current, QuestionSequence questionSequence, SectionSequence sectionSequence) {
		 if(sectionSequence.prevSection(current.getQuestionMapperInstance().getQuestionMapper().getSectionName() )== null) {
			 //means this is the first section
			 if(questionSequence.previousQuestion(current.getQuestionMapperInstance().getQuestionMapper().getId()) == null) {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 private void setWorkspaceIDEForFullStackQ(HttpServletRequest request, QuestionInstanceDto currentQuestion) throws Exception{
		logger.info("in setWorkspaceIDEForFullStackQ "+currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack());
		String stack = currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack();
		if(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.JAVA_FULLSTACK.getStack()) || currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.PHP_FULLSTACK.getStack()) || currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.ANGULARJS_FULLSTACK.getStack()) || currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.JAVASCRIPT_FULLSTACK.getStack()) || currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.DOTNET_FULLSTACK.getStack())  || currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.JAVA_MONGODB.getStack()) || stack.equalsIgnoreCase(FullStackOptions.DOTNET_MONGODB.getStack())){
			
			User user = (User) request.getSession().getAttribute("user");
			String fullName = user.getFirstName()+user.getLastName();
			Test test = (Test) request.getSession().getAttribute("test");
			fullName = fullName.replace(" ", "");
			String secName = currentQuestion.getQuestionMapperInstance().getQuestionMapper().getSectionName();
			//QuestionMapperInstance qms = questionMapperInstanceRep.findUniqueQuestionMapperInstanceForUser(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionText(), test.getTestName(), secName, user.getEmail(), user.getCompanyId());
			QuestionMapperInstance qms = questionMapperInstaceService.removeDublicateAndGetInstance(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionText(), test.getTestName(), secName, user.getEmail(), user.getCompanyId());
		    
			
			//System.out.println("qms is  "+qms);
			String workspace = "";
		   // System.out.println("Generating workspace..........for "+user.getEmail());
		    if(qms == null){
		    	System.out.println("gen workspace now");
		    	WorkspaceResponse workspaceResponse = generateWorkspace(user, currentQuestion, test.getId(), currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getId(), fullName, currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack());
		    	System.out.println("gen workspace now done");
		    	workspace = workspaceResponse.getLinks().getIde();
		    	qms = currentQuestion.getQuestionMapperInstance();
		 		qms.setCompanyId(test.getCompanyId());
		 		qms.setQuestionText(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionText());
		 		qms.setTestName(test.getTestName());
		 		qms.setSectionName(secName);
		 		qms.setUser(user.getEmail());
		 		qms.setCreateDate(new Date());
		 		qms.setCompanyName(test.getCompanyName());
		 		qms.setWorkspaceUrl(workspace);
		 		qms.setWorkSpaceId(workspaceResponse.getId());
		 		questionMapperInstanceRep.save(qms);
		    }
		    else{
		    	if(qms.getWorkspaceUrl() == null || qms.getWorkspaceUrl().trim().length() == 0){
			    	// if(stackName.equals("Java")){
		    		//workspace = generateWorkspace(currentQuestion, test.getId(), currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getId(), fullName);
		    		System.out.println("gen workspace now1");
		    		WorkspaceResponse workspaceResponse = generateWorkspace(user, currentQuestion, test.getId(), currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getId(), fullName, currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack());
		    		System.out.println("gen workspace now done1");
		    		workspace = workspaceResponse.getLinks().getIde();
		    		//return workspaceResponse.getLinks().getIde();
			    	qms.setWorkSpaceId(workspaceResponse.getId());
		    		qms.setWorkspaceUrl(workspace);
		    		qms.setUpdateDate(new Date());
		    		questionMapperInstanceRep.save(qms);
			 	// }
			    }
				else{
					workspace = qms.getWorkspaceUrl();
				}
		    }
		   // System.out.println("workspace yurl..........for "+qms.getWorkspaceUrl());
		    currentQuestion.setQuestionMapperInstance(qms);
		    currentQuestion.getQuestionMapperInstance().setWorkspaceUrl(workspace);
		    
		}
		
	 	
	 }
	 
	 private WorkspaceResponse generateWorkspace(User user, QuestionInstanceDto currentQuestion, Long tid, Long qid, String fullName, FullStackOptions fullStackOptions) throws Exception{
		 System.out.println("fullStackOptions.getStack() "+fullStackOptions.getStack());
		 logger.info("in generate workspace "+fullStackOptions.getStack());
		 String json = "";
		 Question qs = currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion();
			if(fullStackOptions == null || fullStackOptions.getStack().equals(FullStackOptions.JAVA_FULLSTACK.getStack())){
				System.out.println("generatin workspace for java.... "+FullStackWorkspaceJsonTemplate.JAVA_STACK);
				
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.JAVA_STACK));
				
				
				
			}
			else if(fullStackOptions.getStack().equals(FullStackOptions.PHP_FULLSTACK.getStack())){
				System.out.println("generatin workspace for php");
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.PHP_STACK));
				
			}
			else if(fullStackOptions.getStack().equals(FullStackOptions.ANGULARJS_FULLSTACK.getStack())){
				//System.out.println("generatin workspace for angular");
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.ANGULAR_STACK));
				
			}
			else if(fullStackOptions.getStack().equals(FullStackOptions.DOTNET_FULLSTACK.getStack())){
				//System.out.println("generatin workspace for dot net scharpqqqqqqqqqqqqqqqqq");
				//System.out.println("222222222222");
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.DOTNET_STACK));
				
			}
			else if(fullStackOptions.getStack().equals(FullStackOptions.JAVA_MONGODB.getStack())){
				System.out.println("generatin workspace for mongodb mooooooooooooooooo");
				//System.out.println("222222222222");
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.JAVA_MONGODB));
				json = json.replace("TIME_STAMP", ""+System.currentTimeMillis());
			}//DOTNET_MONGODB
			else if(fullStackOptions.getStack().equals(FullStackOptions.DOTNET_MONGODB.getStack())){
				System.out.println("generatin workspace for mongodb dotnet....");
				//System.out.println("222222222222");
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.DOTNET_MONGODB));
				json = json.replace("TIME_STAMP", ""+System.currentTimeMillis());
			}
			else{
			//	System.out.println("generatin workspace for others");
				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+FullStackWorkspaceJsonTemplate.JAVA_STACK));
			}
		 
			if(qs.getFullStackGitHupCodeUrl() != null){
				json = json.replace("${GIT_CODEBASE_LINK}", qs.getFullStackGitHupCodeUrl());
			}
			
	 		//String qid = ""+currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getId();
	 		json = json.replace("${APP_USER}", fullName+"-"+tid+"-"+qid+"-"+System.currentTimeMillis());
	 		Integer hc = fullName.hashCode();
	 			if(hc < 0 ){
	 				hc = hc * (-1);
	 				//System.out.println("hash code is "+hc);
	 			}
	 		json = json.replace("${APP_USER_CODE}", "db"+hc+"-"+System.currentTimeMillis());
	 		//json = json.replace("${APP_USER}", "a01");
	 		json = json.replace("${APP_DESC}", "Skeleton Code............................Project\n\n\n.........");
	 		EclipseCheService eclipseCheService = new EclipseCheService();
	 		String url = cheService.getApiURLAndSaveDetailsForCluster(user.getCompanyId());
	 		if(url == null){
	 			throw new AssessmentGenericException("NO_CLUSTER_AVAILABLE_CHECK_CLUSTER_ADMIN");
	 		}
	 		eclipseCheService.setPosition(url.length());
	 		url += "/api/workspace/devfile?start-after-create=false&namespace=che";
	 		eclipseCheService.setUrl(url);
	 		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
	 		
	 		/**
	 		 * Remove the problem.txt input (mappint between testcase and expected result file) from parent location to 1 level above
	 		 */
//	 		 try {
//				String baseCodePath = propertyConfig.getFullStackCodeLocation();
//				 String fin = workspaceResponse.getLinks().getIde() != null ? (workspaceResponse.getLinks().getIde().substring(workspaceResponse.getLinks().getIde().lastIndexOf("/")+1, workspaceResponse.getLinks().getIde().length())):"";
//				 //System.out.println("fin is "+fin);
//				 String path = baseCodePath + File.separator + workspaceResponse.getId() + File.separator + fin;
//				 path += File.separator + "problem.properties";
//				 //System.out.println("problem file location "+path);
//				// System.out.println("desst folder loc "+baseCodePath + File.separator + workspaceResponse.getId() + File.separator);
//				 FileUtils.moveFileToDirectory(new File(path), new File(baseCodePath + File.separator + workspaceResponse.getId() + File.separator), false);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error("can not move problem file", e);
//				//System.out.println("can not move problem file"+e.getClass());
//			}
	 		//return workspaceResponse.getLinks().getIde();
	 		return workspaceResponse;
	 }
	 
//	 private WorkspaceResponse generateWorkspace(QuestionInstanceDto currentQuestion, Long tid, Long qid, String fullName, FullStackOptions fullStackOptions) throws Exception{
//		
//		 logger.info("in generate workspace "+fullStackOptions.getStack());
//		 String json = "";
//			if(fullStackOptions == null || fullStackOptions.getStack().equals(FullStackOptions.JAVA_FULLSTACK.getStack())){
//				System.out.println("generatin workspace for java");
//				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+"Java_FullStack.json"));
//			}
//			else if(fullStackOptions.getStack().equals(FullStackOptions.PHP_FULLSTACK.getStack())){
//				System.out.println("generatin workspace for php");
//				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+"PHP_MySQL.json"));
//			}
//			else if(fullStackOptions.getStack().equals(FullStackOptions.ANGULARJS_FULLSTACK.getStack())){
//				System.out.println("generatin workspace for angular");
//				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+"ANGULAR_JAVASCRIPT_MYSQL.json"));
//			}
//			else if(fullStackOptions.getStack().equals(FullStackOptions.DOTNET_FULLSTACK.getStack())){
//				System.out.println("generatin workspace for dot net scharp");
//				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+"c-sharp.json"));
//			}
//			else{
//				System.out.println("generatin workspace for others");
//				json = FileUtils.readFileToString(new File("assessment"+File.separator+"eclipseChe"+File.separator+"Java_FullStack.json"));
//			}
//		 
//		 
//	 		//String qid = ""+currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getId();
//	 		json = json.replace("${APP_USER}", fullName+"-"+tid+"-"+qid+"-"+System.currentTimeMillis());
//	 		//json = json.replace("${APP_USER}", "a01");
//	 		json = json.replace("${APP_DESC}", "Skeleton Code............................Project\n\n\n.........");
//	 		EclipseCheService eclipseCheService = new EclipseCheService();
//	 		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
//	 		
//	 		/**
//	 		 * Remove the problem.txt input (mappint between testcase and expected result file) from parent location to 1 level above
//	 		 */
//	 		 try {
//				String baseCodePath = propertyConfig.getFullStackCodeLocation();
//				 String fin = workspaceResponse.getLinks().getIde() != null ? (workspaceResponse.getLinks().getIde().substring(workspaceResponse.getLinks().getIde().lastIndexOf("/")+1, workspaceResponse.getLinks().getIde().length())):"";
//				 System.out.println("fin is "+fin);
//				 String path = baseCodePath + File.separator + workspaceResponse.getId() + File.separator + fin;
//				 path += File.separator + "problem.properties";
//				 System.out.println("problem file location "+path);
//				 System.out.println("desst folder loc "+baseCodePath + File.separator + workspaceResponse.getId() + File.separator);
//				 FileUtils.moveFileToDirectory(new File(path), new File(baseCodePath + File.separator + workspaceResponse.getId() + File.separator), false);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error("can not move problem file", e);
//				System.out.println("can not move problem file"+e.getClass());
//			}
//	 		//return workspaceResponse.getLinks().getIde();
//	 		return workspaceResponse;
//	 }
	 
	 
	 @RequestMapping(value = "/prevQuestion", method = RequestMethod.POST)
	  public ModelAndView prevQuestion(@RequestParam(name="imageVideoData", required=false) MultipartFile imageVideoData,@RequestParam String questionId, @RequestParam String timeCounter,HttpServletRequest request, HttpServletResponse response,@ModelAttribute("currentQuestion") QuestionInstanceDto currentQuestion) throws Exception {
		 //ModelAndView model= new ModelAndView("test_cognizant");
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 ModelAndView model;
		 if(test.getFullStackTest() != null && test.getFullStackTest()){
		 		//model= new ModelAndView("test_fstk");
			 model= new ModelAndView("test_fstk_new");
		 	}
		 	else{
		 		model= new ModelAndView("test_cognizant");
//		 		model= new ModelAndView("test_new");
		 	}
		 
		 if(imageVideoData != null &&  imageVideoData.getSize() != 0){
			 String baseFolder = "";
			 if(currentQuestion.getType().equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 baseFolder = propertyConfig.getImageQuestionFolder();
				 
			 }
			 else if(currentQuestion.getType().equals(QuestionType.VIDEO_UPLOAD_BY_USER.getType())){
				 baseFolder = propertyConfig.getVideoQuestionFolder();
			 }
			 
			 Long questionMapperId = currentQuestion.getQuestionMapperId();
			 QuestionMapper mapper = questionMapperRep.findById(questionMapperId).get();
			 
			 baseFolder += File.separator+user.getEmail()+File.separator+"test"+test.getId()+"qid"+mapper.getQuestion().getId(); 
			 //+File.separator+imageVideoData.getName()
			 File file = new File(baseFolder);
			 file.mkdirs();
			 File actual = new File(baseFolder+File.separator+imageVideoData.getOriginalFilename());
			 FileUtils.copyInputStreamToFile(imageVideoData.getInputStream(), actual);
			 
			
			 if(currentQuestion.getType().equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 currentQuestion.setImageUploadUrl(propertyConfig.getFileServerWebUrl()+"/images/"+user.getEmail()+"/test"+test.getId()+"qid"+mapper.getQuestion().getId()+"/"+imageVideoData.getOriginalFilename());
			 }
			 else{
				 currentQuestion.setVideoUploadUrl(propertyConfig.getFileServerWebUrl()+"/videos/"+user.getEmail()+"/test"+test.getId()+"qid"+mapper.getQuestion().getId()+"/"+imageVideoData.getOriginalFilename());
			 }
			 
			// FileUtils.
		 }
		 
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		 
		SectionInstanceDto currentSection = (SectionInstanceDto) request.getSession().getAttribute("currentSection");
		//just in case a Q is of coding type value that comes from jsp has \r characters.so removng them so they can be rendered next time
		if(currentQuestion.getCode() != null){
	 		 currentQuestion.setCode(currentQuestion.getCode().replaceAll("\r", ""));
	 		 String rep = "\\\\n";
	 		 String rept = "\\\\t";
	 		currentQuestion.setCode(currentQuestion.getCode().replaceAll("\n", rep));
	 		currentQuestion.setCode(currentQuestion.getCode().replaceAll("\t", rept));
	 	}
		setAnswers(request, currentSection, currentQuestion, questionId, false);
		//setValuesInSession(currentSection, sectionInstanceDtos);
		
		QuestionSequence questionSequence = new QuestionSequence(currentSection.getQuestionInstanceDtos());
		SectionSequence sectionSequence = new SectionSequence(sectionInstanceDtos);
		currentQuestion = questionSequence.previousQuestion(Long.valueOf(questionId));
		if(currentQuestion == null) {
			
			
			SectionInstanceDto previousSection = sectionSequence.prevSection(currentSection.getSection().getSectionName());
			
			if(previousSection != null) {
				previousSection = populateWithQuestions(previousSection, test.getTestName(), previousSection.getSection().getSectionName(), user.getCompanyId(), user.getEmail());
				//currentSection.getQuestionInstanceDtos().clear();
				currentQuestion = previousSection.getQuestionInstanceDtos().get(previousSection.getQuestionInstanceDtos().size()-1);
				 model.addObject("currentSection", previousSection);
				 previousSection.setCurrent(true);
				 currentSection.setCurrent(false);
				 /**
					 * Making sure next and prev button behave for the first and last event
					 */
				 questionSequence = new QuestionSequence(previousSection.getQuestionInstanceDtos());//now get the last question from the prev section
					if(isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
						previousSection.setLast(true);
					 }
					else {
						previousSection.setLast(false);
					}
					 
					 if(isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
						 previousSection.setFirst(true);
					 }
					 else {
						 previousSection.setFirst(false);
					 }
					 
				 if(currentQuestion.getCode() == null || currentQuestion.getCode().trim().length() == 0){
			 			currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode());
			 	 }
				 model.addObject("currentQuestion", currentQuestion);
				 request.getSession().setAttribute("currentSection", previousSection);
				 putMiscellaneousInfoInModel(model, request);
				 processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
				 /**
				  * Get the fullstack for Q if type is full stack.
				  * 
				  */
				 if(!currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.NONE.getStack())){
					 setWorkspaceIDEForFullStackQ(request, currentQuestion);
				 }
				 /**
				  * End full stack check
				  */
				 model.addObject("confidenceFlag", test.getConsiderConfidence());
				 return model;
			}
			else {
				//Save test and generate result
				//model= new ModelAndView("intro");
				model= new ModelAndView("intro_new");
				putMiscellaneousInfoInModel(model, request);
				return model;
			}
		}
		else {
			 model.addObject("currentSection", currentSection);
//			 if(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() != null && currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
//				 currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode().replaceAll("\r", ""));
//				 //currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode().replaceAll("\\r\\n|\\r|\\n", "<br />"));
//			 	}
			 if(currentQuestion.getCode() == null || currentQuestion.getCode().trim().length() == 0){
		 			currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode());
		 		}
			 model.addObject("currentQuestion", currentQuestion);
			 if(isQuestionLast(currentQuestion, questionSequence, sectionSequence)) {
				 currentSection.setLast(true);
			 }
			 else {
				 currentSection.setLast(false);
			 }
			 
			 if(isQuestionFirst(currentQuestion, questionSequence, sectionSequence)) {
				 currentSection.setFirst(true);
			 }
			 else {
				 currentSection.setFirst(false);
			 }
			 
			 request.getSession().setAttribute("currentSection", currentSection);
			 putMiscellaneousInfoInModel(model, request);
			 setTimeInCounter(request, Long.valueOf(timeCounter));
			 processPercentages(model, sectionInstanceDtos, test.getTotalMarks());
			 /**
			  * Get the fullstack for Q if type is full stack.
			  * 
			  */
			 if(!currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getFullstack().getStack().equals(FullStackOptions.NONE.getStack())){
				 setWorkspaceIDEForFullStackQ(request, currentQuestion);
			 }
			 /**
			  * End full stack check
			  */
			 model.addObject("confidenceFlag", test.getConsiderConfidence());
			 return model;
		}
		    
	 }
	 
	 @RequestMapping(value = "/submitTest", method = RequestMethod.POST)
	  public ModelAndView submitTest(@RequestParam(name="imageVideoData", required=false) MultipartFile imageVideoData, @RequestParam String questionId, @RequestParam String timeCounter, HttpServletRequest request, HttpServletResponse response,@ModelAttribute("currentQuestion") QuestionInstanceDto currentQuestion) throws IOException {
//		 Boolean submitted = (Boolean) request.getSession().getAttribute("submitted");
//		 	if(submitted != null && (submitted)){
//		 		request.getSession().invalidate();
//		 		 ModelAndView mav = new ModelAndView("login_new");
//		 	    User user = new User();
//		 	   // user.setEmail("system@iiht.com");
//		 	  //  user.setPassword("1234");
//		 	 //   user.setCompanyName("IIHT");
//		 	    mav.addObject("user", user);
//		 	    mav.addObject("message", "You have already submitted the test. Your results have already been dispatched by email to Test Admin");// later put it as label
//				mav.addObject("msgtype", "Information");
//		 	    return mav;
//		 	}
		 ModelAndView model= new ModelAndView("test_cognizant");
//		 ModelAndView model= new ModelAndView("test_new");
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 model.addObject("sectionInstanceDtos", sectionInstanceDtos);
		 SectionInstanceDto currentSection = (SectionInstanceDto) request.getSession().getAttribute("currentSection");
		 
		 if(imageVideoData != null &&  imageVideoData.getSize() != 0){
			 String baseFolder = "";
			 if(currentQuestion.getType().equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 baseFolder = propertyConfig.getImageQuestionFolder();
				 
			 }
			 else if(currentQuestion.getType().equals(QuestionType.VIDEO_UPLOAD_BY_USER.getType())){
				 baseFolder = propertyConfig.getVideoQuestionFolder();
			 }
			 
			 Long questionMapperId = currentQuestion.getQuestionMapperId();
			 QuestionMapper mapper = questionMapperRep.findById(questionMapperId).get();
			 
			 baseFolder += File.separator+user.getEmail()+File.separator+"test"+test.getId()+"qid"+mapper.getQuestion().getId(); 
			 //+File.separator+imageVideoData.getName()
			 File file = new File(baseFolder);
			 file.mkdirs();
			 File actual = new File(baseFolder+File.separator+imageVideoData.getOriginalFilename());
			 FileUtils.copyInputStreamToFile(imageVideoData.getInputStream(), actual);
			 
			
			 if(currentQuestion.getType().equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())){
				 currentQuestion.setImageUploadUrl(propertyConfig.getFileServerWebUrl()+"/images/"+user.getEmail()+"/test"+test.getId()+"qid"+mapper.getQuestion().getId()+"/"+imageVideoData.getOriginalFilename());
			 }
			 else{
				 currentQuestion.setVideoUploadUrl(propertyConfig.getFileServerWebUrl()+"/videos/"+user.getEmail()+"/test"+test.getId()+"qid"+mapper.getQuestion().getId()+"/"+imageVideoData.getOriginalFilename());
			 }
			 
			// FileUtils.
		 }
		 //if(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType() != null && currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
		 	//	currentQuestion.setCode(currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getInputCode().replaceAll("\\r\\n|\\r|\\n", "<br />"));
//			if(currentQuestion.getCode() != null){
//				currentQuestion.setCode(currentQuestion.getCode().replaceAll("\r", ""));
//				String rep = "\\\\n";
//		 		String rept = "\\\\t";
//		 		currentQuestion.setCode(currentQuestion.getCode().replaceAll("\n", rep));
//		 		currentQuestion.setCode(currentQuestion.getCode().replaceAll("\t", rept));
//			}
		 	
		// }
		 String confidencePercent = "NA";
		 float totQs = 0;
		 float totConfidence = 0;
		 setAnswers(request, currentSection, currentQuestion, questionId, true);
		 //currentQuestion.getQuestionMapperInstance().getQuestionMapper().getQuestion().getRightChoices().split(",").length
		 Boolean codingAssignments = false;
		 	for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
		 		saveSection(sectionInstanceDto, request);
		 		if(test.getConsiderConfidence() != null && test.getConsiderConfidence()){
		 			totQs += sectionInstanceDto.getNoOfQuestions();
		 				for(QuestionInstanceDto dto : sectionInstanceDto.getQuestionInstanceDtos()){
		 					if(dto.getConfidence() != null && dto.getConfidence()){
		 						totConfidence += 1;
		 					}
		 					
		 					if(dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
		 						if(dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getLanguage().getLanguage().equals(ProgrammingLanguage.MYSQL.getLanguage())){
		 							//System.out.println("student controller coding assignment "+false);
		 							codingAssignments = false;
		 						}
		 						else{
		 							//System.out.println("student controller coding assignment "+true);
		 							codingAssignments = true;
		 						}
		 						//codingAssignments = true;
		 					}
		 				}
		 			 
		 		}
		 	}
		 	
		 	DecimalFormat df = new DecimalFormat("##.##");
		 	if(test.getConsiderConfidence() != null && test.getConsiderConfidence()){
		 		confidencePercent = df.format(100 * ((float)totConfidence/totQs));
		 	}
		 	
		 	UserTestSession userTestSession = new UserTestSession();
			userTestSession.setCompanyId(user.getCompanyId());
			userTestSession.setCompanyName(user.getCompanyName());
			
			userTestSession.setUser(user.getEmail());
			userTestSession.setTest(test);
			userTestSession.setTestName(test.getTestName());
			userTestSession.setComplete(true);
			userTestSession.setCollegeName(user.getCollegeName());
			userTestSession.setGrade(user.getGrade());
			userTestSession.setClassifier(user.getClassifier());
			userTestSession.setFirstName(user.getFirstName());
			userTestSession.setLastName(user.getLastName());
			
			/**
		 	 * Add subjective type to usertestsession
		 	 */
		 	
			 	for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			 		for(QuestionInstanceDto dto : sectionInstanceDto.getQuestionInstanceDtos()){
			 			String tp = dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType();
			 			if(tp.equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType()) || tp.equals(QuestionType.VIDEO_UPLOAD_BY_USER.getType()) || tp.equals(QuestionType.SUBJECTIVE_TEXT.getType())){
			 				userTestSession.setSubjective(true);
			 			}
			 		}
			 	}
		 	/**
		 	 * End Add subjective type to usertestsession
		 	 */
			
			
			/**
			 * Store sectio results in user test session
			 */
			
			 
			 String sectionsQuestionsNotAnswered = "";
			 for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
				 userTestSession.setSectionResults((userTestSession.getSectionResults() == null?"":userTestSession.getSectionResults())+", "+sectionInstanceDto.getSection().getSectionName()+"-"+df.format((new Float(sectionInstanceDto.getTotalCorrectAnswers()) / new Float(sectionInstanceDto.getNoOfQuestions())) * 100 ));
				 userTestSession.setSectionsNoOfQuestionsNotAnswered((userTestSession.getSectionsNoOfQuestionsNotAnswered() == null?"": userTestSession.getSectionsNoOfQuestionsNotAnswered())+", "+sectionInstanceDto.getSection().getSectionName()+"-"+sectionInstanceDto.getNoOfQuestionsNotAnswered());
			 }
			 if(userTestSession.getSectionResults().startsWith(",")) {
				 userTestSession.setSectionResults( userTestSession.getSectionResults().replaceFirst(",", ""));
			 }
			 
			 if(userTestSession.getSectionsNoOfQuestionsNotAnswered() != null && userTestSession.getSectionsNoOfQuestionsNotAnswered().startsWith(",")) {
				 userTestSession.setSectionsNoOfQuestionsNotAnswered(userTestSession.getSectionsNoOfQuestionsNotAnswered().replaceFirst(",", ""));
			 }
			/**
			 * End storing section level results info
			 */
			 StudentTestForm studentTestForm = (StudentTestForm) request.getSession().getAttribute("studentTestForm");
			 userTestSession.setTestInviteSent(studentTestForm.getTestInviteSent());
			 userTestSession.setSharedDirect(studentTestForm.getSharedDirect());
			 Date createDate = (Date) request.getSession().getAttribute("testStartDate");
			 userTestSession.setCreateDate(createDate);
			 userTestSession.setUpdateDate(new Date());
			 Integer noOfAttempts = (Integer)request.getSession().getAttribute("noOfAttempts");
			 userTestSession.setNoOfAttempts(noOfAttempts);
			 studentTestForm.setNoOfAttempts(noOfAttempts);
			 /**
			  * Get the weighted score for the entire test
			  */
			 //Float testWeightedScore = questionMapperInstaceService.getWeightedTestScore(user.getEmail(), test.getTestName(), user.getCompanyId());
			// userTestSession.setWeightedScorePercentage(testWeightedScore);
			// that code in userTestSessionService.saveOrUpdate method now
			
			 /**
			  * End set weighted score
			  */
			 /**
			  * Save no of non compliances on UserTestSession object
			  */
			 
			 UserNonCompliance nonCompliance = userNonComplianceService.findNonCompliance(user.getEmail(), test.getTestName(), user.getCompanyId());
			 	if(nonCompliance == null){
			 		userTestSession.setNoOfNonCompliances(0);
			 	}
			 	else{
			 		userTestSession.setNoOfNonCompliances(nonCompliance.getNoOfNonCompliances() == null?0:nonCompliance.getNoOfNonCompliances());
			 	}
			 /**
			  * End Save no of non compliances on UserTestSession object
			  */
			 userTestSession = userTestSessionService.saveOrUpdate(userTestSession);
			
		//	studentTestForm.setNoOfAttempts(userTestSession.getNoOfAttempts());
		 
		 putMiscellaneousInfoInModel(model, request);
		 setTimeInCounter(request, Long.valueOf(timeCounter));
		 try {
			request.getSession().setAttribute("submitted", true);
			//String rows = sendResultsEmail(request, userTestSession);
			sendResultsEmail(request, userTestSession);
			String rows = compileRows(request);
			model= new ModelAndView("studentTestCompletion");
			model.addObject("rows", rows);
			model.addObject("showResults", test.getSentToStudent());
			model.addObject("justification", test.getJustification());
			model.addObject("studentTestForm", studentTestForm);
				if(test.getSentToStudent()){
					model.addObject("TOTAL_QUESTIONS", userTestSession.getTotalMarks());
					model.addObject("TOTAL_MARKS", userTestSession.getTotalMarksRecieved());
					model.addObject("PASS_PERCENTAGE", test.getPassPercent());
					model.addObject("RESULT_PERCENTAGE", userTestSession.getPercentageMarksRecieved());
					int per = Math.round(userTestSession.getPercentageMarksRecieved());
					model.addObject("RESULT_PERCENTAGE_WITHOUT_FRACTION", new Integer(per));
				//	model.addObject("STATUS", test.getPassPercent() > userTestSession.getPercentageMarksRecieved()?"Fail":"Success");
					model.addObject("STATUS", test.getPassPercent() > (userTestSession.getWeightedScorePercentage()==null?0:userTestSession.getWeightedScorePercentage())?"Fail":"Success");
					model.addObject("codingAssignments", codingAssignments);
					model = getTraitsForUserForTest(user.getEmail(), test.getCompanyId(), test.getTestName(), model);
					/**
					 * Add code for showing justification grid
					 */
					model.addObject("sectionInstanceDtos", sectionInstanceDtos);
					codingAssignmentSummaryIfAvailable(model, request);//show coding results on submission page
				}
				
				if(test.getJustification() != null && test.getJustification()){
					/**
					 * Add code for showing justification grid
					 */
					model.addObject("sectionInstanceDtos", sectionInstanceDtos);
				}
				
				if(test.getConsiderConfidence() != null && test.getConsiderConfidence()){
					//get confidence percent
					model.addObject("confidencePercent", confidencePercent);
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			String message = "Results can not be sent for "+user.getEmail()+" for test "+test.getTestName();
			EmailGenericMessageThread client = new EmailGenericMessageThread("jatin.sutaria@thev2technologies.com", "Can not send Test link email", message, propertyConfig);
			Thread th = new Thread(client);
			th.start();
		}
		 return model;
	 }
	 
	 
	 private ModelAndView getTraitsForUserForTest(String user, String companyId, String testName, ModelAndView model){
		 List<UserTrait> traits = reportsService.getUserTraits(companyId, testName, user);
		 List<UserTrait> rettraits = new ArrayList<>();
		 for(UserTrait trait : traits){
			 if(trait.getDescription() != null && trait.getDescription().trim().length() > 0){
				 rettraits.add(trait);
			 }
		 }
		if(rettraits.size() > 0){
			model.addObject("showTraits", true);
			model.addObject("traits", rettraits);
		}
		else{
			model.addObject("showTraits", false);
		}
		
		return model;
	 }
	 
	 private ModelAndView codingAssignmentSummaryIfAvailable(ModelAndView model, HttpServletRequest request){
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 List<QuestionMapperInstance> codingInstances = new ArrayList<>();
		 	for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos){
		 		List<QuestionInstanceDto> questionInstanceDtos = sectionInstanceDto.getQuestionInstanceDtos();
		 		for(QuestionInstanceDto dto : questionInstanceDtos){
		 			if(dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
		 				
		 				if(!dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getLanguage().getLanguage().equals(ProgrammingLanguage.MYSQL.getLanguage())){
		 					
		 					//System.out.println("in codingAssignmentSummaryIfAvailable +adding coding assignemnt" );
		 					//System.out.println(dto.getQuestionMapperInstance().getQuestionMapper().getQuestion().getLanguage());
		 					//System.out.println(ProgrammingLanguage.MYSQL.getLanguage());
		 					codingInstances.add(dto.getQuestionMapperInstance());
		 				}
		 				
		 			}
		 		}
		 	}
		 	model.addObject("codingInstances", codingInstances);
		 return model;
	 }
	 
	 private String decodeUserId(String encodedUri)
	 {
		// Decode data on other side, by processing encoded data
		 String decoded = new String(DatatypeConverter.parseBase64Binary(encodedUri));
		// System.out.println("user id is " + decoded);
		 return decoded;
	 }
	 
	 private String compileRows(HttpServletRequest request){
		 String table = "<tr>" + 
				 	"<td>$SECTION_NAME$</td>"+
				 	"<td>"    +
				 	"<div class=\"progress\">"+
				 	"<div class=\"progress-bar\" role=\"progressbar\" style=\"width: $per$%;\" aria-valuenow=\"25\" aria-valuemin=\"0\" aria-valuemax=\"100\">"+
				 	"$per$%</div>"+
				 	"</div>"+
			 		"</td>"+
			 		"</tr>";
		 String rows = "";
		 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
		 DecimalFormat df = new DecimalFormat("##.##");
		 for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
			 String record = table;
			 Integer per = new Integer(Math.round((float)sectionInstanceDto.getTotalCorrectAnswers().intValue() / (float)sectionInstanceDto.getNoOfQuestions().intValue() * 100)) ;
			 record = record.replace("$SECTION_NAME$", sectionInstanceDto.getSection().getSectionName());
			 record = record.replace("$per$", df.format(per));
			 rows += record;
		 }
		 return rows;
	 }
	 
	 private String sendResultsEmail(HttpServletRequest request, UserTestSession userTestSession) throws Exception {
		 String table = "<tr style=\"border-collapse:collapse;border: 1px solid black\">\r\n" + 
		 		"                                                <td align=\"center\" style=\"border: 1px solid black\"> {SECTION_NAME}</td>\r\n" + 
		 		"						<td align=\"center\" style=\"border: 1px solid black\"> {SECTION_PERCENT}</td>\r\n" + 
		 		"                                             </tr>";
			String loction = propertyConfig.getResultLinkHtmlLocation();
			String html = FileUtils.readFileToString(new File(loction));
			User user = (User) request.getSession().getAttribute("user");
			 Test test = (Test) request.getSession().getAttribute("test");
			 html = html.replace("{FIRST_NAME}", user.getFirstName());
			 html = html.replace("{LAST_NAME}", user.getLastName());
			 html = html.replace("{TEST_NAME}", test.getTestName());
			 html = html.replace("{TOTAL_QUESTIONS}", ""+userTestSession.getTotalMarks());//change later
			 html = html.replace("{TOTAL_MARKS}", ""+userTestSession.getTotalMarksRecieved());
			 html = html.replace("{PASS_PERCENTAGE}", ""+test.getPassPercent());
			 html = html.replace("{RESULT_PERCENTAGE}", ""+userTestSession.getPercentageMarksRecieved());
			 html = html.replace("{WEIGHTED_RESULT_PERCENTAGE}", userTestSession.getFormattedWeightedScore());
			 html = html.replace("{STATUS}", test.getPassPercent() > userTestSession.getPercentageMarksRecieved()?"Fail":"Success");
			 String rows = "";
			 List<SectionInstanceDto> sectionInstanceDtos = (List<SectionInstanceDto>) request.getSession().getAttribute("sectionInstanceDtos");
			 DecimalFormat df = new DecimalFormat("##.##");
			 for(SectionInstanceDto sectionInstanceDto : sectionInstanceDtos) {
				 String record = table;
				 record = record.replace("{SECTION_NAME}", sectionInstanceDto.getSection().getSectionName());
				 record = record.replace("{SECTION_PERCENT}", df.format((new Float(sectionInstanceDto.getTotalCorrectAnswers()) / new Float(sectionInstanceDto.getNoOfQuestions())) * 100 ));
				 rows += record;
			 }
			 html = html.replace("{ROWS}", rows);
			 UserNonCompliance nonCompliance = null;
			  nonCompliance = userNonComplianceService.findNonCompliance(userTestSession.getUser(), userTestSession.getTestName(), userTestSession.getCompanyId());
			// nonCompliance = userNonComplianceService.findLastNonCompliance(userTestSession.getUser(), userTestSession.getTestName(), userTestSession.getCompanyId());
//			 
//			 if(userTestSession.getId() == null) {
//				 nonCompliance = userNonComplianceService.findNonCompliance(userTestSession.getUser(), userTestSession.getTestName(), userTestSession.getCompanyId());
//			 }
//			 else {
//				 nonCompliance =  userNonComplianceService.findByPrimaryKey(userTestSession.getUser(), userTestSession.getTestName(), userTestSession.getCompanyId(), userTestSession.getId());
//			 }
			 html = html.replace("{NO_OF_NONCOMPLIANCES}", "<b>("+(nonCompliance == null? 0:nonCompliance.getNoOfNonCompliances())+")</b>");
			 
			 if(test.getTestName().equals("General_Technology_Comprehensive") || test.getTestName().equals("Java_Technology_Behaviour_Experienced") || test.getTestName().equals("Java_Technology_Behaviour_Freshers")){
				 String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
				 String email = "";
				 if(user.getEmail().lastIndexOf("[") > 0 ){
				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
				 	}
				 	else{
				 		email = user.getEmail();
				 	}
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, email, propertyConfig, file, user.getFirstName()+" "+user.getLastName()+"-"+test.getTestName());
					
				 	Thread th = new Thread(client);
					th.start();
			 }
			 else if(test.getTestName().equalsIgnoreCase("Manual Testing")){
				 String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
				 String email = "";
				 	if(user.getEmail().lastIndexOf("[") > 0 ){
				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
				 	}
				 	else{
				 		email = user.getEmail();
				 	}
				 String cc[] = {"abbas.meghani@gmail.com", email};
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, email, propertyConfig, file, user.getFirstName()+" "+user.getLastName()+"-"+test.getTestName());
					client.setCcArray(cc);
				 	Thread th = new Thread(client);
					th.start();
			 }
			 else if(test.getTestName().equalsIgnoreCase("Java_Test_With_Recomm_Support")){
				 String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
				 String email = "";
				 	if(user.getEmail().lastIndexOf("[") > 0 ){
				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
				 	}
				 	else{
				 		email = user.getEmail();
				 	}
				 String cc[] = {"sreeram.gopal@iiht.com", email};
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, email, propertyConfig, file, user.getFirstName()+" "+user.getLastName()+"-"+test.getTestName());
					client.setCcArray(cc);
				 	Thread th = new Thread(client);
					th.start();
			 }
			 else if(test.getTestName().equalsIgnoreCase("Java Developer Infrasoft Intermediate 1.0 ")){
				 String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
				 String email = "";
				 	if(user.getEmail().lastIndexOf("[") > 0 ){
				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
				 	}
				 	else{
				 		email = user.getEmail();
				 	}
				 String cc[] = {"akansha.gupta@infrasofttech.com", email};
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, email, propertyConfig, file, user.getFirstName()+" "+user.getLastName()+"-"+test.getTestName());
					client.setCcArray(cc);
				 	Thread th = new Thread(client);
					th.start();
			 }
//			 else if(test.getTestName().equalsIgnoreCase("Chenova_Exp_MicrosoftTech_Test") || test.getTestName().equalsIgnoreCase("Chenova_Exp_JavaTech_Test")){
//				 String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
//				 String email = "";
//				 	if(user.getEmail().lastIndexOf("[") > 0 ){
//				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
//				 	}
//				 	else{
//				 		email = user.getEmail();
//				 	}
//				 String cc[] = {"VKotian@chenoainc.com", email};
//				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, propertyConfig);
//						client.setCcArray(cc);
//				 	Thread th = new Thread(client);
//					th.start();
//			 }
			 else if(test.getSendRecommReport() != null && test.getSendRecommReport()){
				 String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
				 String email = "";
				 	if(user.getEmail().lastIndexOf("[") > 0 ){
				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
				 	}
				 	else{
				 		email = user.getEmail();
				 	}
				 String cc[] = { email};
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, email, propertyConfig, file, user.getFirstName()+" "+user.getLastName()+"-"+test.getTestName());
						if(test.getSentToStudent() != null && test.getSentToStudent()){
							client.setCcArray(cc);
						}
				 	
				 	Thread th = new Thread(client);
					th.start();
			 }
			 else if(test.getSentToStudent()){
				// String file = reportsService.generatedetailedReportForCompositeTest(user.getCompanyId(), test.getTestName(), user.getEmail());
				 String email = "";
				 	if(user.getEmail().lastIndexOf("[") > 0 ){
				 		email = user.getEmail().substring(0, user.getEmail().lastIndexOf("["));
				 	}
				 	else{
				 		email = user.getEmail();
				 	}
				 String cc[] = {email};
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, propertyConfig);
					client.setCcArray(cc);
				 	Thread th = new Thread(client);
					th.start();
			 }
			 else{
				 EmailGenericMessageThread client = new EmailGenericMessageThread(test.getCreatedBy(), "Test Results for "+user.getFirstName()+" "+user.getLastName()+" for test- "+test.getTestName(), html, propertyConfig);
					
				 	Thread th = new Thread(client);
					th.start();
			 }
		return rows;	 
			 
	 }
}
