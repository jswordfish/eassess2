package com.assessment.web.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.CommonUtil;
import com.assessment.common.PropertyConfig;
import com.assessment.common.util.EmailGenericMessageThread;
import com.assessment.data.FullStackOptions;
import com.assessment.data.Question;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.Test;
import com.assessment.data.TestCase;
import com.assessment.data.TestCases;
import com.assessment.data.User;
import com.assessment.eclipseche.config.response.WorkspaceResponse;
import com.assessment.eclipseche.services.EclipseCheService;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.UserService;
import com.assessment.web.dto.TestCaseStatistics;
import com.assessment.web.dto.TestCasesMetric;
import com.ibm.icu.text.DateFormat;
@Controller
public class FullStackConttroller {
	
	Logger logger = LoggerFactory.getLogger(FullStackConttroller.class);
	
@Autowired	
PropertyConfig propertyConfig;

@Autowired
QuestionMapperInstanceRepository  questionMapperInstanceRep;

@Autowired
QuestionMapperInstanceService questionMapperInstanceService;

@Autowired
UserService userService;

	@RequestMapping(value = "/configureWorkspace", method = RequestMethod.POST)
	public @ResponseBody String configureWorkspace(@RequestParam Long instanceid, HttpServletRequest request, HttpServletResponse response) {
//		QuestionMapperInstance instance = questionMapperInstanceRep.findById(instanceid).get();
//		
//			try{
//				Thread.sleep(20000);
//				System.out.println("first time trying to move");
//				moveWorkspaceFile(instance);//1st time
//				
//				
//				Thread.sleep(20000);
//				System.out.println("2 time trying to move");
//				moveWorkspaceFile(instance);//2nd time
//				
//				Thread.sleep(20000);
//				System.out.println("3 time trying to move");
//				moveWorkspaceFile(instance);//3 time
//				
//				Thread.sleep(20000);
//				System.out.println("4 time trying to move");
//				moveWorkspaceFile(instance);//4 time
//				
//				Thread.sleep(20000);
//				System.out.println("5 time trying to move");
//				moveWorkspaceFile(instance);//5 time
//				
//				Thread.sleep(20000);
//				System.out.println("6 time trying to move");
//				moveWorkspaceFile(instance);//6 time
//			}
//			catch(InterruptedException e){
//				
//			}
		 return "ok";
	}

	private void moveWorkspaceFile(QuestionMapperInstance instance){
		try {
			String baseCodePath = propertyConfig.getFullStackCodeLocation();
			 String fin = instance.getWorkspaceUrl() != null ? (instance.getWorkspaceUrl().substring(instance.getWorkspaceUrl().lastIndexOf("/")+1, instance.getWorkspaceUrl().length())):"";
			 System.out.println("fin is "+fin);
			// String path = baseCodePath + File.separator + instance.getWorkSpaceId() + File.separator + fin;
			 String path = baseCodePath + File.separator + instance.getWorkSpaceId() + File.separator + "projects"+File.separator + fin;
			 path += File.separator + "problem.txt";
			 System.out.println("problem file location "+path);
			 System.out.println("desst folder loc "+baseCodePath + File.separator + instance.getWorkSpaceId() + File.separator);
			 FileUtils.moveFileToDirectory(new File(path), new File(baseCodePath + File.separator + instance.getWorkSpaceId() + File.separator), false);
			 System.out.println("File moved ***********");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("can not move problem file", e);
			System.out.println("can not move problem file"+e.getClass());
		}
	}

	@RequestMapping(value = "/gotofullstack", method = RequestMethod.GET)
	public ModelAndView showLogin(@RequestParam String workspace, HttpServletRequest request, HttpServletResponse response) {
	String url = URLDecoder.decode(workspace);
	url = new String(Base64.getDecoder().decode(url.getBytes()));
	  ModelAndView mav = new ModelAndView("fullstack");
	  mav.addObject("url", url);
	  return mav;
	}
	
	@RequestMapping(value = "/createWorkSpaceForUser", method = RequestMethod.GET)
    public @ResponseBody String  addQuestionsToSectionAjax(@RequestParam String userName, @RequestParam String stackName, @RequestParam String questionId,@RequestParam String testId, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
	 User user = (User) request.getSession().getAttribute("user");
	 	if(user == null){
	 		return "Log in Again";
	 	}
 	 userName = userName.replace(" ", "");
 	 if(stackName.equals("Java")){
 		 String json = FileUtils.readFileToString(new File("eclipseChe/Java_FullStack.json"));
 		json = json.replace("${APP_USER}", userName+"="+testId+"-"+questionId+"-"+System.currentTimeMillis());
 		//json = json.replace("${APP_USER}", "a01");
 		json = json.replace("${APP_DESC}", "Skeleton Code............................Project\n\n\n.........");
 		EclipseCheService eclipseCheService = new EclipseCheService();
 		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
 		return workspaceResponse.getLinks().getIde();
 	 }
	
		
        return "";
    }
	
	 @RequestMapping(value = "/submitFullStackCode", method = RequestMethod.GET)
	  public @ResponseBody String submitFullStackCode(HttpServletRequest request, HttpServletResponse response,@RequestParam String qMapperInstanceId) throws Exception {
		 ModelAndView model= new ModelAndView("test");
		 User user = (User) request.getSession().getAttribute("user");
		 Test test = (Test) request.getSession().getAttribute("test");
		 QuestionMapperInstance questionMapperInstance = questionMapperInstanceRep.findById(Long.parseLong(qMapperInstanceId)).get();
		 questionMapperInstance.setWorkspaceSubmitted(true);
		 questionMapperInstance.setTestId(test.getId());
		 questionMapperInstanceRep.save(questionMapperInstance);
		 String workSpaceId = questionMapperInstance.getWorkSpaceId();
		 //String projname = 
		 String workSpaceFolder = questionMapperInstance.getWorkspaceUrl().substring(questionMapperInstance.getWorkspaceUrl().indexOf("che/")+4, questionMapperInstance.getWorkspaceUrl().length());
		// String codebasePath = propertyConfig.getFullStackCodeLocation()+File.separator+workSpaceId+File.separator+workSpaceFolder;
		 String codebasePath = propertyConfig.getFullStackCodeLocation()+File.separator+workSpaceId+File.separator+"projects"+File.separator+workSpaceFolder;
		 //String codebasePath = propertyConfig.getFullStackCodeLocation()+File.separator+workSpaceId+File.separator+"projects"+File.separator+workSpaceFolder;
		 
		 System.out.println(" workSpaceFolder is "+workSpaceFolder);
		 System.out.println(" codebasePath is "+codebasePath);
		 /**
		  * step 1 create a file called sonar-project.properties in workspace foldewr
		  * 
		  * */
		 String analysisFile = "";
		 String stack = questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack();
		 if(questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack().equalsIgnoreCase(FullStackOptions.JAVA_FULLSTACK.getStack())){
			System.out.println("doing code quality on java stack");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileLocation()));
		 }
		 else if(questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack().equalsIgnoreCase(FullStackOptions.JAVA_MONGODB.getStack())){
				System.out.println("doing code quality on java mongodb stack");
				 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileLocation()));
			 }
		 else if(questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack().equalsIgnoreCase(FullStackOptions.PHP_FULLSTACK.getStack())) {
			 System.out.println("doing code quality on php stack");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonalAnalysisFilePHPLocation()));
		 }
		 else if(questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack().equalsIgnoreCase(FullStackOptions.ANGULARJS_FULLSTACK.getStack()) ) {
			 System.out.println("doing code quality on php stack");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileAngularLocation()));
		 }
		 else if(questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack().equalsIgnoreCase(FullStackOptions.JAVASCRIPT_FULLSTACK.getStack()) ) {
			 System.out.println("doing code quality on php stack");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileAngularLocation()));
		 }
		 else if(questionMapperInstance.getQuestionMapper().getQuestion().getFullstack().getStack().equalsIgnoreCase(FullStackOptions.DOTNET_FULLSTACK.getStack()) ) {
			 System.out.println("doing code quality on dot net stack");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileDotNetLocation()));
		 }
		 else if(stack.equalsIgnoreCase(FullStackOptions.DOTNET_MONGODB.getStack())){
			 System.out.println("doing code quality on dot net stack mongo db");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileDotNetLocation()));

		 }
		 else{
			 System.out.println("doing code quality on others stack");
			 analysisFile = FileUtils.readFileToString(new File(propertyConfig.getSonarAnalysisFileLocation()));
		 }
	
		analysisFile = analysisFile.replace("${key}", workSpaceFolder);
		String writeLoc = codebasePath+File.separator+"sonar-project.properties";
		System.out.println(" writeLoc is "+writeLoc);
		try {
			System.out.println(" writting");
			FileUtils.write(new File(writeLoc), analysisFile);
			System.out.println(" written");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" analysis file written is ");
		 /**
		  * step 2 create a file called analyze.sh in workspace foldewr
		  * 
		  */
		 //not needed
		 
		 /**
		  * Step 3 Analyze
		  */
		 Process process;
		try {
			System.out.println("1 ");
			ProcessBuilder builder = new ProcessBuilder();
			System.out.println("2 ");
			// String[] command = {"/opt/softwares/sonar-scanner-3.3.0.1492/bin/sonar-scanner"};
			//String[] command = {"/opt/sonar-scanner-4.0.0.1744-linux/bin/sonar-scanner"};
			String[] command = {"/opt/sonar-scanner/sonar-scanner-4.0.0.1744-linux/bin/sonar-scanner"};
			
			 System.out.println("3 ");
			 builder.redirectErrorStream(true); // This is the important part
			 System.out.println("4 ");
			 builder.command(command);
			 System.out.println("5 "+codebasePath);
			 builder.directory(new File(codebasePath));
			 System.out.println("6 ");
			 process = builder.start();
			 System.out.println(" command given");
			 LogStreamReader lsr = new LogStreamReader(process.getInputStream());
			 System.out.println("7 ");
			 Thread thread = new Thread(lsr, "LogStreamReader");
			 thread.start();
			 System.out.println("8 ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// InputStream is = process.getInputStream();
		 
		 String html = FileUtils.readFileToString(new File(propertyConfig.getCodeQualityEmailTemplateLocation()));
		 String url = propertyConfig.getCodeQualityServerLink()+URLEncoder.encode(workSpaceFolder);
		 html = html.replace("{FULL_NAME}", user.getFirstName()+" "+user.getLastName());
		 html = html.replace("{TEST_NAME}", test.getTestName());
		 html = html.replace("{CODE_QUALITY_URL}", url);
		 
		 String reviewer = questionMapperInstance.getQuestionMapper().getQuestion().getReviewerEmail();
		 html = html.replace("{PROJECT_URL}", questionMapperInstance.getUsageDocumentUrl()==null?"":questionMapperInstance.getUsageDocumentUrl());
		 html = html.replace("{REVIEWER_EMAIL}", reviewer);
		 html = html.replace("{PASSWORD}", ""+reviewer.hashCode());
		 html = html.replace("{Company}", user.getCompanyName());
		 html = html.replace("{BASE_URL}", propertyConfig.getBaseUrl()+"login");
		// String message = "Results can not be sent for "+user.getEmail()+" for test "+test.getTestName();
		 System.out.println(" sending mail with foll link "+url);
		 TestCasesMetric casesMetric = initiateGenericAutomation(workSpaceId, request, questionMapperInstance, url);
		 System.out.println("testmetrics "+casesMetric);
			if(casesMetric != null){
				System.out.println("testmetrics 1111111111");
				String message = "";
				message += "<b>Run Time Test Execution Summary</b><br/>";
				message += "Problem Statement - "+casesMetric.getProblemStatement()+"<br/>";
				message += "No Of Test Cases - "+casesMetric.getNoOfTestCases()+"<br/>";
				message += "No Of Test Cases Passed - "+casesMetric.getTestCasesPassed();
				questionMapperInstance.setNoOfTestCases(casesMetric.getNoOfTestCases());
				questionMapperInstance.setNoOfTestCasesPassed(casesMetric.getTestCasesPassed());
				questionMapperInstance.setTestId(test.getId());
				questionMapperInstanceRep.save(questionMapperInstance);
				if(html.contains("${BEHAVIOUR}")){
					System.out.println("testmetrics goood");
				}
				else{
					System.out.println("testmetrics bad");
				}
				html = html.replace("${BEHAVIOUR}", message);
			}
			
			html = html.replace("${BEHAVIOUR}", "");//just make sure this is not there for tests not having automation
		 	EmailGenericMessageThread client = new EmailGenericMessageThread("jatin.sutaria@thev2technologies.com", "Code quality Report Link for "+user.getFirstName(), html, propertyConfig);
		 	client.setEmailSentCC(reviewer);
		 	//String cc[] = {reviewer};
		 	//client.setCcArray(cc);
		 	Thread th = new Thread(client);
			th.start();
			
		 return "Your code has been submitted for verification. This is a 2 step process - Code quality (or behaviour compliance if configured) will be measured through automation and other compilances will be judged by reviewer.";
	 }
	 
	 
	 private TestCasesMetric initiateGenericAutomation(String workspaceId, HttpServletRequest request, QuestionMapperInstance questionMapperInstance, String codeQualityUrl) throws IOException{
		 User user = userService.findByPrimaryKey(questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
		 String baseCodePath = propertyConfig.getFullStackCodeLocation();
		 String fin = questionMapperInstance.getWorkspaceUrl() != null ? (questionMapperInstance.getWorkspaceUrl().substring(questionMapperInstance.getWorkspaceUrl().lastIndexOf("/")+1, questionMapperInstance.getWorkspaceUrl().length())):"";
		 System.out.println("fin is "+fin);
		// String path = baseCodePath + File.separator + workspaceId + File.separator + fin;
		 String path = baseCodePath + File.separator + workspaceId + File.separator + "projects"+File.separator+fin;
		 path += File.separator + "output.txt";
		 System.out.println(" path is "+path);
		// TestCasesMetric casesMetric = processResults(questionMapperInstance, (baseCodePath + File.separator + workspaceId + File.separator + fin), codeQualityUrl);
		 TestCasesMetric casesMetric = processResults(request, questionMapperInstance, (baseCodePath + File.separator + workspaceId + File.separator +"projects" +File.separator + fin), codeQualityUrl);
		 return casesMetric;
//		 File output = new File(path);
//		 if(!output.exists()){
//			 System.out.println(path+" does not exist");
//			 casesMetric.setAvailable(false);
//			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
//			 casesMetric.setCodeQualityLink(codeQualityUrl);
//			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
//			 return casesMetric;
//		 }
//		 else{
//			 Properties props = new Properties();
//			 FileInputStream fis = new FileInputStream(output);
//			 props.load(fis);
//			 fis.close();
//			 int total = Integer.parseInt(((String)props.get("Total")).trim());
//			 int failed = Integer.parseInt(((String)props.get("Failed")).trim());
//			 int ignored = Integer.parseInt(((String)props.get("Ignore")).trim());
//			 casesMetric.setNoOfTestCases(total);
//			 casesMetric.setTestCasesPassed(total - (failed + ignored));
//			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
//			 casesMetric.setCodeQualityLink(codeQualityUrl);
//			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
//			 questionMapperInstance.setNoOfTestCases(total);
//			 questionMapperInstance.setNoOfTestCasesPassed(total - (failed + ignored));
//			 questionMapperInstanceRep.save(questionMapperInstance);
//			 return casesMetric;
//		 }
//		 if(workspaceId.contains("psk2y2afb3ecogbh")){
//			 System.out.println("00000000000000000");
//			 String path = "/root/.che-multiuser/instance/data/workspaces/workspacepsk2y2afb3ecogbh/regex_jdbc";
//			 String outputFile = ""+System.currentTimeMillis()+".txt";
//			 String[] command = ("/usr/bin/mvn -Dtest=com.assignment.regex.TestPatternFinder test --log-file "+outputFile).split("\\s+");
//			 System.out.println(command);
//			 ProcessBuilder builder = new ProcessBuilder();
//			 builder.command(command);
//			 builder.directory(new File(path));
//			 Process process = builder.start();
//			 LogStreamReader lsr = new LogStreamReader(process.getInputStream());
//			 System.out.println("regex problem ");
//			 Thread thread = new Thread(lsr, "LogStreamReader");
//			 thread.start();
//			 try{
//				 Thread.sleep(10000);
//			 }
//			 catch(InterruptedException e){
//				 
//			 }
//			 String op_path = path+"/"+outputFile;
//			 List<String> lines = FileUtils.readLines(new File(op_path));
//			 Integer noOfTestCases = 0;
//			 Integer noOfTestCasesFailed = 0;
//			 for(String line : lines){
//				 if(line.contains("Tests run:")){
//					 
//					 String split[] = line.split(",");
//					 for(String unit : split){
//						 String test[] = unit.split(":");
//						 System.out.println(test[0]);
//						 System.out.println(test[1]);
//						 if(test[0].trim().equals("Tests run")){
//							 noOfTestCases = Integer.parseInt(test[1].trim());
//						 }
//						 
//						 if(test[0].trim().equals("Failures")){
//							 noOfTestCasesFailed = Integer.parseInt(test[1].trim());
//							 break;
//						 }
//					 }
//					 
//					break;
//				 }
//			 }
//			 
//			 String testcaseResults = "regex.txt";
//			 String line1 = "noOfTestCases$$$"+noOfTestCases;
//			 String line2 = "noOfTestCasesFailed$$$"+noOfTestCasesFailed;
//			 String pb = questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText().replaceAll("\n", "<br/>").replace("\r", "");
//			 String line3 = "problem$$$"+pb;
//			 String line4 = "name$$$"+user.getFirstName()+" "+user.getLastName();
//			 String line5 = "testName$$$"+questionMapperInstance.getTestName();
//			 String line6 = "codeQualityLink$$$"+codeQualityUrl;
//			 String line7 = "usageLink$$$"+questionMapperInstance.getUsageDocumentUrl();
//			 List<String> writelines = new ArrayList<>();
//			 writelines.add(line1);
//			 writelines.add(line2);
//			 writelines.add(line3);
//			 writelines.add(line4);
//			 writelines.add(line5);
//			 writelines.add(line6);
//			 writelines.add(line7);
//			 
//			 FileUtils.writeLines(new File(testcaseResults), writelines);
//			 TestCasesMetric casesMetric = new TestCasesMetric();
//			 casesMetric.setNoOfTestCases(noOfTestCases);
//			 casesMetric.setTestCasesPassed(noOfTestCases - noOfTestCasesFailed);
//			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
//			 casesMetric.setCodeQualityLink(codeQualityUrl);
//			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
//			 return casesMetric;
//		 }
//		 else if(workspaceId.contains("zigca3iu5ynpydhp")){
//			 System.out.println("1111111111111111111111111111");
//			 String path = "/root/.che-multiuser/instance/data/workspaces/workspacezigca3iu5ynpydhp/JohnDoe-48-47-1568040746461";
//			// String path = "/root/.che-multiuser/instance/data/workspaces/workspacek9jw1ghhtr724g7q/console-java-simple";
//			 String outputFile = ""+System.currentTimeMillis()+".txt";
//			 String[] command = ("/usr/bin/mvn -Dtest=com.problem1.TestProblem1 test --log-file "+outputFile).split("\\s+");
//			 System.out.println("command is "+command.toString());
//			 ProcessBuilder builder = new ProcessBuilder();
//			 builder.command(command);
//			 builder.directory(new File(path));
//			 Process process = builder.start();
//			 LogStreamReader lsr = new LogStreamReader(process.getInputStream());
//			 System.out.println("collection problem ");
//			 Thread thread = new Thread(lsr, "LogStreamReader");
//			 thread.start();
//			 try{
//				 Thread.sleep(10000);
//			 }
//			 catch(InterruptedException e){
//				 
//			 }
//			 System.out.println("test cases fired ");
//			 String op_path = path+"/"+outputFile;
//			 System.out.println("test cases result file"+op_path);
//			 List<String> lines = FileUtils.readLines(new File(op_path));
//			 System.out.println("no of lines in "+op_path+" is "+lines.size());
//			 Integer noOfTestCases = 0;
//			 Integer noOfTestCasesFailed = 0;
//			 for(String line : lines){
//				
//				 if(line.contains("Tests run:")){
//					 System.out.println("here !!!!!!!!!1 "+line);
//					 String split[] = line.split(",");
//					 for(String unit : split){
//						 String test[] = unit.split(":");
//						 System.out.println(test[0]);
//						 System.out.println(test[1]);
//						 if(test[0].trim().equals("Tests run")){
//							 System.out.println("test cases run ");
//							 noOfTestCases = Integer.parseInt(test[1].trim());
//						 }
//						 
//						 if(test[0].trim().equals("Failures")){
//							 System.out.println("test cases failed ");
//							 noOfTestCasesFailed = Integer.parseInt(test[1].trim());
//							 break;
//						 }
//					 }
//					 
//					break;
//				 }
//			 }
//			 
//			 String testcaseResults = "collections_logic.txt";
//			 String line1 = "noOfTestCases$$$"+noOfTestCases;
//			 String line2 = "noOfTestCasesFailed$$$"+noOfTestCasesFailed;
//			 String pb = questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText().replaceAll("\n", "<br/>").replace("\r", "");
//			 String line3 = "problem$$$"+pb;
//			 String line4 = "name$$$"+user.getFirstName()+" "+user.getLastName();
//			 String line5 = "testName$$$"+questionMapperInstance.getTestName();
//			 String line6 = "codeQualityLink$$$"+codeQualityUrl;
//			 String line7 = "usageLink$$$"+questionMapperInstance.getUsageDocumentUrl();
//			 List<String> writelines = new ArrayList<>();
//			 writelines.add(line1);
//			 writelines.add(line2);
//			 writelines.add(line3);
//			 writelines.add(line4);
//			 writelines.add(line5);
//			 writelines.add(line6);
//			 writelines.add(line7);
//			 
//			 FileUtils.writeLines(new File(testcaseResults), writelines);
//			 TestCasesMetric casesMetric = new TestCasesMetric();
//			 casesMetric.setNoOfTestCases(noOfTestCases);
//			 casesMetric.setTestCasesPassed(noOfTestCases - noOfTestCasesFailed);
//			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
//			 casesMetric.setCodeQualityLink(codeQualityUrl);
//			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
//			 System.out.println("Automation cases run");
//			 return casesMetric;
//		 }
//		 else{
//			 return null;
//		 }
	 }
	 
	 
	 /**
	  * output1 - functional
	  * output2 - boundary
	  * output3 - exception
	  * @param output1
	  * @param output2
	  * @param output3
	  * @param questionMapperInstance
	  * @return
	  */
	 private TestCaseStatistics check(File output1, File output2, File output3, QuestionMapperInstance questionMapperInstance){
	 String xml = questionMapperInstance.getQuestionMapper().getQuestion().getTestcasesXml();
		 
		 if( xml == null || xml.trim().length() == 0){
			 return null;
		 }
		 
		 
		 
		 try {
			TestCases testCases = (TestCases)Unmarshaller.unmarshal(TestCases.class, new StringReader(xml));
			if(testCases.getCases() == null ){
				return null;
			}
				
			 Properties props1 = new Properties();
			 FileInputStream fis1 = new FileInputStream(output1);
			 props1.load(fis1);
			 fis1.close();
			 
			 Properties props2 = new Properties();
			 FileInputStream fis2 = new FileInputStream(output2);
			 props2.load(fis2);
			 fis2.close();
			 
			 Properties props3 = new Properties();
			 FileInputStream fis3 = new FileInputStream(output3);
			 props3.load(fis3);
			 fis3.close();
			 
			 
			 Integer totalTestCases = 0;
			 Integer totalTestCasesPassed = 0;
			 Integer totalFunctionalTestCases = 0;
			 Integer totalFunctionalTestCasesPassed = 0;
			 Integer totalBoundaryTestCases = 0;
			 Integer totalBoundaryTestCasedPassed = 0;
			 Integer totalExceptionTestCases = 0;
			 Integer totalExceptionTestCasesPassed = 0;
			 List<TestCase> function = new ArrayList<>();
			 List<TestCase> boundary = new ArrayList<>();
			 List<TestCase> exception = new ArrayList<>();
			 Map<String, TestCase> map = new HashMap<>();
			 for(TestCase testCase : testCases.getCases()){
				 if(testCase.getTestCaseType().equalsIgnoreCase("functional")){
					 function.add(testCase);
					 map.put(testCase.getName(), testCase);
				 }
				 else if(testCase.getTestCaseType().equalsIgnoreCase("boundary")){
					 boundary.add(testCase);
					 map.put(testCase.getName(), testCase);
				 }
				 else if(testCase.getTestCaseType().equalsIgnoreCase("exception")){
					 exception.add(testCase);
					 map.put(testCase.getName(), testCase);
				 }
			 }
			 
			 if(props1 != null){
				 totalTestCases += function.size();
				 totalFunctionalTestCases = function.size();
				 
				 for(Object str : props1.keySet()){
					 String s = (String) str;
					 System.out.println("functional test case name "+s);
					 String val = props1.getProperty(s);
					 TestCase testCase = map.get(s);
					 if(testCase.getExpectedOuput() == null){
						 testCase.setPassed(false);
					 }
					 else if(val.equalsIgnoreCase(testCase.getExpectedOuput())){
						 System.out.println("test case passed "+str+" valus "+val+" expected "+testCase.getExpectedOuput());
						 testCase.setPassed(true);
						 totalFunctionalTestCasesPassed ++;
						 totalTestCasesPassed ++;
						 System.out.println("functional test case passed - "+testCase.getName());
					 }
					 else{
						 System.out.println("test case passed "+str+" valus "+val+" expected "+testCase.getExpectedOuput());
						 testCase.setPassed(false);
					 }
				 }
				 
			 }
			 if(props2 != null){
				 totalTestCases += boundary.size();
				 totalBoundaryTestCases = boundary.size();
				 for(Object str : props2.keySet()){
					 String s = (String) str;
					 System.out.println("boundary test case name "+s);
					 String output = props2.getProperty(s);
					 TestCase testCase = map.get(s);
					 if(output == null ){
						 if(testCase != null){
						 testCase.setPassed(false);
						 }
					 }
					 else if(testCase != null && output != null){
						 if(testCase.getExpectedOuput().equalsIgnoreCase(output)){
							 testCase.setPassed(true);
							 totalBoundaryTestCasedPassed ++;
							 System.out.println("boundary test case passed - "+testCase.getName());
							 totalTestCasesPassed ++;
						 }
						 else{
							 testCase.setPassed(false);
						 }
					 }
				 }
			 }
			 if(props3 != null){
				 totalTestCases += exception.size();
				 totalExceptionTestCases = exception.size();
				 
				 for(Object str : props3.keySet()){
					 String s = (String) str;
					 System.out.println("exception test case name "+s);
					 String output = props3.getProperty(s);
					 TestCase testCase = map.get(s);
					 if(output == null ){
						 if(testCase != null){
						 testCase.setPassed(false);
						 }
					 }
					 else if(testCase != null && output != null){
						 if(testCase.getExpectedOuput().equalsIgnoreCase(output)){
							 testCase.setPassed(true);
							 totalExceptionTestCasesPassed ++;
							 totalTestCasesPassed ++;
							 System.out.println("exception test case passed - "+testCase.getName());
						 }
						 else{
							 testCase.setPassed(false);
						 }
					 }
				 }
			 }
			 
			 
			 TestCaseStatistics caseStatistics = new TestCaseStatistics();
			 caseStatistics.setTestCases(testCases);
			 caseStatistics.setTotalTestCases(totalTestCases);
			 caseStatistics.setTotalTestCasesPassed(totalTestCasesPassed);
			 
			 caseStatistics.setTotalBoundaryTestCases(totalBoundaryTestCases);
			 caseStatistics.setTotalBoundaryTestCasedPassed(totalBoundaryTestCasedPassed);
			 
			 caseStatistics.setTotalExceptionTestCases(totalExceptionTestCases);
			 caseStatistics.setTotalExceptionTestCasesPassed(totalExceptionTestCasesPassed);
			 
			 caseStatistics.setTotalFunctionalTestCases(totalFunctionalTestCases);
			 caseStatistics.setTotalFunctionalTestCasesPassed(totalFunctionalTestCasesPassed);
			 return caseStatistics;
			 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	 }
	 
	 
	 private TestCasesMetric processResults(HttpServletRequest request, QuestionMapperInstance questionMapperInstance, String location, String codeQualityUrl) throws IOException{
		 TestCasesMetric casesMetric = new TestCasesMetric();
		// String path = location + File.separator + "output.txt";
		// System.out.println(" in processResults path is "+path);
		 
		 String path1 = location + File.separator + "output_revised.txt";
		 String path2 = location + File.separator + "output_boundary_revised.txt";
		 String path3 = location + File.separator + "output_exception_revised.txt";
		 File output1 = new File(path1);
		 File output2 = new File(path2);
		 File output3 = new File(path3);
		 	
		 
		
		 //File output = new File(path);

		 if(!output1.exists() && !output2.exists() && !output3.exists()){
		 		casesMetric.setAvailable(false);
				 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
				 casesMetric.setCodeQualityLink(codeQualityUrl);
				 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
				 return casesMetric;
		 	}
		 else{
			 TestCaseStatistics caseStatistics = check(output1, output2, output3, questionMapperInstance);
			 	if(caseStatistics ==null){
			 		casesMetric.setAvailable(false);
					 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
					 casesMetric.setCodeQualityLink(codeQualityUrl);
					 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
					 return casesMetric;
			 	}
			 
			 System.out.println("total test cases "+caseStatistics.getTotalTestCases());
			 System.out.println("totalTestCasesPassed "+caseStatistics.getTotalTestCasesPassed());
			 System.out.println("totalFunctionalTestCases "+caseStatistics.getTotalFunctionalTestCases());
			 System.out.println("totalFunctionalTestCasesPassed "+caseStatistics.getTotalFunctionalTestCasesPassed());
			 System.out.println("totalBoundaryTestCases "+caseStatistics.getTotalBoundaryTestCases());
			 System.out.println("totalBoundaryTestCasedPassed "+caseStatistics.getTotalBoundaryTestCasedPassed());
			 System.out.println("totalExceptionTestCases "+caseStatistics.getTotalExceptionTestCases());
			 System.out.println("totalExceptionTestCasesPassed "+caseStatistics.getTotalExceptionTestCasesPassed());
			
			
			 
			 casesMetric.setNoOfTestCases(caseStatistics.getTotalTestCases());
			 casesMetric.setTestCasesPassed(caseStatistics.getTotalTestCasesPassed());
			 casesMetric.setFunctionalTestCases(caseStatistics.getTotalFunctionalTestCases());
			 casesMetric.setFunctionalTestCasesPassed(caseStatistics.getTotalFunctionalTestCasesPassed());
			
			 casesMetric.setBoundaryTestCases(caseStatistics.getTotalBoundaryTestCases());
			 casesMetric.setBoundaryTestCasesPassed(caseStatistics.getTotalBoundaryTestCasedPassed());
			 
			 casesMetric.setExceptionTestCases(caseStatistics.getTotalExceptionTestCases());
			 casesMetric.setExceptionTestCasesPassed(caseStatistics.getTotalExceptionTestCasesPassed());
			 
			 
			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
			 casesMetric.setCodeQualityLink(codeQualityUrl);
			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
			 
			 questionMapperInstance.setNoOfTestCases(caseStatistics.getTotalTestCases());
			 questionMapperInstance.setNoOfTestCasesPassed(caseStatistics.getTotalTestCasesPassed());
			 questionMapperInstance.setFunctionalTestCases(caseStatistics.getTotalFunctionalTestCases());
			 questionMapperInstance.setFunctionalTestCasesPassed(caseStatistics.getTotalFunctionalTestCasesPassed());
			
			 questionMapperInstance.setBoundaryTestCases(caseStatistics.getTotalBoundaryTestCases());
			 questionMapperInstance.setBoundaryTestCasesPassed(caseStatistics.getTotalBoundaryTestCasedPassed());
			 
			 questionMapperInstance.setExceptionTestCases(caseStatistics.getTotalExceptionTestCases());
			 questionMapperInstance.setExceptionTestCasesPassed(caseStatistics.getTotalExceptionTestCasesPassed());
			 
			 StringWriter stringWriter = new StringWriter();
			 try {
				Marshaller.marshal(caseStatistics.getTestCases(), stringWriter);
				String xml = stringWriter.toString();
				 
				 questionMapperInstance.setTestCasesResultXml(xml);
				 System.out.println("Setting test cases xml on questionMapperInstance for user "+questionMapperInstance.getUser());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 Test test = (Test) request.getSession().getAttribute("test");
			 questionMapperInstance.setTestId(test.getId());
			 questionMapperInstanceRep.save(questionMapperInstance);
			 return casesMetric;
		 }
	 }
	 
	 
	 
//	 private TestCasesMetric processResults(QuestionMapperInstance questionMapperInstance, String location, String codeQualityUrl) throws IOException{
//		 TestCasesMetric casesMetric = new TestCasesMetric();
//		 String path = location + File.separator + "output.txt";
//		 System.out.println(" in processResults path is "+path);
//		
//		 File output = new File(path);
//		 Integer totalTestCases = 0;
//		 Integer totalTestCasesPassed = 0;
//		 Integer totalFunctionalTestCases = 0;
//		 Integer totalFunctionalTestCasesPassed = 0;
//		 Integer totalBoundaryTestCases = 0;
//		 Integer totalBoundaryTestCasedPassed = 0;
//		 Integer totalExceptionTestCases = 0;
//		 Integer totalExceptionTestCasesPassed = 0;
//		 if(!output.exists()){
//			 System.out.println(path+" does not exist");
//			 casesMetric.setAvailable(false);
//			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
//			 casesMetric.setCodeQualityLink(codeQualityUrl);
//			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
//			 return casesMetric;
//		 }
//		 else{
//			 Properties props = new Properties();
//			 FileInputStream fis = new FileInputStream(output);
//			 props.load(fis);
//			 fis.close();
//			 int total = Integer.parseInt(((String)props.get("Total")).trim());
//			 int failed = Integer.parseInt(((String)props.get("Failed")).trim());
//			 int ignored = Integer.parseInt(((String)props.get("Ignore")).trim());
//			 totalTestCases += total;
//			 totalFunctionalTestCases = total;
//			 totalFunctionalTestCasesPassed = total - (failed + ignored);
//			 totalTestCasesPassed +=  totalFunctionalTestCasesPassed;
//			 
//			 path = location + File.separator + "output_boundary.txt";
//			 output = new File(path);
//			 props = new Properties();
//			 fis = new FileInputStream(output);
//			 props.load(fis);
//			 fis.close();
//			 total = Integer.parseInt(((String)props.get("Total")).trim());
//			 failed = Integer.parseInt(((String)props.get("Failed")).trim());
//			 ignored = Integer.parseInt(((String)props.get("Ignore")).trim());
//			 totalTestCases += total;
//			 totalBoundaryTestCases = total;
//			 totalBoundaryTestCasedPassed = total - (failed + ignored);
//			 totalTestCasesPassed +=  totalBoundaryTestCasedPassed;
//			 
//			 path = location + File.separator + "output_exception.txt";
//			 output = new File(path);
//			 props = new Properties();
//			 fis = new FileInputStream(output);
//			 props.load(fis);
//			 fis.close();
//			 total = Integer.parseInt(((String)props.get("Total")).trim());
//			 failed = Integer.parseInt(((String)props.get("Failed")).trim());
//			 ignored = Integer.parseInt(((String)props.get("Ignore")).trim());
//			 totalTestCases += total;
//			 totalExceptionTestCases = total;
//			 totalExceptionTestCasesPassed = total - (failed + ignored);
//			 totalTestCasesPassed +=  totalExceptionTestCasesPassed;
//			 System.out.println("total test cases "+totalTestCases);
//			 System.out.println("totalTestCasesPassed "+totalTestCasesPassed);
//			 System.out.println("totalFunctionalTestCases "+totalFunctionalTestCases);
//			 System.out.println("totalFunctionalTestCasesPassed "+totalFunctionalTestCasesPassed);
//			 System.out.println("totalBoundaryTestCases "+totalBoundaryTestCases);
//			 System.out.println("totalBoundaryTestCasedPassed "+totalBoundaryTestCasedPassed);
//			 System.out.println("totalExceptionTestCases "+totalExceptionTestCases);
//			 System.out.println("totalExceptionTestCasesPassed "+totalExceptionTestCasesPassed);
//			
//			
//			 
//			 casesMetric.setNoOfTestCases(totalTestCases);
//			 casesMetric.setTestCasesPassed(totalTestCasesPassed);
//			 casesMetric.setFunctionalTestCases(totalFunctionalTestCases);
//			 casesMetric.setFunctionalTestCasesPassed(totalFunctionalTestCasesPassed);
//			
//			 casesMetric.setBoundaryTestCases(totalBoundaryTestCases);
//			 casesMetric.setBoundaryTestCasesPassed(totalBoundaryTestCasedPassed);
//			 
//			 casesMetric.setExceptionTestCases(totalExceptionTestCases);
//			 casesMetric.setExceptionTestCasesPassed(totalExceptionTestCasesPassed);
//			 
//			 
//			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
//			 casesMetric.setCodeQualityLink(codeQualityUrl);
//			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
//			 
//			 questionMapperInstance.setNoOfTestCases(totalTestCases);
//			 questionMapperInstance.setNoOfTestCasesPassed(totalTestCasesPassed);
//			 questionMapperInstance.setFunctionalTestCases(totalFunctionalTestCases);
//			 questionMapperInstance.setFunctionalTestCasesPassed(totalFunctionalTestCasesPassed);
//			
//			 questionMapperInstance.setBoundaryTestCases(totalBoundaryTestCases);
//			 questionMapperInstance.setBoundaryTestCasesPassed(totalBoundaryTestCasedPassed);
//			 
//			 questionMapperInstance.setExceptionTestCases(totalExceptionTestCases);
//			 questionMapperInstance.setExceptionTestCasesPassed(totalExceptionTestCasesPassed);
//			 
//			 questionMapperInstanceRep.save(questionMapperInstance);
//			 return casesMetric;
//		 }
//	 }
	 
	 private TestCasesMetric initiateAutomation(String workspaceId, HttpServletRequest request, QuestionMapperInstance questionMapperInstance, String codeQualityUrl) throws IOException{
		 User user = userService.findByPrimaryKey(questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
		 if(workspaceId.contains("psk2y2afb3ecogbh")){
			 System.out.println("00000000000000000");
			 String path = "/root/.che-multiuser/instance/data/workspaces/workspacepsk2y2afb3ecogbh/regex_jdbc";
			 String outputFile = ""+System.currentTimeMillis()+".txt";
			 String[] command = ("/usr/bin/mvn -Dtest=com.assignment.regex.TestPatternFinder test --log-file "+outputFile).split("\\s+");
			 System.out.println(command);
			 ProcessBuilder builder = new ProcessBuilder();
			 builder.command(command);
			 builder.directory(new File(path));
			 Process process = builder.start();
			 LogStreamReader lsr = new LogStreamReader(process.getInputStream());
			 System.out.println("regex problem ");
			 Thread thread = new Thread(lsr, "LogStreamReader");
			 thread.start();
			 try{
				 Thread.sleep(10000);
			 }
			 catch(InterruptedException e){
				 
			 }
			 String op_path = path+"/"+outputFile;
			 List<String> lines = FileUtils.readLines(new File(op_path));
			 Integer noOfTestCases = 0;
			 Integer noOfTestCasesFailed = 0;
			 for(String line : lines){
				 if(line.contains("Tests run:")){
					 
					 String split[] = line.split(",");
					 for(String unit : split){
						 String test[] = unit.split(":");
						 System.out.println(test[0]);
						 System.out.println(test[1]);
						 if(test[0].trim().equals("Tests run")){
							 noOfTestCases = Integer.parseInt(test[1].trim());
						 }
						 
						 if(test[0].trim().equals("Failures")){
							 noOfTestCasesFailed = Integer.parseInt(test[1].trim());
							 break;
						 }
					 }
					 
					break;
				 }
			 }
			 
			 String testcaseResults = "regex.txt";
			 String line1 = "noOfTestCases$$$"+noOfTestCases;
			 String line2 = "noOfTestCasesFailed$$$"+noOfTestCasesFailed;
			 String pb = questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText().replaceAll("\n", "<br/>").replace("\r", "");
			 String line3 = "problem$$$"+pb;
			 String line4 = "name$$$"+user.getFirstName()+" "+user.getLastName();
			 String line5 = "testName$$$"+questionMapperInstance.getTestName();
			 String line6 = "codeQualityLink$$$"+codeQualityUrl;
			 String line7 = "usageLink$$$"+questionMapperInstance.getUsageDocumentUrl();
			 List<String> writelines = new ArrayList<>();
			 writelines.add(line1);
			 writelines.add(line2);
			 writelines.add(line3);
			 writelines.add(line4);
			 writelines.add(line5);
			 writelines.add(line6);
			 writelines.add(line7);
			 
			 FileUtils.writeLines(new File(testcaseResults), writelines);
			 TestCasesMetric casesMetric = new TestCasesMetric();
			 casesMetric.setNoOfTestCases(noOfTestCases);
			 casesMetric.setTestCasesPassed(noOfTestCases - noOfTestCasesFailed);
			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
			 casesMetric.setCodeQualityLink(codeQualityUrl);
			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
			 return casesMetric;
		 }
		 else if(workspaceId.contains("zigca3iu5ynpydhp")){
			 System.out.println("1111111111111111111111111111");
			 String path = "/root/.che-multiuser/instance/data/workspaces/workspacezigca3iu5ynpydhp/JohnDoe-48-47-1568040746461";
			// String path = "/root/.che-multiuser/instance/data/workspaces/workspacek9jw1ghhtr724g7q/console-java-simple";
			 String outputFile = ""+System.currentTimeMillis()+".txt";
			 String[] command = ("/usr/bin/mvn -Dtest=com.problem1.TestProblem1 test --log-file "+outputFile).split("\\s+");
			 System.out.println("command is "+command.toString());
			 ProcessBuilder builder = new ProcessBuilder();
			 builder.command(command);
			 builder.directory(new File(path));
			 Process process = builder.start();
			 LogStreamReader lsr = new LogStreamReader(process.getInputStream());
			 System.out.println("collection problem ");
			 Thread thread = new Thread(lsr, "LogStreamReader");
			 thread.start();
			 try{
				 Thread.sleep(10000);
			 }
			 catch(InterruptedException e){
				 
			 }
			 System.out.println("test cases fired ");
			 String op_path = path+"/"+outputFile;
			 System.out.println("test cases result file"+op_path);
			 List<String> lines = FileUtils.readLines(new File(op_path));
			 System.out.println("no of lines in "+op_path+" is "+lines.size());
			 Integer noOfTestCases = 0;
			 Integer noOfTestCasesFailed = 0;
			 for(String line : lines){
				
				 if(line.contains("Tests run:")){
					 System.out.println("here !!!!!!!!!1 "+line);
					 String split[] = line.split(",");
					 for(String unit : split){
						 String test[] = unit.split(":");
						 System.out.println(test[0]);
						 System.out.println(test[1]);
						 if(test[0].trim().equals("Tests run")){
							 System.out.println("test cases run ");
							 noOfTestCases = Integer.parseInt(test[1].trim());
						 }
						 
						 if(test[0].trim().equals("Failures")){
							 System.out.println("test cases failed ");
							 noOfTestCasesFailed = Integer.parseInt(test[1].trim());
							 break;
						 }
					 }
					 
					break;
				 }
			 }
			 
			 String testcaseResults = "collections_logic.txt";
			 String line1 = "noOfTestCases$$$"+noOfTestCases;
			 String line2 = "noOfTestCasesFailed$$$"+noOfTestCasesFailed;
			 String pb = questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText().replaceAll("\n", "<br/>").replace("\r", "");
			 String line3 = "problem$$$"+pb;
			 String line4 = "name$$$"+user.getFirstName()+" "+user.getLastName();
			 String line5 = "testName$$$"+questionMapperInstance.getTestName();
			 String line6 = "codeQualityLink$$$"+codeQualityUrl;
			 String line7 = "usageLink$$$"+questionMapperInstance.getUsageDocumentUrl();
			 List<String> writelines = new ArrayList<>();
			 writelines.add(line1);
			 writelines.add(line2);
			 writelines.add(line3);
			 writelines.add(line4);
			 writelines.add(line5);
			 writelines.add(line6);
			 writelines.add(line7);
			 
			 FileUtils.writeLines(new File(testcaseResults), writelines);
			 TestCasesMetric casesMetric = new TestCasesMetric();
			 casesMetric.setNoOfTestCases(noOfTestCases);
			 casesMetric.setTestCasesPassed(noOfTestCases - noOfTestCasesFailed);
			 casesMetric.setProblemStatement(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
			 casesMetric.setCodeQualityLink(codeQualityUrl);
			 casesMetric.setProjDocLink(questionMapperInstance.getUsageDocumentUrl());
			 System.out.println("Automation cases run");
			 return casesMetric;
		 }
		 else{
			 return null;
		 }
	 }
	 
	 
	 @RequestMapping(value = "/uploadProjectDocs", method = RequestMethod.POST)
	 public @ResponseBody String doUpload(@RequestParam("addimage") MultipartFile addimage,HttpServletRequest request, HttpServletResponse response, @RequestParam String qMapperInstanceId) throws Exception {     
		 String docUrl = "";
		 ModelAndView mav = null;
			User user = (User) request.getSession().getAttribute("user");
			List<Question> questions = new ArrayList<Question>();
			if(addimage != null){
				String fileName = qMapperInstanceId+(user.getFirstName()+user.getLastName()+System.currentTimeMillis())+addimage.getOriginalFilename();
				 String destination = propertyConfig.getFileServerLocation()+File.separator+"docs"+File.separator+fileName;
				 File file = new File(destination);
				 	if( file.exists()){
				 		if(addimage.getOriginalFilename() != null && addimage.getOriginalFilename().trim().length() > 0){
				 			FileUtils.forceDelete(file);
				 		}
				 		
				 	}
				 	if(addimage.getOriginalFilename() != null && addimage.getOriginalFilename().trim().length() > 0){
				 		 docUrl = propertyConfig.getFileServerWebUrl()+"docs/"+fileName;
						
				 		 addimage.transferTo(file);
				 	}
				
				
			}
			
			QuestionMapperInstance questionMapperInstance = questionMapperInstanceRep.findById(Long.parseLong(qMapperInstanceId)).get();
			questionMapperInstance.setUsageDocumentUrl(docUrl);
			//questionMapperInstance.setWorkspaceSubmitted(true);
			questionMapperInstanceRep.save(questionMapperInstance);
	     return docUrl;
	 }
	 
//	 @RequestMapping(value = "/multifileresults", method = RequestMethod.GET)
//		public ModelAndView multifileresults(@RequestParam String workspace, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String url = URLDecoder.decode(workspace);
//		url = new String(Base64.getDecoder().decode(url.getBytes()));
//		  ModelAndView mav = new ModelAndView("runtimeResults");
//		  List<String> lines = null;
//		  if(workspace.equals("k9jw1ghhtr724g7q")){
//			  //collections
//			  
//			  lines = FileUtils.readLines(new File("collections_logic.txt"));
//		  }
//		  else if(workspace.equals("psk2y2afb3ecogbh")){
//			  //regex
//			  lines = FileUtils.readLines(new File("regex.txt"));
//		  }
//		  mav.addObject("testGiver", lines.get(3).substring(lines.get(3).indexOf("$$$")+3, (lines.get(3).length())));
//		  mav.addObject("problemStatement", lines.get(2).substring(lines.get(2).indexOf("$$$")+3, (lines.get(2).length())).replaceAll("\n", "<br/>"));
//		  mav.addObject("testName", lines.get(4).substring(lines.get(4).indexOf("$$$")+3, (lines.get(4).length())));
//		  mav.addObject("codeQuality", lines.get(5).substring(lines.get(5).indexOf("$$$")+3, (lines.get(5).length())));
//		  mav.addObject("usageLink", lines.get(6).substring(lines.get(6).indexOf("$$$")+3, (lines.get(6).length())));
//		  mav.addObject("noOfTestCases", lines.get(0).substring(lines.get(0).indexOf("$$$")+3, (lines.get(0).length())));
//		  mav.addObject("noOfTestCasesFailed", lines.get(1).substring(lines.get(1).indexOf("$$$")+3, (lines.get(1).length())));
//		  return mav;
//		}
	 
	 @RequestMapping(value = "/multiFileResults", method = RequestMethod.GET)
		public ModelAndView multiFileResults(@RequestParam String fullname, @RequestParam Long instanceId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		  QuestionMapperInstance instance = questionMapperInstanceRep.findById(instanceId).get();
		  ModelAndView mav = new ModelAndView("multiFileResults");
		  
		  mav.addObject("testGiver", URLDecoder.decode(fullname));
		  mav.addObject("problemStatement", instance.getQuestionMapper().getQuestion().getQuestionText());
		  mav.addObject("testName", instance.getTestName());
		  String workSpaceFolder = instance.getWorkspaceUrl().substring(instance.getWorkspaceUrl().indexOf("che/")+4, instance.getWorkspaceUrl().length());
		  String url = propertyConfig.getCodeQualityServerLink()+URLEncoder.encode(workSpaceFolder);
		  mav.addObject("codeQuality", url);
		  mav.addObject("usageLink", instance.getUsageDocumentUrl());
		  mav.addObject("noOfTestCases", instance.getNoOfTestCases() == null?0:instance.getNoOfTestCases());
		  mav.addObject("noOfTestCasesPassed", instance.getNoOfTestCasesPassed() == null?0:instance.getNoOfTestCasesPassed()  );
		  mav.addObject("noOfFunctionalTestCases", instance.getFunctionalTestCases() == null?0: instance.getFunctionalTestCases());
		  mav.addObject("noOfFunctionalTestCasesPassed", instance.getFunctionalTestCasesPassed()==null?0: instance.getFunctionalTestCasesPassed());
		  
		  mav.addObject("noOfBoundaryTestCases", instance.getBoundaryTestCases()==null?0:instance.getBoundaryTestCases() );
		  mav.addObject("noOfBoundaryTestCasesPassed", instance.getBoundaryTestCasesPassed()==null?0:instance.getBoundaryTestCasesPassed() );
		  
		  mav.addObject("noOfExceptionTestCases", instance.getExceptionTestCases()==null?0:instance.getExceptionTestCases() );
		  mav.addObject("noOfExceptionTestCasesPassed", instance.getExceptionTestCasesPassed()==null?0:instance.getExceptionTestCasesPassed() );
		  
		  
		  return mav;
		}
	 
 @RequestMapping(value = "/showAllResultsforMFA", method = RequestMethod.GET)
	public ModelAndView showAllResultsforMFA(@RequestParam(name= "page", required = false) Integer pageNumber, HttpServletRequest request, HttpServletResponse response) {
	 User user = (User) request.getSession().getAttribute("user");
	 
	 if(pageNumber == null) {
			pageNumber = 0;
		}
	 
	 
	 Page<QuestionMapperInstance> instances = questionMapperInstanceService.findAllFullStackQuestionMapperInstances(user.getCompanyId(), PageRequest.of(pageNumber, 20));
	 
	// List<QuestionMapperInstance> instances = questionMapperInstanceService.findAllFullStackQuestionMapperInstances(user.getCompanyId());
	  for(QuestionMapperInstance instance : instances){
		  User user2 = userService.findByPrimaryKey(instance.getUser(), user.getCompanyId());
		  instance.setUerFullName(user2.getFirstName()+" "+user2.getLastName());
		  instance.setLastDate(DateFormat.getDateTimeInstance().format(instance.getUpdateDate() == null?instance.getCreateDate():instance.getUpdateDate()));
	  }
	 
	 ModelAndView mav = new ModelAndView("fullStack_Results");
	  mav.addObject("instances", instances.getContent());
	  Map<String, String> params = new HashMap<>();
	  //params.put("qualifier1", qualifier1);
	  CommonUtil.setCommonAttributesOfPagination(instances, mav.getModelMap(), pageNumber, "showAllResultsforMFA", params);
	  return mav;

	 // return mav;
	}
 
 //multiFileResults
	
	
}

class LogStreamReader implements Runnable {
	Logger logger = LoggerFactory.getLogger(LogStreamReader.class);
    private BufferedReader reader;

    public LogStreamReader(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    public void run() {
        try {
            String line = reader.readLine();
            while (line != null) {
            	logger.info(line);
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("error writing stack", e);
        }
    }
}

