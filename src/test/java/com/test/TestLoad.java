package com.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.UserTestSessionRepository;
import com.assessment.web.dto.LoadTestDto;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestLoad {
	
	@Autowired
	QuestionMapperInstanceRepository qmsrep;
	
	@Autowired
	UserTestSessionRepository sessrep;

	
	@Test
	public void  testShowFailures() throws Exception {
		//@RequestParam(name= "answerCount") Integer answerCount, @RequestParam(name= "testName") String testName,@RequestParam(name= "companyId") String companyId, HttpServletRequest request, HttpServletResponse response
		Integer answerCount = 273;
		String testName = "Load_TestWithMultipleQs";
		String companyId = "IH";
		List<String> emails = FileUtils.readLines(new File("c:\\delete\\7feb2020\\emails.txt"));
		List<LoadTestDto> failures = new ArrayList<LoadTestDto>();
			for(String email : emails){
				email = email.trim();
				Integer sess = sessrep.getTestStatus(email,testName, companyId);
				LoadTestDto test = null;
				if(sess == null || sess == 0){
					test = new LoadTestDto();
					test.setEmail(email);
					test.setTestName(testName);
					test.setSessionCount(0);
				}
				
				
				Integer count = qmsrep.findQuestionMapperInstancesCountForUserForTest(testName, email, companyId);
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
					if(test == null){
						test = new LoadTestDto();
						test.setEmail(email);
						test.setTestName(testName);
						test.setSessionCount(1);
						test.setAnswersCount(count);
					}
					else{
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
			for(LoadTestDto test : failures){
				System.out.println(test.getTestName()+" user "+test.getEmail()+" sessioncount "+test.getSessionCount()+" ans count "+test.getAnswersCount());
			}
		//return failures;
	}
	
	
	@Test
	public void  testShowFailures2() throws Exception {
		//@RequestParam(name= "answerCount") Integer answerCount, @RequestParam(name= "testName") String testName,@RequestParam(name= "companyId") String companyId, HttpServletRequest request, HttpServletResponse response
		Integer answerCount = 273;
		String testName = "Load_TestWithMultipleQs";
		String companyId = "IH";
		List<String> emails = FileUtils.readLines(new File("c:\\delete\\7feb2020\\emails.txt"));
		
			for(String email : emails){
				email = email.trim();
				Integer sess = sessrep.getTestStatus(email,testName, companyId);
				Integer count = qmsrep.findQuestionMapperInstancesCountForUserForTest(testName, email, companyId);
				System.out.println("user "+email+" -sessionCount "+sess+" -answer count "+count);
			}
			
		//return failures;
	}

}
