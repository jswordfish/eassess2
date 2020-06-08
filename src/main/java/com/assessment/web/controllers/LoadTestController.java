package com.assessment.web.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.UserTestSessionRepository;
import com.assessment.web.dto.LoadTestDto;
@Controller
public class LoadTestController {
	
	@Autowired
	QuestionMapperInstanceRepository qmsrep;
	
	@Autowired
	UserTestSessionRepository sessrep;

	
	@RequestMapping(value = "/showFailures", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<LoadTestDto> showFailures(@RequestParam(name= "answerCount") Integer answerCount, @RequestParam(name= "testName") String testName,@RequestParam(name= "companyId") String companyId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> emails = FileUtils.readLines(new File("/file-server/emails.txt"));
		List<LoadTestDto> failures = new ArrayList<LoadTestDto>();
			for(String email : emails){
				Integer sess = sessrep.getTestStatus(email,testName, companyId);
				LoadTestDto test = null;
				if(sess == null || sess == 0){
					test = new LoadTestDto();
					test.setEmail(email);
					test.setTestName(testName);
					test.setSessionCount(0);
				}
				System.out.println("sess is "+sess);
				
				Integer count = qmsrep.findQuestionMapperInstancesCountForUserForTest(testName, email, companyId);
				System.out.println("count is "+count);
				if(count == null){
					if(test == null){
						test = new LoadTestDto();
						test.setEmail(email);
						test.setTestName(testName);
						test.setSessionCount(1);
						test.setAnswersCount(0);
					}
					else{
						test.setEmail(email);
						test.setTestName(testName);
						test.setSessionCount(0);
						test.setAnswersCount(0);
					}
					
					
				}
				else if(count.intValue() != answerCount.intValue()){
					System.out.println("answer count "+answerCount +" comp "+(count.intValue()==answerCount.intValue()));
					if(test == null){
						System.out.println("11111111111111");
						test = new LoadTestDto();
						test.setEmail(email);
						test.setTestName(testName);
						test.setSessionCount(1);
						test.setAnswersCount(count);
					}
					else{
						System.out.println("222222222222222");
						test.setEmail(email);
						test.setTestName(testName);
						test.setSessionCount(0);
						test.setAnswersCount(count);
					}
				}
				
				
				if(test != null){
					failures.add(test);
				}
			}
		return failures;
	}
}

