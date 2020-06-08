package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.common.ExcelReader;
import com.assessment.common.Qualifiers;
import com.assessment.data.Company;
import com.assessment.data.DifficultyLevel;
import com.assessment.data.Question;
import com.assessment.data.QuestionMapper;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.QuestionMapperRepository;
import com.assessment.repositories.QuestionRepository;
import com.assessment.services.CompanyService;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.QuestionService;
import com.assessment.services.SectionInstanceService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestQuestion {
	@Autowired
	QuestionService questionService;
	
	@Autowired
	QuestionMapperRepository questionMapperRepository;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	QuestionMapperInstanceRepository instanceRep;
	
	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	SectionInstanceService sectionInstanceService;
	
	@Test
	public void testQ(){
		List<QuestionMapperInstance> instances = instanceRep.findQuestionMapperInstancesForUserForTest("BulkUpdate_MultipleSections", "14@may.com", "IH");
		System.out.println(instances.size());
	}
	
	
	@Test
	public void testgetJavafullStackquestions(){
		List<QuestionMapperInstance> ins = instanceRep.findFullStackQuestionMapperInstancesForJava("IH");
		System.out.println(ins.size());
	}
	
	@Test
	public void testQMC(){
		List<QuestionMapperInstance> ins = instanceRep.findCodingQuestionMapperInstances("Test");
		System.out.println(ins.size());
	}
	
	@Test
	public void testGetUniqueQualifiers(){
		Set<Qualifiers> qs = questionRepository.getAllUniqueQualifiers("Test");
		System.out.println(qs.size());
		for(Qualifiers qualifiers : qs){
			System.out.println(qualifiers.getQualifier1()+" ." +qualifiers.getQualifier2()+" ."+ qualifiers.getQualifier3()+" ."+ qualifiers.getQualifier4()+" ."+ qualifiers.getQualifier5());
		}
	}
	
	@Test
	public void testGetInstancesByQualifier(){
		List<QuestionMapperInstance> instances = questionMapperInstanceService.getInstancesOR("Java", "IH");
		System.out.println(instances.size());
	}
	
	@Test
	@Rollback(value=false)
	public void doBulkCreateTest(){
		List<QuestionMapperInstance> instances = questionMapperInstanceService.getInstancesOR("Java", "IH");
		List<QuestionMapperInstance> set = instances.subList(0, 23);
		List<QuestionMapperInstance> newset = new ArrayList<QuestionMapperInstance>();
		Mapper mapper = new DozerBeanMapper();
		for(QuestionMapperInstance an : set){
			QuestionMapperInstance ins = new QuestionMapperInstance();
			mapper.map(an, ins);
			ins.setId(null);
			newset.add(ins);
		}
		sectionInstanceService.saveAnswersInBatch(newset);
		System.out.println("done");
	}
	
	@Test
	@Rollback(value=false)
	public void doBulkUpdateTest(){
		List<QuestionMapperInstance> instances = questionMapperInstanceService.getInstancesOR("Java", "IH");
		List<QuestionMapperInstance> set = instances.subList(0, 23);
	
		for(QuestionMapperInstance an : set){
		an.setUpdateDate(new Date());
		}
		sectionInstanceService.updateAnswersInBatch(set);
		System.out.println("done");
	}
	
	@Test
	public void testQuestion() {
		Page<Question> questions = questionService.findQuestionsByPage("IH", 0);
		System.out.println(questions.getSize());
	}
	
	@Test
	public void testGetLevel1Qs(){
		List<Question> qs = questionService.getAllLevel1Questions("IH");
		for(Question q : qs){
			System.out.println(q.getQualifier1());
		}
	}
	
	@Test
	@Rollback(value=false)
	public void testBulkInsert(){
		List<Question> qs = questionService.getAllLevel1Questions("IH");
		List<Question> qs1 = qs.subList(0, 2);
		Question q1 = new Question();
		q1.setCompanyId("IH");
		q1.setCompanyName("IIHT");
		q1.setQuestionText("del1");
		//q1.setQuestionType(question);
		qs1.add(q1);
		questionRepository.saveAll(qs1);
		
	}
	
	@Test
	@Rollback(value=false)
	public void l() {
		List<QuestionMapperInstance> ins = instanceRep.findQuestionMapperInstancesForUserForTest("Recruitment_Drive_Comprehensive_test_fresher", "patilsiddesh941@gmail.com", "IH");
		System.out.println(ins.size());
	}
	
	@Test
	@Rollback(value=false)
	public void testGetQuestionMapper() {
		List<QuestionMapper> mappers = questionMapperRepository.findByQuestion_id(76l);
		System.out.println(mappers.size());
	}
	
	@Test
	@Rollback(value=false)
	public void testGetQuestion(){
		String search = "Create Mentor on Demand application using java FSA for IBM.%%%%Click<a href=\"https://img.yaksha.online/images/assessment/New MOD-IBM Phase 3 test Driven Development.docx\">Download</a> to download developer instraction manual";
		//List<Question> questions = questionRepository.searchQuestions("IH", "Create Mentor on Demand application using java FSA for IBM.");
		List<Question> questions = questionRepository.searchQuestions("IH", search);
		System.out.println(questions.size());
		String str = questions.get(0).getQuestionText();
			if(str.contains("\r")){
				System.out.println("qqqqqqqqqq");
			}
			
			if(str.contains("\n")){
				System.out.println("aaaaaaa");
			}
		str = str.replaceAll("[" + System.lineSeparator() + "]", "%");
		questions = questionRepository.searchQuestions("IH", str);
		System.out.println(questions.size());
	}
	
	@Test
	@Rollback(value=false)
	public void testCreateQuestion() {
		String s1 = "Create Sample apllication for Emart using html and css. click to <a href=\" http://ide.yaksha.online/file-server/eMart_Case_Study_For_UI(CSS).docx\">Download</a> developer instruction document. ";
		String s2 = "Create Sample apllication for Emart using html and css. click to <a href=\"http://ide.yaksha.online/file-server/eMart_Case_Study_For_UI(CSS).docx\">Download</a> developer instruction document. ";
		
		Question q = new Question();
		String qt = " Create Pixogram application using bootstrap."+System.lineSeparator()+
"<a href=\"https://img.yaksha.online/images/assessment/New java-Pixogram_bootstrap.docx\"> Right Click to download Project Specifications";
		q.setCompanyId("ALS2019");
		q.setCompanyName("ALS2019");
		//q.setQuestionText("以下哪个标记代表HTML5中某个部分的标题？");
		q.setQuestionText(qt);
		q.setDifficultyLevel(DifficultyLevel.EASY);
		q.setQualifier1("dirty1");
		q.setChoice1("c1");
		q.setChoice2("c2");
		q.setRightChoices("Choice 1");
		questionService.createQuestion(q);
	}
	
	@Test
	@Rollback(value=false)
	public void testUploadquestions() throws Exception{
		FileInputStream fis = new FileInputStream("AssessmentEngine_Upload_Data.xlsx");
		File file = new File("questions.xml");
		List<Question> questions = ExcelReader.parseExcelFileToBeans(fis, file);
		Company company = companyService.findByPrimaryKey("IIHT", "IH");
		for(Question q : questions) {
			System.out.println(q.getQuestionText());
			q.setCompanyId(company.getCompanyId());
			q.setCompanyName(company.getCompanyName());
			questionService.createQuestion(q);
		}
	}
	
	@Test
	@Rollback(value=false)
	public void testAdaptiveTestQsLevel3(){
		//List<Question> qs = questionRepository.getAdaptiveAssessmentLevel3Questions("IH");
		//System.out.println(qs.size());
//		Integer count  = questionRepository.getAdaptiveAssessmentLevel1Count("Core java", "IH");
//		System.out.println(count);
	}
	@Test
	@Rollback(value=false)
	public void testGetQualifiersForTest(){
		Set<Qualifiers> qualifiers = questionMapperRepository.getAllUniqueQualifiersForTest("IH", "MultiChoiceTest");
		System.out.println(qualifiers.size());
			for(Qualifiers q : qualifiers){
				System.out.println(q.getQualifier1()+"-"+q.getQualifier2()+"-"+q.getQualifier3()+"-"+q.getQualifier4()+"-"+q.getQualifier5());
			}
		
	}
	
	@Test
	@Rollback(value=false)
	public void testGetQMIForCourseContext(){
		List<QuestionMapperInstance> qms = instanceRep.findQuestionMapperInstancesForUserForCourseContext("Comprehensive Java", "test@test.com", "IH");
		System.out.println(qms.size());
	}
	
	@Test
	@Rollback(value=false)
	public void testGetUniqueUsersForCourseContext(){
		String user = "www1@www.com";
		List<String> usrs = instanceRep.findUniqueTestsForCourseContext("Comprehensive Java", user, "IH");
		System.out.println(usrs.size());
	}
	
	@Test
	@Rollback(value=false)
	public void testGetUniqueUsersForCourseContextAndTest(){
		String user = "www1@www.com";
		List<String> usrs = instanceRep.findUniqueUsersForCourseContextAndTest("HTML_CSS_JS_v1.0", "Comprehensive Java", user, "IH");
		System.out.println(usrs.size());
	}
	
	@Test
	@Rollback(value=false)
	public void testGetQMIsForCourseContextAndTest(){
		String user = "www1@www.com[198-1]";
		List<QuestionMapperInstance> qms = instanceRep.findQuestionMapperInstancesForUserForCourseContextAndTest("HTML_CSS_JS_v1.0", "Comprehensive Java", user, "IH");
		System.out.println(qms.size());
	}

}
