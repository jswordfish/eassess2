package com.assessment.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.PropertyConfig;
import com.assessment.common.Qualifiers;
import com.assessment.common.util.EmailGenericMessageThread;
import com.assessment.data.CandidateProfileParams;
import com.assessment.data.Company;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.QuestionType;
import com.assessment.data.Test;
import com.assessment.data.User;
import com.assessment.data.UserTestSession;
import com.assessment.reports.manager.AssessmentReportDataManager;
import com.assessment.reports.manager.AssessmentReportsManager;
import com.assessment.reports.manager.AssessmentTestPerspectiveData;
import com.assessment.reports.manager.AssessmentUserPerspectiveData;
import com.assessment.reports.manager.UserTrait;
import com.assessment.repositories.CompanyRepository;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.QuestionRepository;
import com.assessment.repositories.TestRepository;
import com.assessment.repositories.UserTestSessionRepository;
import com.assessment.services.CandidateProfileParamsService;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.SectionService;
import com.assessment.services.TestService;
import com.assessment.services.UserService;
import com.assessment.web.dto.FullStackCodeMetrics;
import com.assessment.web.dto.SPACodeMetrics;
import com.assessment.web.dto.TestAnswerData;
import com.assessment.web.dto.TestapiParam;
import com.assessment.web.dto.UserBySkillDTO;

@Controller
public class ReportsController {
	
	Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Autowired
	UserTestSessionRepository userTestSessionRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SectionService sectionService;
	
	@Autowired
	AssessmentReportsManager reportManager;
	
	@Autowired
	QuestionMapperInstanceRepository rep;
	
	@Autowired
	PropertyConfig propertyConfig;
	
	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	TestService testService;
	
	@Autowired
	CandidateProfileParamsService candidateProfileParamsService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	TestRepository testRep;
	
	
	@RequestMapping(value = "/multiFileReports", method = RequestMethod.GET)
	 public @ResponseBody TestapiParam multiFileReports(@RequestParam("date") String datewise, HttpServletRequest request, HttpServletResponse response) throws IOException {
		TestapiParam ret = new TestapiParam();
		String path = propertyConfig.getReportFilesLocation();
		path = path + File.separator + datewise;
		File file = new File(path);
		if(!file.exists()){
			ret.setMessage("Location "+path+" does not exist. Contact Admin");
			return ret;
		}
		
		File mf = new File(path+File.separator+"Manifest.mf");
			if(!mf.exists()){
				ret.setMessage("Location "+path+" does contain Manifest.mf file. Contact Admin");
				return ret;
			}
			List<String> result = new ArrayList<>();
			try (Stream<Path> walk = Files.walk(Paths.get(path))) {

				result = walk.filter(Files::isRegularFile)
						.map(x -> x.toString()).collect(Collectors.toList());

				

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(result.size() <2 ){
				ret.setMessage("Location "+path+" does contain result pdfs");
				return ret;
			}
			
			List<String> mfContent = FileUtils.readLines(mf);
			Boolean sendToParticipant = false;
			List<String> emails = new ArrayList<>();
				for(String line : mfContent){
					if(!line.contains("SendResultToParticipant")){
						emails.add(line.toLowerCase());
					}
					else{
						String par[] = line.split("=");
						if(par.length != 2){
							ret.setMessage("Manifest file does not have property SendResultToParticipant in right format");
							return ret;
						}
						
						if(par[1].equalsIgnoreCase("yes")){
							sendToParticipant = true;
						}
						else{
							sendToParticipant = false;
						}
						
					}
				}
			
				
				String msg = "";
				String testName = "";
				for(String report : result){
					if(report.endsWith("pdf")){
						String[] reportname = report.split("_");
						if(reportname.length != 2){
							ret.setMessage("One of Participant Report files follows a wrong pattern "+report);
							return ret;
						}
						
						Long tid = null;
						try{
							tid = Long.parseLong(reportname[1].substring(0, reportname[1].indexOf(".")));
						}
						catch(Exception e){
							ret.setMessage("One of Participant Report files follows a wrong pattern "+report);
							return ret;
						}
						Test test = null;
						try {
							Optional<Test> opt = testRep.findById(tid);
								if(opt != null){
									test = opt.get();
								}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							logger.error("invalid test id "+tid, e);
							System.out.println("invalid test id "+tid);
						}
						
						
						testName = (test == null?"Test Name NA":test.getTestName());
					}
						
					if(sendToParticipant){
						if(report.endsWith("pdf")){
							List<String> copy = new ArrayList<>();
							copy.addAll(mfContent);
							copy = copy.stream().filter(str -> !str.startsWith("SendResultToParticipant")).collect(Collectors.toList());
							
							String part[] = report.split("_");
								if(part.length != 2){
									ret.setMessage("One of Participant Report files follows a wrong pattern "+report);
									return ret;
								}
								
								String participant = part[0];
								participant = participant.substring(participant.lastIndexOf(File.separator)+1, participant.length());
								copy.add(participant.toLowerCase())	;
								/**
								 * Remove duplicates
								 */
								List<String> listWithoutDuplicates = copy.stream()
									     .distinct()
									     .collect(Collectors.toList());
								String cand = part[0].substring(part[0].lastIndexOf(File.separator)+1, part[0].length());
								EmailGenericMessageThread runnable = new EmailGenericMessageThread("yaksha@iiht.com", "Multifile Report  for "+cand+" on Test - "+testName, "Please see attached!", propertyConfig);
								runnable.setPdfAttachmentFile(report);
								runnable.setPdfAttachmentFileName(report);
								
								/**
								 * Convert in to cc array
								 */
								String[] array = listWithoutDuplicates.stream()
										 .toArray(String[]::new);
								runnable.setCcArray(array);
								Thread thread = new Thread(runnable);
								thread.start();
								String sentall = String.join(",", array);
								msg += "Report name "+report+" sent to "+sentall+"\n";
						}
						
					}
					else{//not sent to participant
						if(report.endsWith("pdf")){
							List<String> copy = new ArrayList<>();
							copy.addAll(mfContent);
							copy = copy.stream().filter(str -> !str.startsWith("SendResultToParticipant")).collect(Collectors.toList());
							
							String part[] = report.split("_");
							if(part.length != 2){
								ret.setMessage("One of Participant Report files follows a wrong pattern "+report);
								return ret;
							}
							
							String cand = part[0].substring(part[0].lastIndexOf(File.separator)+1, part[0].length());
							EmailGenericMessageThread runnable = new EmailGenericMessageThread("yaksha@iiht.com", "Multifile Report  for "+cand+" on Test - "+testName, "Please see attached!", propertyConfig);
							runnable.setPdfAttachmentFile(report);
							runnable.setPdfAttachmentFileName(report);
							/**
							 * Remove duplicates
							 */
							List<String> listWithoutDuplicates = copy.stream()
								     .distinct()
								     .collect(Collectors.toList());
							
							/**
							 * Convert in to cc array
							 */
							String[] array = listWithoutDuplicates.stream()
									 .toArray(String[]::new);
							runnable.setCcArray(array);
							Thread thread = new Thread(runnable);
							thread.start();
							String sentall = String.join(",", array);
							msg += "Report name "+report+" sent to "+sentall+"\n";
						}
						
					}
			}
				ret.setSendResultToParticipant((sendToParticipant)?"yes":"no");
				ret.setDate(datewise);
				ret.setMessage(msg);
				return ret;
	 }
	
	
	
	
	
	
	private List<UserTrait> generateTraits(String companyId, List<QuestionMapperInstance> answers){
		List<CandidateProfileParams> candidateProfileParams = candidateProfileParamsService.findCandidateProfileParamsByCompanyId(companyId);
		
		Map<CandidateProfileParams, List<QuestionMapperInstance>> map = new HashMap<>();
		for(QuestionMapperInstance ans : answers){
			CandidateProfileParams param = new CandidateProfileParams(ans.getQuestionMapper().getQuestion().getQualifier1(), ans.getQuestionMapper().getQuestion().getQualifier2(), ans.getQuestionMapper().getQuestion().getQualifier3(), ans.getQuestionMapper().getQuestion().getQualifier4(), ans.getQuestionMapper().getQuestion().getQualifier5()); 
			param.setQualifierDesc(ans.getQuestionMapper().getQuestion().getQualifierDescription());
			//param.setPercent(ans);
			if(map.get(param) == null){
				List<QuestionMapperInstance> ins = new ArrayList<>();
				ins.add(ans);
				map.put(param, ins);
			}
			else{
				map.get(param).add(ans);
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		Map<CandidateProfileParams,Float> mapPer = new HashMap<>();
		Map<CandidateProfileParams, String> mapTrait = new HashMap<>();
		for(CandidateProfileParams param : map.keySet()){
			List<QuestionMapperInstance> answersForQualifier = map.get(param);
			int noOfCorrect = 0;
			for(QuestionMapperInstance ans :answersForQualifier ){
				if(ans.getCorrect()){
					noOfCorrect++;
				}
			}
			
			Float percent = Float.parseFloat(df.format(noOfCorrect * 100 / answersForQualifier.size()));
			param.setPercent(percent);
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
		List<UserTrait> traits = new ArrayList<>();
		for(CandidateProfileParams param : mapTrait.keySet()){
			UserTrait trait = new UserTrait();
			trait.setTraitSpecifics(param.getQualifierDesc());
			trait.setPercent(param.getPercent());
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
		return traits;
	}
	
	private List<QuestionMapperInstance> getAnswersFromLastUserAttempts(String courseContext, String email, String companyId){
		List<String> tests = questionMapperInstanceService.findUniqueTestsForCourseContext(courseContext, email, companyId);
		List<QuestionMapperInstance> instances = new ArrayList<>();
		for(String test : tests){
			List<String> users = questionMapperInstanceService.findUniqueUsersForCourseContextAndTest(test, courseContext, email, companyId);
			if(users != null && users.size() > 0){
				String usr = users.get(users.size() - 1);
				List<QuestionMapperInstance> ins = questionMapperInstanceService.findQuestionMapperInstancesForUserForTest(test, usr, companyId);
				instances.addAll(ins);
			}
		}
		return instances;
	}
	
	@RequestMapping(value = "/showComprehensiveReportForCourse", method = RequestMethod.GET)
	public ModelAndView showComprehensiveReportForCourse(@RequestParam String companyId,@RequestParam String courseContext, @RequestParam String email, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("comprehensive_report");
		mav.addObject("courseContext", courseContext);
		//User user = (User) request.getSession().getAttribute("user");
		//List<QuestionMapperInstance> instances = questionMapperInstanceService.findQuestionMapperInstancesForUserForCourseContext(courseContext, email, user.getCompanyId());
		
		/**
		 * Gets the answers of the last user attempt for every test.
		 */
		List<QuestionMapperInstance> instances = getAnswersFromLastUserAttempts(courseContext, email, companyId);
		
		List<QuestionMapperInstance> mcqs = new ArrayList<>();
		List<QuestionMapperInstance> spas = new ArrayList<>();
		List<QuestionMapperInstance> fsapps = new ArrayList<>();
		Integer totalQs = 0;
		Integer correctAnswers = 0;
		//System.out.println("email is "+email +" course context is "+courseContext+" total answers "+instances.size());
		/**
		 * mapping between weight and score
		 */
		Map<Integer, Integer> map = new HashMap<>();
		String normalPercentage = "";
		String weightedPercentage = "";
		Map<Integer, Integer> map_weight_totalsQs = new HashMap<>();
		
		for(QuestionMapperInstance ans : instances){
			totalQs++;
			
			
			if(ans.getQuestionMapper().getQuestion().getCourseWeight() == null ){
				ans.getQuestionMapper().getQuestion().setCourseWeight(1);
			}
			
			if(map.get(ans.getQuestionMapper().getQuestion().getCourseWeight()) == null){
				map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), 0);
			}
			
			if(map_weight_totalsQs.get(ans.getQuestionMapper().getQuestion().getCourseWeight()) == null){
				map_weight_totalsQs.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), 1);
			}
			else{
				Integer totalQsForWeight = map_weight_totalsQs.get(ans.getQuestionMapper().getQuestion().getCourseWeight());
				totalQsForWeight++;
				map_weight_totalsQs.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), totalQsForWeight);
			}
			
			
			if(ans.getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.MCQ.getType())){
				mcqs.add(ans);
				if(ans.getCorrect()){
					correctAnswers ++;
					if(map.get(ans.getQuestionMapper().getQuestion().getCourseWeight()) == null){
						map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), 1);
					}
					else{
						Integer score = map.get(ans.getQuestionMapper().getQuestion().getCourseWeight());
						score ++;
						map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), score);
					}
				}
			}
			else if(ans.getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
				spas.add(ans);
				if(ans.getTestCaseInputPositive() != null && ans.getTestCaseInputPositive()){
					correctAnswers ++;
					if(map.get(ans.getQuestionMapper().getQuestion().getCourseWeight()) == null){
						map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), 1);
					}
					else{
						Integer score = map.get(ans.getQuestionMapper().getQuestion().getCourseWeight());
						score ++;
						map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), score);
					}
				}
			}
			else{
				Integer totalTestCases = ans.getNoOfTestCases();
				Integer passed = ans.getNoOfTestCasesPassed();
					if(totalTestCases != null && passed != null){
						if((100 * passed/totalTestCases) >= 50){
							ans.setCorrect(true);
							correctAnswers ++;
							if(map.get(ans.getQuestionMapper().getQuestion().getCourseWeight()) == null){
								map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), 1);
							}
							else{
								Integer score = map.get(ans.getQuestionMapper().getQuestion().getCourseWeight());
								score ++;
								map.put(ans.getQuestionMapper().getQuestion().getCourseWeight(), score);
							}
						}
						else{
							ans.setCorrect(false);
						}
					}
					else{
						//ans.setAnswered(true);
						ans.setCorrect(false);
					}
				fsapps.add(ans);
			}
			
		}
		//System.out.println("correct answers "+correctAnswers+" totalqs "+totalQs);
		float per = (100 * (correctAnswers))/totalQs;
		 DecimalFormat df = new DecimalFormat();
		 df.setMaximumFractionDigits(2);
		 String percentage = df.format(per);
		 mav.addObject("totalAverage", per);
		 
		 /**
		  * mapping between weight and percentage correct answers for the weight
		  */
		// Map<Integer, Float> map_weight_percent = new HashMap<>();
		 float totalWeight = 0;
		 float totalPercents = 0;
		 for(Integer weight : map.keySet()){
			Integer qsForWeight =  map_weight_totalsQs.get(weight);
			float p = map.get(weight)*100/qsForWeight;
			//map_weight_percent.put(weight, p);
			totalWeight += weight;
			totalPercents += p * weight;
		 }
		 float calculatedWeightedPercentage = totalPercents / totalWeight;
		 String wPer = df.format(calculatedWeightedPercentage);
		 mav.addObject("totalWeightedAverage", wPer);
		 
		//System.out.println("generate user traits "+mcqs.size()+" comp id "+companyId);
		List<UserTrait> traits = generateTraits(companyId, mcqs);
		mav.addObject("mcqTraits", traits);
		
		if(spas.size() > 0){
			mav.addObject("codingTraitsPresent", true);
			int syntaxAwareness = 0;
			int codeIntegrity = 0;
			int codeValidations = 0;
			int lowInuts = 0;
			int highInputs = 0;
			int productionGrade = 0;
			for(QuestionMapperInstance spa : spas){
				if(spa.getCodeCompilationErrors() != null && !spa.getCodeCompilationErrors()){
					syntaxAwareness++;
				}
				
				if(spa.getTestCaseInputPositive()){
					codeIntegrity ++;
				}
				
				if(spa.getTestCaseInputNegative()){
					codeValidations ++;
				}
				
				if(spa.getTestCaseMaximumValue()){
					highInputs++;
				}
				
				if(spa.getTestCaseMinimalValue()){
					lowInuts++;
				}
				
				if(spa.getTestCaseInvalidData()){
					productionGrade ++;
				}
			}
			
			SPACodeMetrics codeMetrics = new SPACodeMetrics();
			codeMetrics.setSyntaxAwarenessPercent((100 * syntaxAwareness)/spas.size());
			codeMetrics.setCodeIntegrityPercent((100 * codeIntegrity)/spas.size());
			codeMetrics.setCodeValidationsPercent((100 * codeValidations)/spas.size());
			
			codeMetrics.setHighInputPercent((100 * highInputs)/spas.size());
			codeMetrics.setLowInputPercent((100 * lowInuts)/spas.size());
			codeMetrics.setProductionGradePercent((100 * productionGrade)/spas.size());
			mav.addObject("codingTraits", codeMetrics);
		}
		
		if(fsapps.size() > 0){
			mav.addObject("fullstackTraitsPresent", true);
			List<FullStackCodeMetrics> fullStackCodeMetrics  = new ArrayList<>();
			for(QuestionMapperInstance instance : fsapps){
				FullStackCodeMetrics codeMetrics = new FullStackCodeMetrics();
				codeMetrics.setProblemDesc(instance.getQuestionMapper().getQuestion().getQuestionText());
				codeMetrics.setNoOfTestCases(instance.getNoOfTestCases());
				codeMetrics.setNoOfTestCasesPassed(instance.getNoOfTestCasesPassed());
				fullStackCodeMetrics.add(codeMetrics);
			}
			mav.addObject("fullstackTraits", fullStackCodeMetrics);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/showReports", method = RequestMethod.GET)
	public ModelAndView showReports(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("reports");
		User user = (User) request.getSession().getAttribute("user");
		AssessmentReportDataManager assessmentReportDataManager = new AssessmentReportDataManager(testService, userTestSessionRepository, sectionService, userService, user.getCompanyId(), user.getFirstName()+" "+user.getLastName());
		//assessmentReportDataManager.setTestService(testService);
		Collection<AssessmentTestPerspectiveData> data = assessmentReportDataManager.getTestPerspectiveData();
		mav.addObject("testsessions", data);
		mav.addObject("reportType", "Tests & Users Assessment Reports");
		return mav;
	}
	
	@RequestMapping(value = "/showReport", method = RequestMethod.GET)
	public ModelAndView showReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("report");
		
		return mav;
	}
	
//	@RequestMapping(value = "/downloadUserReportsForTestWithExtraAttributes", method = RequestMethod.GET)
//    public ResponseEntity<InputStreamResource> downloadUserReportsForTestWithExtraAttributes(@RequestParam String testName, HttpServletRequest request, HttpServletResponse response)
//    {
//        try
//        {
//        	User user = (User) request.getSession().getAttribute("user");
//        	AssessmentReportDataManager assessmentReportDataManager = new AssessmentReportDataManager(userTestSessionRepository, sectionService, userService, user.getCompanyId(), user.getFirstName()+" "+user.getLastName());
//    		List<AssessmentUserPerspectiveData> collection = assessmentReportDataManager.getUserPerspectiveData();
//    		List<AssessmentUserPerspectiveData> collectionForTest = new ArrayList<>();
//    			for(AssessmentUserPerspectiveData data : collection) {
//    				if(data.getTestName().equals(testName)) {
//    					collectionForTest.add(data);
//    				}
//    			}
//    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//    		String date = formatter.format(new Date());
//    		String fileName = reportManager.generateUserPerspectiveReport(collectionForTest, user.getFirstName()+" "+user.getLastName(), date) ;
//    		File file = new File(fileName);
//            HttpHeaders respHeaders = new HttpHeaders();
//            MediaType mediaType = MediaType.parseMediaType("application/pdf");
//            respHeaders.setContentType(mediaType);
//            respHeaders.setContentLength(file.length());
//            respHeaders.setContentDispositionFormData("attachment", file.getName());
//            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
//            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
//        }
//        catch (Exception e)
//        {
////            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
////            logger.error(message, e);
//            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//		
//    }
	
	@RequestMapping(value = "/downloadUserSessionReportsForTest", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadUserSessionReportsForTest(@RequestParam String testName,@RequestParam String companyId, @RequestParam String email, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	User user = userService.findByPrimaryKey(email, companyId);
        	List<QuestionMapperInstance> instances = rep.findQuestionMapperInstancesForUserForTest(testName, email, companyId);
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
        		
        		answerData.setFirstName(user.getFirstName());
        		answerData.setLastName(user.getLastName());
        		answerData.setEmail(email);
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
        
    		String fileName = reportManager.generateUserSessionReport(testAnswerDatas, user.getFirstName()+" "+user.getLastName(), testName);
    		File file = new File(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/vnd.ms-excel");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
//            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
//            logger.error(message, e);
        	e.printStackTrace();
        	logger.error("error in downloadUserSessionReportsForTest", e);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }
	
	@RequestMapping(value = "/downloadUserReportsForTestWithExtraAttrs", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadUserReportsForTestWithExtraAttrs(@RequestParam(required=false) Float passScore, @RequestParam String testName, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	User user = (User) request.getSession().getAttribute("user");
        	String companyId = user.getCompanyId();
    		String fileName = reportManager.generateUserSessionsReportForTest(passScore, testName, companyId);
    		File file = new File(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/vnd.ms-excel");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
//            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
//            logger.error(message, e);
        	e.printStackTrace();
        	logger.error("error in downloadUserReportsForTestWithExtraAttrs", e);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }
	
	//
	@RequestMapping(value = "/downloadUserReportsForTest", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadUserReportsForTest(@RequestParam(required=false) Float passScore, @RequestParam String testName, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	User user = (User) request.getSession().getAttribute("user");
        	AssessmentReportDataManager assessmentReportDataManager = new AssessmentReportDataManager(testService, userTestSessionRepository, sectionService, userService, user.getCompanyId(), user.getFirstName()+" "+user.getLastName());
        	//assessmentReportDataManager.setTestService(testService);
        	List<AssessmentUserPerspectiveData> collection = assessmentReportDataManager.getUserPerspectiveData();
    		List<AssessmentUserPerspectiveData> collectionForTest = new ArrayList<>();
    			for(AssessmentUserPerspectiveData data : collection) {
    				if(data.getTestName().equals(testName)) {
    					data.setCompanyId(user.getCompanyId());
    					data.setUrlForUserSession(propertyConfig.getBaseUrl()+"downloadUserSessionReportsForTest?testName="+URLEncoder.encode(testName)+"&companyId="+user.getCompanyId()+"&email="+URLEncoder.encode(data.getEmail()));
    					collectionForTest.add(data);
    				}
    				
    				//if(passScore != null && data.getOverAllScore() >= passScore){
    				if(passScore != null && (data.getWeightedScore()==null?data.getOverAllScore():data.getWeightedScore()) >= passScore){
    					data.setPass(true);
    				}
    				else{
    					data.setPass(false);
    				}
    			}
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		String date = formatter.format(new Date());
    		String fileName = reportManager.generateUserPerspectiveReport(collectionForTest, user.getFirstName()+" "+user.getLastName(), date) ;
    		File file = new File(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
//            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
//            logger.error(message, e);
        	e.printStackTrace();
        	logger.error("error in downloadUserReportsForTest", e);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }

	@RequestMapping(value = "/downloadUserReport", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadUserReport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	User user = (User) request.getSession().getAttribute("user");
        	AssessmentReportDataManager assessmentReportDataManager = new AssessmentReportDataManager(testService, userTestSessionRepository, sectionService, userService, user.getCompanyId(), user.getFirstName()+" "+user.getLastName());
    		List<AssessmentUserPerspectiveData> collection = assessmentReportDataManager.getUserPerspectiveData();
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		String date = formatter.format(new Date());
    		String fileName = reportManager.generateUserPerspectiveReport(collection, user.getFirstName()+" "+user.getLastName(), date) ;
    		File file = new File(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
//            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
//            logger.error(message, e);
        	e.printStackTrace();
        	logger.error("error in downloadUserReport", e);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }
	
	@RequestMapping(value = "/downloadTestReport", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadTestReport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	User user = (User) request.getSession().getAttribute("user");
        	AssessmentReportDataManager assessmentReportDataManager = new AssessmentReportDataManager(testService, userTestSessionRepository, sectionService, userService, user.getCompanyId(), user.getFirstName()+" "+user.getLastName());
    		Collection<AssessmentTestPerspectiveData> collection = assessmentReportDataManager.getTestPerspectiveData();
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		String date = formatter.format(new Date());
    		String fileName = reportManager.generateTestPerspectiveReport(collection, user.getFirstName()+" "+user.getLastName(), date) ;
    		File file = new File(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
//            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
//            logger.error(message, e);
        	e.printStackTrace();
        	logger.error("error in downloadTestReport", e);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }
	
	@RequestMapping(value = "/showSkillTags", method = RequestMethod.GET)
	public ModelAndView showSkillTags(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView("skill_reports2");
		Set<Qualifiers> qs = questionRepository.getAllUniqueQualifiers(user.getCompanyId());
		mav.addObject("skills", qs);
		return mav;
	}
	//Set<Qualifiers> qs = questionRepository.getAllUniqueQualifiers("IH");
	
	@RequestMapping(value = "/downloadUserReportsForSkill", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadUserReportsForSkill(@RequestParam String skillName, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
        	User user = (User) request.getSession().getAttribute("user");
        	List<QuestionMapperInstance> instances = questionMapperInstanceService.getInstancesOR(skillName, user.getCompanyId());
        	Map<String, List<QuestionMapperInstance>> user_answers_map = new HashMap<>();
        	for(QuestionMapperInstance instance : instances){
        		if(user_answers_map.get(instance.getUser()) == null){
        			List<QuestionMapperInstance> userAnswers = new ArrayList<>();
        			userAnswers.add(instance);
        			user_answers_map.put(instance.getUser(), userAnswers);
        		}
        		else{
        			user_answers_map.get(instance.getUser()).add(instance);
        		}
        	}
        	List<UserBySkillDTO> dtos = new ArrayList<>();
        	for(String email : user_answers_map.keySet() ){
        		List<QuestionMapperInstance> ins = user_answers_map.get(email);
        		int correct = 0;
        		Set<String> testNames = new HashSet<>();
        		for(QuestionMapperInstance instance : ins){
        			testNames.add(instance.getTestName());
        			if(instance.getCorrect()){
        				correct++;
        			}
        			testNames.add(instance.getTestName());
        		}
        		Float per = (float) ((100 * correct)/ins.size());
        		UserBySkillDTO userBySkillDTO = new UserBySkillDTO();
        		userBySkillDTO.setEmail(email);
        		User usr = userService.findByPrimaryKey(email, user.getCompanyId());
        		userBySkillDTO.setFirstName(usr.getFirstName());
        		userBySkillDTO.setLastName(usr.getLastName());
        		String percent = String.format("%.02f", per);
        		userBySkillDTO.setScoreInPercentage(percent);
        		String tests = StringUtils.join(testNames, ",");
        		userBySkillDTO.setTests(tests);
        		userBySkillDTO.setNoOfQuestionsAttempted(ins.size());
        		userBySkillDTO.setCompanyId(user.getCompanyId());
        		userBySkillDTO.setSkill(skillName);
        		dtos.add(userBySkillDTO);
        	}
        	
        	Collections.sort(dtos, new Comparator<UserBySkillDTO>() {

				@Override
				public int compare(UserBySkillDTO o1, UserBySkillDTO o2) {
					// TODO Auto-generated method stub
					return (int)(Float.parseFloat(o2.getScoreInPercentage()) - Float.parseFloat(o1.getScoreInPercentage()));
				}
			});
        	
        	String fileName = reportManager.generateUsersBySkillReport(dtos, skillName) ;
    		File file = new File(fileName);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
//            String message = "Errore nel download del file "+idForm+".csv; "+e.getMessage();
//            logger.error(message, e);
        	e.printStackTrace();
        	logger.error("error in downloadUserReportsForSkill", e);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }
	
	
	//Now methods added by Gulrez
	// created by Gulrez
	@GetMapping("/fetchTestList")
	public @ResponseBody Map<String, Object> fetchTestList(final HttpServletRequest request,
	@RequestParam String companyId) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("TestName: "+companyId);
		List<UserTestSession>listTest = userTestSessionRepository.findTestList(companyId);
		System.out.println("size: " + listTest.size());
		// List<String> list = new ArrayList<>();
		// for (UserTestSession test : listTest) {
		// list.add(test.getUser());
		// }
		map.put("listTest", listTest);
		return map;
	}
	
	@GetMapping("/downloadReport")
	public ModelAndView downloadReport(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("filterReport");
		User user = (User) request.getSession().getAttribute("user");
		List<Company> companyList = companyRepository.findAll();
		mav.addObject("list", companyList);
		return mav;
	}
	
	
	@RequestMapping(value = "/dateWiseReport", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> dateWiseReport(@RequestParam String testName,
	@RequestParam String startDate, @RequestParam String endDate, @RequestParam String companyId,
	HttpServletRequest request, HttpServletResponse response) {
		long start = 0L;
		long end = 0L;
		start = System.currentTimeMillis();
			try {
				User user = (User) request.getSession().getAttribute("user");
				AssessmentReportDataManager assessmentReportDataManager = new AssessmentReportDataManager(
				testName, testService, userTestSessionRepository, sectionService, userService, companyId,
				user.getFirstName() + " " + user.getLastName());
				List<AssessmentUserPerspectiveData> collection = assessmentReportDataManager
				.getUserPerspectiveData();
				List<AssessmentUserPerspectiveData> collectionForTest = new ArrayList();
				System.out.println(endDate);
				String format = "dd-MM-yyyy";
				Date startDate2=new SimpleDateFormat(format).parse(startDate);  
				Date endDate2=new SimpleDateFormat(format).parse(endDate);  
				Calendar c = Calendar.getInstance();
				c.setTime(endDate2);
				c.add(Calendar.DATE, 1);
				endDate2 = c.getTime();
				System.out.println(endDate2);
				System.out.println(collection.size());
					for (AssessmentUserPerspectiveData data : collection) {
					String testDateString2 = data.getTestStartDate();
					DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Date d2 = df2.parse(testDateString2);
						if (startDate2.compareTo(d2) <= 0 && endDate2.compareTo(d2) >= 0) {
							if(data.getTestName().equalsIgnoreCase(testName)) {
						
							data.setCompanyId(companyId);
							data.setUrlForUserSession(
							propertyConfig.getBaseUrl()
							+ "downloadUserSessionReportsForTest?testName="
							+ testName
							+ "&companyId="
							+ companyId
							+ "&email="
							+ data.getEmail());
							collectionForTest.add(data);
						
							}
						}
					}
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String date = formatter.format(new Date());
				String fileName = reportManager.generateUserPerspectiveReport(collectionForTest,
				user.getFirstName() + " " + user.getLastName(), date);
				File file = new File(fileName);
				HttpHeaders respHeaders = new HttpHeaders();
				MediaType mediaType = MediaType.parseMediaType("application/pdf");
				respHeaders.setContentType(mediaType);
				respHeaders.setContentLength(file.length());
				respHeaders.setContentDispositionFormData("attachment", file.getName());
				InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
				end = System.currentTimeMillis();
				System.out.println("ReportsController.downloadUserReportsForTest() has taken "
				+ (end - start) + " ms to complete the execution");
				return new ResponseEntity(isr, respHeaders, HttpStatus.OK);
			} 
			catch (Exception e) {
				e.printStackTrace();
				logger.error("error in downloadUserReportsForTest", e);
			}
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@InitBinder
	public void initConverter(WebDataBinder binder) {
	CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	binder.registerCustomEditor(Date.class, dateEditor);
	}
}
