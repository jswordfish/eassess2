package com.assessment.web.controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assessment.common.Qualifiers;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.Test;
import com.assessment.data.User;
import com.assessment.data.UserTestSession;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.TestService;
import com.assessment.services.UserService;
import com.assessment.services.UserTestSessionService;
import com.assessment.web.dto.TestAnswerData;
import com.assessment.web.dto.TestSummary;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
public class ReportWebServicesController {
	
	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;
	
	@Autowired
	UserTestSessionService userTestSessionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TestService testService;
	
	
	@RequestMapping(value = "/testSummary", method = RequestMethod.GET, produces = "application/json")
	 public @ResponseBody TestSummary  testSummary(@RequestHeader("securitytoken") String securitytoken, @RequestParam String companyId, @RequestParam String testName,@RequestParam String user,  HttpServletRequest request, HttpServletResponse response) {
		TestSummary summary = new TestSummary(); 	
			if(securitytoken == null || (!securitytoken.equals("QWERTY"))){
				summary.setErrorMsg("Security_Token_Missing");
		 		return summary;
		 	}
		 
		UserTestSession session = userTestSessionService.findUserTestSession(user, testName, companyId);
	
	    User usr = userService.findByPrimaryKey(user, companyId);
		if(usr == null){
			summary.setErrorMsg("User_Does_Not_Exist");
		}
		List<QuestionMapperInstance> instances = questionMapperInstanceService.findQuestionMapperInstancesForUserForTest(testName, user, companyId);
	
		if(session == null){
			summary.setTestComplete(false);
			summary.setEmail(user);
		}
		else{
			summary.setEmail(user);
			summary.setFirstName(usr.getFirstName());
			summary.setLastName(usr.getLastName());
			summary.setTestComplete(true);
			summary.setTestName(testName);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				if(session.getCreateDate() != null){
					String date = dateFormat.format(session.getCreateDate());
					summary.setTestTakenDate(date);
				}
			summary.setTestStatus(session.getPass());
			summary.setAverageScore(session.getPercentageMarksRecieved());
			summary.setWeightedScore(session.getWeightedScorePercentage());
			summary.setCompanyId(companyId);
			summary.setCompanyName(session.getCompanyName());
        	Test test = testService.findbyTest(testName, companyId);
        	List<TestAnswerData> testAnswerDatas = new ArrayList<>();
        	for(QuestionMapperInstance instance : instances){
        		TestAnswerData answerData = new TestAnswerData();
        			if(test.getConsiderConfidence() == null || !test.getConsiderConfidence()){
        				answerData.setConfidentAboutAnswer("NA");
        			}
        			else{
        				answerData.setConfidentAboutAnswer(instance.getConfidence() == null?"No":(instance.getConfidence()?"Yes":"No"));
        			}
        		
        		answerData.setFirstName(usr.getFirstName());
        		answerData.setLastName(usr.getLastName());
        		answerData.setEmail(user);
        		answerData.setTestName(testName);
        		answerData.setProblem(instance.getQuestionMapper().getQuestion().getQuestionText());
        		answerData.setQuestionCategory(instance.getQuestionMapper().getQuestion().getQuestionCategory());
        		answerData.setChoice1(instance.getQuestionMapper().getQuestion().getChoice1());
        		answerData.setChoice2(instance.getQuestionMapper().getQuestion().getChoice2());
        		answerData.setChoice3(instance.getQuestionMapper().getQuestion().getChoice3());
        		answerData.setChoice4(instance.getQuestionMapper().getQuestion().getChoice4());
        		answerData.setChoice5(instance.getQuestionMapper().getQuestion().getChoice5());
        		answerData.setChoice6(instance.getQuestionMapper().getQuestion().getChoice6());
        		answerData.setQuestionWeight(instance.getQuestionMapper().getQuestion().getQuestionWeight());
        		answerData.setCorrectChoice(instance.getQuestionMapper().getQuestion().getRightChoices());
        		answerData.setUserChoice(instance.getUserChoices());
        		answerData.setCorrect(instance.getCorrect()?"Yes":"No");
        		answerData.setUserProgram(instance.getCodeByUser());
        		testAnswerDatas.add(answerData);
        	}
        	summary.setList(testAnswerDatas);
		}
		return summary; 	
		
	 }
	
	@RequestMapping(value = "/sessionsForTest", method = RequestMethod.GET, produces = "application/json")
	 public @ResponseBody List<UserTestSession>  sessionsForTest(@RequestHeader("securitytoken") String securitytoken, @RequestParam String companyId, @RequestParam String testName,  HttpServletRequest request, HttpServletResponse response) {
		if(securitytoken == null || (!securitytoken.equals("QWERTY"))){
	 		return null;
	 	}
		List<UserTestSession> sessions = userTestSessionService.findUserSessionsForTest(testName, companyId);
		for(UserTestSession session :  sessions){
			session.setTest(null);
		}
		return sessions;
	}

}
