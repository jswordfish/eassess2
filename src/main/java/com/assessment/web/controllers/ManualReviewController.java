package com.assessment.web.controllers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.PropertyConfig;
import com.assessment.common.util.EmailGenericMessageThread;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.User;
import com.assessment.data.UserTestSession;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.TestService;
import com.assessment.services.UserService;
import com.assessment.services.UserTestSessionService;

@Controller
public class ManualReviewController {
	@Autowired
	UserService userService;

	@Autowired
	UserTestSessionService userTestSessionservice;

	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;

	@Autowired
	QuestionMapperInstanceRepository ansRep;

	@Autowired
	PropertyConfig propertyConfig;

	@Autowired
	TestService testService;

	@RequestMapping(value = "/showManualReviewResults", method = RequestMethod.GET)
	public ModelAndView showManualReviewResults(@RequestParam(required = false) String status,
			@RequestParam(required = false) String classifierall, HttpServletRequest request,
			HttpServletResponse response, String message) {
		ModelAndView mav = new ModelAndView("lmsadmin_review_tests");
		User user = (User) request.getSession().getAttribute("user");
		List<UserTestSession> sessions = new ArrayList<UserTestSession>();
		List<String> classifiers = userService.findInstituteGradeClassifier(user.getCompanyId(),
				user.getCollegeName());
		if (classifierall == null) {
			// status will also be null
			if (classifiers.size() > 0) {
				String cls = classifiers.get(0);
				String clss[] = cls.split("-");
				String institute = clss[0].trim();
				String grade = clss[1].trim();
				String cs = clss[2].trim();
				sessions = userTestSessionservice
						.findSubjectiveResultsNotMarkedAsCompleteForInstitution(
								user.getCompanyId(), institute,
								grade, cs);
			}
		} else {
			String clss[] = classifierall.split("-");
			String institute = clss[0].trim();
			String grade = clss[1].trim();
			String cs = clss[2].trim();
			if (status != null && status.equals("Not Marked Complete")) {
				sessions = userTestSessionservice
						.findSubjectiveResultsNotMarkedAsCompleteForInstitution(
								user.getCompanyId(), institute,
								grade, cs);
			} else if (status != null && status.equals("Marked Complete")) {
				// status is marked complete
				sessions = userTestSessionservice
						.findSubjectiveResultsMarkedAsCompleteForInstitution(
								user.getCompanyId(), institute,
								grade, cs);
			}
			mav.addObject("status", status);
			mav.addObject("classifierall", classifierall);
		}

		if(message != null){
			mav.addObject("message", message);// later put it as label
			mav.addObject("msgtype", "Information");
		}
		mav.addObject("sessions", sessions);
		mav.addObject("classifiers", classifiers);
		List<String> statuses = new ArrayList<>();
		statuses.add("Marked Complete");
		statuses.add("Not Marked Complete");
		mav.addObject("statuses", statuses);
		return mav;
	}

	@RequestMapping(value = "/markTestAsComplete", method = RequestMethod.GET)
	public ModelAndView markTestAsComplete(@RequestParam(required = true) Long userSessionId,
			HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		UserTestSession session = userTestSessionservice.findSessinById(userSessionId);
		session.setMarkComplete(true);
		session = userTestSessionservice.saveOrUpdate(session);
		String message = "";
		try {
			String html = FileUtils.readFileToString(
					new File(propertyConfig.getSendTestResultInfoSubjective()));
			String learnerDate = session.getDateofTest();
			String testgivername = session.getFirstName() == null ? "Learner "
					: session.getFirstName();
			String testgiveremail = "";
			if (session.getUser().contains("[")) {
				testgiveremail = session.getUser().substring(0,
						session.getUser().indexOf("["));
			} else {
				testgiveremail = session.getUser();
			}

			String reviewer = user.getFirstName() + " " + user.getLastName();
			String testname = session.getTestName();
			String totQ = session.getTotalMarks() + "";

			com.assessment.data.Test test = testService.findbyTest(testname, user.getCompanyId());
			String passThreshold = test.getPassPercent() + "";
			String weightedScore = session.getWeightedScorePercentage() + "";
			String pass = session.getWeightedScorePercentage() == null ? "Fail"
					: (session.getWeightedScorePercentage() >= test.getPassPercent()
							? "Pass"
							: "Fail");
			String nonc = session.getNoOfNonCompliances() + "";
			html = html.replace("{LEARNER}", testgivername);
			html = html.replace("{TEST_NAME}", testname);
			html = html.replace("{DATE}", learnerDate);

			html = html.replace("{REVIEWER}", reviewer);
			html = html.replace("{TOTAL_QUESTIONS}", totQ);
			html = html.replace("{PASS_PERCENTAGE}", passThreshold);

			html = html.replace("{WEIGHTED_RESULT_PERCENTAGE}", weightedScore);

			html = html.replace("{STATUS}", pass);
			html = html.replace("{NO_OF_NONCOMPLIANCES}", nonc);
			System.out.println("test giver email is " + testgiveremail);
			EmailGenericMessageThread emailGenericMessageThread = new EmailGenericMessageThread(
					testgiveremail,
					"Your test " + testname + " checked by your Reviewer", html,
					propertyConfig);
			Thread thread = new Thread(emailGenericMessageThread);
			thread.start();
			message = "Test Review Complete. Results shared by Email to Learner";

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "Problem in Test Marking Process.";
		}
		return showManualReviewResults(null, null, request, response, message);
	}

	@RequestMapping(value = "/markAnswer", method = RequestMethod.GET)
	@ResponseBody
	public String markAnswer(@RequestParam(required = true) String marks,
			@RequestParam(required = false) String suggestion,
			@RequestParam(required = true) Long ansid, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			QuestionMapperInstance instance = questionMapperInstanceService.findById(ansid);
			if (marks.equals("100 % Correct")) {
				instance.setMarksAssignedInPercentIncaseSubjective(100);
			} else if (marks.equals("75 % Correct")) {
				instance.setMarksAssignedInPercentIncaseSubjective(75);
			} else if (marks.equals("50 % Correct")) {
				instance.setMarksAssignedInPercentIncaseSubjective(50);
			} else if (marks.equals("25 % Correct")) {
				instance.setMarksAssignedInPercentIncaseSubjective(25);
			} else if (marks.equals("No Marks")) {
				instance.setMarksAssignedInPercentIncaseSubjective(0);
			}
			instance.setReviewerCommentsForSubjectiveQuestion(suggestion);
			instance.setMarkComplete(true);
			instance.setUpdateDate(new Date());
			ansRep.save(instance);
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "not ok";
		}
	}

//	@RequestMapping(value = "/showManualReviewResults", method = RequestMethod.GET)
//	public ModelAndView showManualReviewResultsComplete(@RequestParam(required=true) String status, @RequestParam(required=true) String classifierall, HttpServletRequest request, HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("lmsadmin_review_tests");
//		User user = (User) request.getSession().getAttribute("user");
//		List<UserTestSession> sessions = new ArrayList<UserTestSession>();
//		String clss[] = classifierall.split("-");
//		String institute = clss[0].trim();
//		String grade = clss[1].trim();
//		String cs = clss[2].trim();
//		List<String> classifiers =  userService.findInstituteGradeClassifier(user.getCompanyId(), user.getCollegeName());
//		if(status.equals("Marked Complete")){
//			sessions = userTestSessionservice.findSubjectiveResultsMarkedAsCompleteForInstitution(user.getCompanyId(), institute, grade, cs);
//		}
//		else{
//			sessions = userTestSessionservice.findSubjectiveResultsNotMarkedAsCompleteForInstitution(user.getCompanyId(), institute, grade, cs);
//		}
//		
//		Stack<String> stk = new Stack<>();
//		stk.add("success");
//		stk.add("danger");
//		stk.add("info");
//		stk.add("warning");
//		stk.add("active");
//			for(UserTestSession session: sessions){
//				if(stk.size() == 0){
//					stk.add("success");
//					stk.add("danger");
//					stk.add("info");
//					stk.add("warning");
//					stk.add("active");
//				}
//				String disp = stk.pop();
//				session.setStyle(disp);
//			}
//		
//		mav.addObject("sessions", sessions);
//		mav.addObject("classifiers", classifiers);
//		List<String> statuses = new ArrayList<>();
//		statuses.add("Marked Complete");
//		statuses.add("Not Marked Complete");
//		mav.addObject("statuses", statuses);
//		return mav;
//	}

	@RequestMapping(value = "/showManualReviewQuestions", method = RequestMethod.GET)
	public ModelAndView showManualReviewQuestions(@RequestParam String testName, @RequestParam String email,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("lmsadmin_review_questions");
		User user = (User) request.getSession().getAttribute("user");
		List<QuestionMapperInstance> instances = questionMapperInstanceService
				.findSubjectQuestionMapperInstancesForUserAndTest(testName, email,
						user.getCompanyId());
		UserTestSession session = userTestSessionservice.findUserTestSession(email, testName,
				user.getCompanyId());
		Stack<String> stk = new Stack<>();
		stk.add("success");
		stk.add("danger");
		stk.add("info");
		stk.add("warning");
		stk.add("active");
		for (QuestionMapperInstance instance : instances) {
			if (stk.size() == 0) {
				stk.add("success");
				stk.add("danger");
				stk.add("info");
				stk.add("warning");
				stk.add("active");
			}
			String disp = stk.pop();
			instance.setStyle(disp);
		}
		User user2 = userService.findByPrimaryKey(email, user.getCompanyId());
		mav.addObject("firstName", user2.getFirstName());
		mav.addObject("lastName", user2.getLastName());
		mav.addObject("testName", testName);
		mav.addObject("email", email);
		mav.addObject("answers", instances);
		mav.addObject("sessionId", session.getId());
		return mav;
	}

	@RequestMapping(value = "/cancelShowManualReviewQuestions", method = RequestMethod.GET)
	public ModelAndView cancelShowManualReviewQuestions(HttpServletRequest request,
			HttpServletResponse response) {
		return showManualReviewResults(null, null, request, response, null);
	}
}