package com.assessment.web.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.Exceptions.AssessmentGenericException;
import com.assessment.common.CommonUtil;
import com.assessment.common.ExcelReader;
import com.assessment.common.PropertyConfig;
import com.assessment.common.util.EmailGenericMessageThread;
import com.assessment.data.Company;
import com.assessment.data.LMSAdminDtO;
import com.assessment.data.LMSUserModuleMapping;
import com.assessment.data.License;
import com.assessment.data.Module;
import com.assessment.data.ModuleItem;
import com.assessment.data.Skill;
import com.assessment.data.SkillLevel;
import com.assessment.data.Test;
import com.assessment.data.User;
import com.assessment.data.UserNonCompliance;
import com.assessment.data.UserTestSession;
import com.assessment.data.UserType;
import com.assessment.services.CompanyService;
import com.assessment.services.LicenseService;
import com.assessment.services.LmsUserModuleMappingService;
import com.assessment.services.ModuleService;
import com.assessment.services.SkillService;
import com.assessment.services.TenantService;
import com.assessment.services.TestService;
import com.assessment.services.UserNonComplianceService;
import com.assessment.services.UserService;
import com.assessment.services.UserTestSessionService;
import com.assessment.web.dto.ModuleDTO;

@Controller
public class UserController {
	@Autowired
	CompanyService companyService;

	@Autowired
	UserService userService;

	@Autowired
	PropertyConfig propertyConfig;

	@Autowired
	TestService testService;

	@Autowired
	UserNonComplianceService userNonComplianceService;

	@Autowired
	UserTestSessionService userTestSessionService;

	@Autowired
	DataSource dataSourceRoot;

	@Autowired
	TenantService tenantService;

	@Autowired
	SkillService skillService;

	@Autowired
	LicenseService licenseService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	LmsUserModuleMappingService mappingService;

	private static String LOCAL_BASE_URL = "http://localhost/";

	Logger logger = LoggerFactory.getLogger(UserController.class);

	// private static String REMOTE_BASE_URL="http://13.59.126.83/";

	@RequestMapping(value = "/uploadUsers", method = RequestMethod.POST)
	public void uploadUsers(HttpServletResponse response, MultipartHttpServletRequest request) throws Exception {
		try {
			// System.out.println("in uploadUsers entering");
			MultipartFile multipartFile = request.getFile("fileFromUserForm");
			Long size = multipartFile.getSize();
			String contentType = multipartFile.getContentType();
			InputStream stream = multipartFile.getInputStream();
			File file = new File("users.xml");
			List<User> users = ExcelReader.parseExcelFileToBeans(stream, file);
			logger.info("in upload method users size " + users.size());
			if (users.size() == 0) {
				throw new AssessmentGenericException("NO_DATA_IN_EXCEL");
			}
			String compId = users.get(0).getCompanyId();
			Company company = companyService.findByCompanyId(compId);
			// System.out.println("Company got in uploadUsers "+company.getId() +"
			// "+company.getCompanyName());
			logger.info("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			for (User u : users) {
				u.setCompanyId(company.getCompanyId());
				u.setCompanyName(company.getCompanyName());
				userService.saveOrUpdate(u);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problems in uploading users", e);
		}
	}

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam String tenantEmailId, @RequestParam String companyId,
			@RequestParam String companyName, @RequestParam String pwd,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
			ModelAndView mav = new ModelAndView("login_new");
			if (companyService.findByCompanyId(companyId) != null) {
				User user = new User();
				user.setCompanyId(companyId);
				user.setCompanyName(companyName);
				user.setInternalUser(true);
				user.setEmail("system.admin@" + companyId + ".com");
				user.setPassword("12345");
				return mav;
			}
			// System.out.println("pwd is ************* "+pwd);
			pwd = new URLDecoder().decode(new String(Base64.getDecoder().decode(pwd)));
			// System.out.println("pwd is *************888 "+pwd);
			Company company = new Company();
			company.setCompanyId(companyId);
			company.setCompanyName(companyName);
			companyService.saveOrUpdate(company);
			logger.info("Company crated");
			// System.out.println("Company crated");
			Skill skill = new Skill("Java", SkillLevel.BASIC);
			skill.setCompanyId(companyId);
			skill.setCompanyName(companyName);
			skillService.createSkill(skill);
			logger.info("Skill crated");
			// System.out.println("Skill crated");
			User user = new User();
			user.setCompanyId(companyId);
			user.setCompanyName(companyName);
			user.setInternalUser(true);
			if (tenantEmailId == null) {
				user.setEmail("system.admin@" + companyId + ".com");
			} else {
				user.setEmail(tenantEmailId);
			}
			user.setUserType(UserType.ADMIN);
			// user.setPassword("12345");
			user.setPassword(pwd);
			userService.addUser(user);

			mav.addObject("user", user);
			return mav;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("index");
			User user = new User();
			user.setCompanyId(companyId);
			user.setCompanyName(companyName);
			user.setInternalUser(true);
			user.setEmail("system.admin@" + companyId + ".com");
			user.setPassword("12345");
			logger.error("problem ", e);
			throw new AssessmentGenericException("problem in init", e);

		}
	}

	@RequestMapping(value = "/setUpTenant", method = RequestMethod.GET)
	public void setUpTenant(@RequestParam String tenantEmailId, @RequestParam String tenantId,
			@RequestParam String companyName, String adminPassword, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		List<String> list = bean.getInputArguments();
		boolean local = false;
		for (String str : list) {
			// System.out.println(str);
			if (str.contains("localDeploy=true")) {
				local = true;
				break;
			}
		}

		String exampleConfig = propertyConfig.getDefaultReferenceConfigFileLocation() + File.separator
				+ "config.properties";
		String tenatsFolder = propertyConfig.getTenantsConfigLocation();

		File file = new File(exampleConfig);

		/**
		 * Step0 - Create a schema for new tenant in database
		 */
		String password = "Welcome@123";
		dataSourceRoot.getConnection().createStatement().execute("create schema " + tenantId);
		dataSourceRoot.getConnection().createStatement().execute(
				"create user 'User_" + tenantId + "'@'%' IDENTIFIED BY '" + password + "'");
		dataSourceRoot.getConnection().createStatement().execute("grant all privileges on " + tenantId
				+ ".* to 'User_" + tenantId + "'@'%'");

		/**
		 * Step 1 update config.properties with right values
		 */
		String op = propertyConfig.getTenantsConfigLocation() + File.separator + tenantId;
		File output = new File(op);
		FileUtils.copyDirectory(new File(propertyConfig.getDefaultReferenceConfigFileLocation()),
				new File(op));
		FileUtils.forceMkdir(output);

		// FileInputStream in = new FileInputStream(exampleConfig);
		String configData = FileUtils.readFileToString(new File(exampleConfig));
		if (local) {
			configData = configData.replace("${BASE_URL}", LOCAL_BASE_URL);
		} else {
			configData = configData.replace("${BASE_URL}", propertyConfig.getRemoteBaseUrl());
		}
		configData = configData.replace("${TENANT}", tenantId);
		configData = configData.replace("${TENANT_SCREENSHOT_LOCATION}",
				op + File.separator + "ScreenShots");
		/**
		 * Step 2 - Put the updated config.properties file in the right tenant folder
		 */

		FileUtils.write(new File(op + File.separator + "config.properties"), configData, false);

		/**
		 * Step 3 - Copy the base war file folder (assessment) into Tomcat's webapps
		 * folder
		 */
		FileUtils.copyDirectory(
				new File(propertyConfig.getDefaultReferenceConfigFileLocation()
						+ File.separator + "assessment"),
				new File(propertyConfig.getTomcatDeployLocation() + File.separator
						+ tenantId));

		/**
		 * Step 4 - Change the appContext file of copied war folder to correspond to
		 * tenant specific values
		 */
		String appContextFileLoc = propertyConfig.getTomcatDeployLocation() + File.separator + tenantId
				+ File.separator + "WEB-INF" + File.separator + "classes" + File.separator
				+ "appContext.xml";
		String contents = FileUtils.readFileToString(new File(appContextFileLoc));
		contents = contents.replace("${CONFIG_LOCATION}", op + File.separator + "config.properties");
		String jdbcUrl = "";
		if (local) {
			jdbcUrl = "jdbc:mysql://localhost:3306/" + tenantId;
		} else {
			jdbcUrl = "jdbc:mysql://127.0.0.1:3306/" + tenantId;
		}
		String user = "User_" + tenantId;
		// String password = "password";
		contents = contents.replace("${TEST_LINK_LOCATION}", op + File.separator + "sendTestLink.html");
		contents = contents.replace("${RESULT_LINK_LOCATION}",
				op + File.separator + "sendTestResultInfo.html");
		contents = contents.replace("${JDBC_URL}", jdbcUrl);
		contents = contents.replace("${USER}", user);
		contents = contents.replace("${PASSWORD}", password);
		FileUtils.write(new File(appContextFileLoc), contents, false);
		String logFile = propertyConfig.getTomcatDeployLocation() + File.separator + tenantId
				+ File.separator + "WEB-INF" + File.separator + "classes" + File.separator
				+ "log4j.properties";
		String logContents = FileUtils.readFileToString(new File(logFile));
		logContents = logContents.replace("${LOG_FILE}", tenantId);
		FileUtils.write(new File(logFile), logContents, false);
		String message = "";
		String pwd = URLEncoder.encode(Base64.getEncoder().encodeToString(adminPassword.getBytes()));
		if (local) {
			message = "Hello \n\n Please go to https://localhost:8443/" + tenantId
					+ "/init?companyId=" + tenantId + "&companyName=" + companyName
					+ "&tenantEmailId=" + tenantEmailId + "&pwd=" + pwd + "\n"
					+ "After clicking on above link you can access your instance of Assessment platform by using following - \n<br>"
					+ "Web Link - https://localhost:8443/" + tenantId + "\n<br>"
					+ "User - " + tenantEmailId + "\n<br>" + "Password - "
					+ adminPassword + "\n<br>" + "Company - " + companyName
					+ "\n\n\n<br><br><br>" + "Thanks and Regards\n<br>"
					+ "System Admin - Yaksha\n<br>" + "IIHT";
		} else {

			message = "Hello \n\n Please go to " + (propertyConfig.getRemoteBaseUrl() + tenantId)
					+ "/init?companyId=" + tenantId + "&companyName=" + companyName
					+ "&tenantEmailId=" + tenantEmailId + "&pwd=" + pwd + "\n"
					+ "After clicking on above link you can access your instance of Assessment platform by using following - \n<br>"
					+ "Web Link - " + propertyConfig.getRemoteBaseUrl() + tenantId
					+ "\n<br>" + "User - " + tenantEmailId + "\n<br>" + "Password - "
					+ adminPassword + "\n<br>" + "Company - " + companyName
					+ "\n\n\n<br><br><br>" + "Thanks and Regards\n<br>"
					+ "System Admin - Yaksha\n<br>" + "IIHT";
		}

		EmailGenericMessageThread runnable = new EmailGenericMessageThread(tenantEmailId,
				"Asessment Platform Setup Details", message, propertyConfig);
		Thread th = new Thread(runnable);
		th.start();
	}

	public void removeTenant(String tenantEmailId, String tenantId, String companyName,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		/**
		 * Step 1 - Delete folder in tomcat_bin/assessment location
		 */
		String op = propertyConfig.getTenantsConfigLocation() + File.separator + tenantId;
		File output = new File(op);
		if (output.exists()) {
			FileUtils.deleteDirectory(output);
		}

		/**
		 * Step 2- Remote web apps folder
		 */
		File webapps = new File(propertyConfig.getTomcatDeployLocation() + File.separator + tenantId);
		if (webapps.exists()) {
			FileUtils.deleteDirectory(webapps);
		}

		/**
		 * Step 3 - Delete schema and database user
		 */
		try {
			dataSourceRoot.getConnection().createStatement()
					.execute("drop schema IF EXISTS " + tenantId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("can't delete schema " + tenantId, e);
			e.printStackTrace();
		}
		try {
			dataSourceRoot.getConnection().createStatement()
					.execute("drop user 'User_" + tenantId + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("can't delete user User_" + tenantId, e);
			e.printStackTrace();
		}

		String message = "Hello,\n\nYour application [Assessment Engine Platform) account has been deleted. \n\nThanks and Regards\nSuper Admin - Yaksha ";
		EmailGenericMessageThread runnable = new EmailGenericMessageThread(tenantEmailId,
				"Asessment Platform Account Deletion", message, propertyConfig);
		Thread th = new Thread(runnable);
		th.start();
	}

	@RequestMapping(value = "/goscreen", method = RequestMethod.GET)
	public ModelAndView goscreen(HttpServletResponse response, HttpServletRequest request) throws Exception {
		return new ModelAndView("screen_capture_demo");
	}

	@RequestMapping(value = "/uploadScreenSnapShot", method = RequestMethod.POST)
	public void uploadScreenSnapShot(@RequestParam String testName, @RequestBody String imageValue,
			HttpServletRequest request) throws Exception {
//		User user = (User) request.getSession().getAttribute("user");
//		String folder = propertyConfig.getScreenShotFolder();
//		//System.out.println("folder is "+folder);
//	//	System.out.println("testName is "+testName);
//		com.assessment.data.Test test = testService.findbyTest(testName, user.getCompanyId());
//		//System.out.println("user is "+user);
//		//System.out.println("comp id is "+user.getCompanyId());
//		//System.out.println("test is "+test.getTestName());
//		//System.out.println("comp is "+user.getCompanyName());
//		folder = folder + File.separator + user.getCompanyName()+File.separator+user.getFirstName()+"-"+user.getLastName()+File.separator+test.getTestName()+"-"+test.getId();
//		FileUtils.forceMkdir(new File(folder));
//		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//		Date date = new Date();
//		String fileName = dateFormat.format(date)+".png";
//		fileName = fileName.replace(":", "_");
//		File dest = new File(folder+File.separator+fileName);
//		imageValue = URLDecoder.decode(imageValue);
//		imageValue = imageValue.substring(("testName="+testName+"&data=data:image/png;base64,").length(), imageValue.length());
//		byte bat[] = Base64.getDecoder().decode(imageValue);
//	        FileUtils.writeByteArrayToFile(dest, bat);
	}

	@RequestMapping(value = "/getNonComplianceCount", method = RequestMethod.POST)
	public @ResponseBody Integer getNonComplianceCount(@RequestBody String userNonCompliance,
			HttpServletRequest request) throws Exception {
		userNonCompliance = userNonCompliance.substring("data=".length(), userNonCompliance.length());
		User user = (User) request.getSession().getAttribute("user");
		// userNonCompliance = userNonCompliance.replaceAll("$$$", "\n");
		userNonCompliance = URLDecoder.decode(userNonCompliance);
		Properties properties = new Properties();
		properties.load(new StringReader(userNonCompliance));
		String email = properties.getProperty("user");
		String testName = properties.getProperty("testName");
		String companyId = properties.getProperty("companyId");
		UserNonCompliance nonCompliance = userNonComplianceService.findNonCompliance(email, testName,
				companyId);
		if (nonCompliance == null) {
			return 0;
		} else {
			return nonCompliance.getNoOfNonCompliances();
		}

	}

	@RequestMapping(value = "/registerNonCompliance", method = RequestMethod.POST)
	public void registerNonCompliance(@RequestBody String userNonCompliance, HttpServletRequest request)
			throws Exception {
		userNonCompliance = userNonCompliance.substring("data=".length(), userNonCompliance.length());
		User user = (User) request.getSession().getAttribute("user");
		// userNonCompliance = userNonCompliance.replaceAll("$$$", "\n");
		userNonCompliance = URLDecoder.decode(userNonCompliance);
		Properties properties = new Properties();
		properties.load(new StringReader(userNonCompliance));
		String email = properties.getProperty("user");
		String testName = properties.getProperty("testName");
		String companyId = properties.getProperty("companyId");
		UserTestSession session = userTestSessionService.findUserTestSession(email, testName, companyId);
		userNonComplianceService.increment(email, testName, companyId, null);
//		    	if(session == null) {
//		    		userNonComplianceService.increment(email, testName, companyId, null);
//		    	}
//		    	else {
//		    		userNonComplianceService.increment(email, testName, companyId, session.getId());
//		    	}

	}

	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
	public ModelAndView listUsers(HttpServletResponse response, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<User> users = userService.findByCompany(user.getCompanyId());
		ModelAndView mav = new ModelAndView("add_user2");
		mav.addObject("users", users);
		User usr = new User();
		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		mav.addObject("usr", usr);
		return mav;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<User> users = userService.findByCompany(user.getCompanyId());
		ModelAndView mav = new ModelAndView("add_user3");
		mav.addObject("users", users);
		User usr = new User();
		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		mav.addObject("usr", usr);
		return mav;
	}

	@RequestMapping(value = "/searchUsrs", method = RequestMethod.GET)
	public ModelAndView searchUsers(@RequestParam String searchText, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<User> users = userService.searchUsers(user.getCompanyId(), searchText);
		ModelAndView mav = new ModelAndView("add_user2");
		mav.addObject("users", users);
		User usr = new User();
		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		mav.addObject("usr", usr);
		return mav;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("usr") User usr) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView("add_user2");
		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		userService.saveOrUpdate(usr);
		usr = new User();
		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		mav.addObject("usr", usr);
		List<User> users = userService.findByCompany(user.getCompanyId());
		mav.addObject("users", users);
		return mav;
	}

	@RequestMapping(value = "/lmsAdmins", method = RequestMethod.GET)
	public ModelAndView lmsAdmins(@RequestParam(name = "page", required = false) Integer pageNumber,
			HttpServletResponse response, HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}

		Page<User> users = userService.findUsersByTypeAndCompany(user.getCompanyId(),
				UserType.LMS_ADMIN.getType(), PageRequest.of(pageNumber, 15));
		ModelAndView mav = new ModelAndView("lmsAdmins");
		mav.addObject("users", users.getContent());
		CommonUtil.setCommonAttributesOfPagination(users, modelMap, pageNumber, "lmsAdmins", null);
		return mav;
	}

	@RequestMapping(value = "/addlmsadmin", method = RequestMethod.GET)
	public ModelAndView addlmsadmin(@RequestParam(name = "userId", required = false) Long userId,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		List<String> licenses = licenseService.getLicensesInString(user.getCompanyId());
		ModelAndView mav = new ModelAndView("addlmsadmin");
		User usr = null;
		if (userId == null) {
			usr = new User();
		} else {
			usr = userService.findById(userId);
		}

		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		mav.addObject("usr", usr);
		mav.addObject("licenses", licenses);
		return mav;
	}

	@RequestMapping(value = "/savelmsadmin", method = RequestMethod.POST)
	public ModelAndView savelmsadmin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("usr") User usr, ModelMap modelMap) {
		User user = (User) request.getSession().getAttribute("user");
		usr.setCompanyId(user.getCompanyId());
		usr.setCompanyName(user.getCompanyName());
		usr.setUserType(UserType.LMS_ADMIN);
		userService.saveOrUpdate(usr);
		Page<User> users = userService.findUsersByTypeAndCompany(user.getCompanyId(),
				UserType.LMS_ADMIN.getType(), PageRequest.of(0, 15));
		ModelAndView mav = new ModelAndView("lmsAdmins");
		mav.addObject("users", users.getContent());
		mav.addObject("message", "Lms Admin " + usr.getEmail() + " Saved");// later put it as label
		mav.addObject("msgtype", "Information");
		CommonUtil.setCommonAttributesOfPagination(users, modelMap, 0, "lmsAdmins", null);
		return mav;
	}

	@RequestMapping(value = "/uploadLMSUsers", method = RequestMethod.POST)
	public @ResponseBody String uploadLMSUsers(HttpServletResponse response, MultipartHttpServletRequest request)
			throws Exception {
		try {
			// System.out.println("in uploadUsers entering");
			User user = (User) request.getSession().getAttribute("user");
			MultipartFile multipartFile = request.getFile("idusers");
			Long size = multipartFile.getSize();
			String contentType = multipartFile.getContentType();
			InputStream stream = multipartFile.getInputStream();
//			    File file = new File("users.xml");
			File file = ResourceUtils.getFile("classpath:users.xml");
			List<User> users = ExcelReader.parseExcelFileToBeans(stream, file);
			logger.info("in upload method users size " + users.size());
			if (users.size() == 0) {
				return "No Data Found";
			}
			String compId = user.getCompanyId();
			Company company = companyService.findByCompanyId(compId);
			// System.out.println("Company got in uploadUsers "+company.getId() +"
			// "+company.getCompanyName());
			logger.info("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			for (User u : users) {
				u.setCompanyId(company.getCompanyId());
				u.setCompanyName(company.getCompanyName());
				u.setPassword(u.getEmail().hashCode() + "");
				u.setUserType(UserType.LMS_STUDENT);
				User u1 = userService.findByPrimaryKey(u.getEmail(),
						company.getCompanyId());
				u.setCompanyId(user.getCompanyId());
				u.setCompanyName(user.getCompanyName());
				userService.saveOrUpdate(u);
				if (u1 == null) { // first time sent email
					String html = FileUtils.readFileToString(new File(propertyConfig
							.getSendCredentialsToStudent()));
					html = html.replace("{FULL_NAME}",
							u.getFirstName() + " " + u.getLastName());
					html = html.replace("{BASE_URL}", propertyConfig.getBaseUrl());
					html = html.replace("{USER}", u.getEmail());
					html = html.replace("{PASSWORD}", u.getPassword());
					EmailGenericMessageThread client = new EmailGenericMessageThread(
							u.getEmail(),
							"Registration Credentials - "
									+ propertyConfig.getBaseUrl(),
							html, propertyConfig);
					Thread thread = new Thread(client);
					thread.start();

				}
			}
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problems in uploading users", e);
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/getHTMLTableForClassfier", method = RequestMethod.GET)
	public @ResponseBody String getHTMLTableForClassfier(@RequestParam("classifier") String classifier,
			HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String table = "<table id=\"classTable\" class=\"table table-bordered\">          <thead>		  <tr>		  <th scope=\"col\">#</th>		  <th scope=\"col\">First Name</th>		  <th scope=\"col\">Last name</th>		  <th scope=\"col\">Institute</th>		  <th scope=\"col\">Grade</th>		  <th scope=\"col\">Classifier</th>		  		</tr>          </thead>          <tbody>            ${content}		          </tbody>        </table>";
		String rows = "<tr class=\"${type}\">              <td>${ser}</td>              <td>${firstName}</td>              <td>${lastName}</td>              <td>${institute}</td>              <td>${grade}</td>              <td>${classifier}</td>                                        </tr>";
		String clss[] = classifier.split("-");
		String institute = clss[0].trim();
		String grade = clss[1].trim();
		String cs = clss[2].trim();
		String companyId = user.getCompanyId();
		List<User> users = userService.findByInstituteGradeClassifier(companyId, institute, grade, cs);
		String tot = "";
		int count = 1;
		for (User usr : users) {
			String row = rows;
			if (count % 2 == 0) {
				row = row.replace("${type}", "success");
			} else {
				row = row.replace("${type}", "info");
			}

			row = row.replace("${ser}", count + "");
			count++;
			row = row.replace("${firstName}", usr.getFirstName());
			row = row.replace("${lastName}", usr.getLastName());

			row = row.replace("${institute}", usr.getCollegeName());
			row = row.replace("${grade}", usr.getGrade());
			row = row.replace("${classifier}", usr.getClassifier());
			tot += row;
		}

		table = table.replace("${content}", tot);
		return table;
	}

	@RequestMapping(value = "/shareModule", method = RequestMethod.GET)
	public @ResponseBody String shareModule(@RequestParam(required = false, name = "meetingid") String meetingid,
			@RequestParam("moduleName") String moduleName,
			@RequestParam("classifier") String classifier, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		moduleName = URLDecoder.decode(moduleName);
		if (meetingid != null) {
			meetingid = URLDecoder.decode(meetingid);
		}
		classifier = URLDecoder.decode(classifier);
		User user = (User) request.getSession().getAttribute("user");

		String clss[] = classifier.split("-");
		String institute = clss[0].trim();
		String grade = clss[1].trim();
		String cs = clss[2].trim();
		String companyId = user.getCompanyId();
		List<String> users = userService.findEmailByInstituteGradeClassifier(companyId, institute, grade,
				cs);
		// Module module = moduleRepository.findById(moduleId).get();
		Module module = moduleService.findUniqueModule(moduleName, user.getCompanyId());
		String html = FileUtils.readFileToString(new File(propertyConfig.getShareModuleoStudent()));
		html = html.replace("{BASE_URL}", propertyConfig.getBaseUrl());
		html = html.replace("{CONTEXT}", classifier);
		html = html.replace("{TRAINER}", user.getFirstName() + " " + user.getLastName());
		html = html.replace("{MODULE_NAME}", module.getModuleName());
		if (meetingid != null && meetingid.trim().length() > 0) {

			html = html.replace("{MEETING_ID}", meetingid);
		} else {
			html = html.replace("{MEETING_ID}", "NA");
		}

		for (String email : users) {
			LMSUserModuleMapping lmsUserModuleMapping = mappingService.getByPrimaryKey(email,
					module.getId(), user.getCompanyId());
			// if(lmsUserModuleMapping == null){
			lmsUserModuleMapping = new LMSUserModuleMapping();
			lmsUserModuleMapping.setModuleId(module.getId());
			lmsUserModuleMapping.setModuleName(module.getModuleName());
			lmsUserModuleMapping.setUser(email);
			lmsUserModuleMapping.setCompanyId(user.getCompanyId());
			lmsUserModuleMapping.setCompanyName(user.getCompanyName());
			lmsUserModuleMapping.setCreatedBy(user.getEmail());
			mappingService.saveOrUpdate(lmsUserModuleMapping);
			// }
		}

		EmailGenericMessageThread client = new EmailGenericMessageThread(user.getEmail(),
				"Sharing Training Module " + module.getModuleName(), html, propertyConfig);
		String emails[] = new String[users.size()];
			
		emails = users.toArray(emails);
		for(int i=0;i<emails.length;i++){
			if(emails[i].contains("[")){
				emails[i] = emails[i].substring(0, emails[i].indexOf("["));
				System.out.println("email " +emails[i]);
			}
		}
		client.setCcArray(emails);
		Thread thread = new Thread(client);
		thread.start();
		return "ok";
	}

	@RequestMapping(value = "/fetchModuleData", method = RequestMethod.GET)
	@ResponseBody
	public String showModuleData(@RequestParam(name = "mname", required = true) String mname,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Module module = moduleService.findUniqueModule(mname, user.getCompanyId());
		String container = "<div><div><label id=\"modulenameid\" style=\"width:100%\">{MODULE_NAME}</label>	<h6>Description - {MODULE_DESC}</h6>							<div class=\"progress\" style=\"float: left;width: 100%;\">									<div id=\"moduleprogressid\" class=\"progress-bar\" style=\"width:{TEST_PERCENT}%\"></div>									<span id=\"moduleprogressspanid\">{TEST_PERCENT}%</span>								</div> 																							</div>						</div>                         <div ><table class='table borderless'>{ROWS}	</table></div>";
		String rows = "<tr class='borderless'><td class='borderless' style=\"color:blue;\">  {FIRST_COLUMN}  </td>									<td class='borderless' style=\"color:#ffcc66;\">{TEST_NAME}</td>									<td class='borderless'style=\"color:#cc3399;\" ><a href=\"javascript:startTest('{TEST_URL}')\">Click Start Test</a></td></tr>";
		String imageAvailable = "<a href=\"javascript:changeVideo('{VIDEO_URL}')\">Click to Open Video</a>";
		String imageNotAvailable = "Video Not Available";
		container = container.replace("{MODULE_NAME}", module.getModuleName());
		container = container.replace("{MODULE_DESC}", module.getModuleDescription() == null ? "NA"
				: module.getModuleDescription());
		Integer totalTests = 0;
		Integer testsAppeared = 0;
		Map<String, String> map = new HashMap<String, String>();
		for (ModuleItem item : module.getItems()) {
			totalTests++;
			String testName = item.getTestName();
			UserTestSession session = userTestSessionService.findUserTestSession(user.getEmail(),
					testName, user.getCompanyId());
			if (session != null) {
				map.put(session.getTestName(), "&#10004;");
				testsAppeared++;
			}
		}

		Integer percent = 0;
		if (totalTests > 0) {
			percent = (int) (100.0f * testsAppeared / totalTests);
		}
		container = container.replace("{TEST_PERCENT}", "" + percent);

		String allrows = "";
		String temp = "";
		String imgtemp = "";
		for (ModuleItem item : module.getItems()) {
			temp = rows;
			if (item.getExternalVideoUrl() != null && (item.getExternalVideoUrl().startsWith(
					"http") || item.getExternalVideoUrl().startsWith("www"))) {
				imgtemp = imageAvailable;
				imgtemp = imgtemp.replace("{VIDEO_URL}", item.getExternalVideoUrl());
			} else {
				imgtemp = imageNotAvailable;
			}
			temp = temp.replace("{FIRST_COLUMN}", imgtemp);
			String testName = item.getTestName();
			Test test = testService.findbyTest(testName, user.getCompanyId());
			String check = "";
			if (map.get(testName) != null) {
				check = map.get(testName);
			} else {
				check = "&cross;";
			}

			temp = temp.replace("{TEST_NAME}",
					(test.getTestName().replaceAll("^['\"]*", "")
							.replaceAll("['\"]*$", "")) + "  - "
							+ check);

			String testurl = getUrlForUser(user.getEmail(), test.getId(), user.getCompanyId());
			Date d1 = new Date();
			String sDate = Base64.getEncoder().encodeToString(("" + d1.getTime()).getBytes());
			Calendar c = Calendar.getInstance();
			c.setTime(d1);
			c.add(Calendar.YEAR, 2);
			Date d2 = c.getTime();
			String eDate = Base64.getEncoder().encodeToString(("" + d2.getTime()).getBytes());
			sDate = URLEncoder.encode(sDate);
			eDate = URLEncoder.encode(eDate);
			testurl += "&startDate=" + sDate + "&endDate=" + eDate;
			temp = temp.replace("{TEST_URL}", testurl);
			allrows += temp;
		}
		container = container.replace("{ROWS}", allrows);
		return container;
	}

	@RequestMapping(value = "/fetchModuleDataForPreviewForAdmin", method = RequestMethod.GET)
	@ResponseBody
	public String showModuleDataForPreviewForAdmin(@RequestParam(name = "mname", required = true) String mname,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Module module = moduleService.findUniqueModule(mname, user.getCompanyId());
		String container = "<div><div><label id=\"modulenameid\" style=\"width:100%\"><b>Preview {MODULE_NAME}</b></label>	<h6>Description - {MODULE_DESC}</h6>																													</div> 																							</div>						</div>                         <div ><table class='table borderless'>{ROWS}	</table></div>";
		String rows = "<tr class='borderless'><td class='borderless' style=\"color:blue;\">  {FIRST_COLUMN}  </td>									<td class='borderless' style=\"color:#ffcc66;\">{TEST_NAME}</td>									<td class='borderless'style=\"color:#cc3399;\" ><a href=\"javascript:startTest('{TEST_URL}')\">Click Start Test</a></td></tr>";
		String imageAvailable = "<a href=\"javascript:changeVideo('{VIDEO_URL}')\">Click to Open Video</a>";
		String imageNotAvailable = "Video Not Available";
		container = container.replace("{MODULE_NAME}", module.getModuleName());
		container = container.replace("{MODULE_DESC}", module.getModuleDescription() == null ? "NA"
				: module.getModuleDescription());

		String allrows = "";
		String temp = "";
		String imgtemp = "";
		for (ModuleItem item : module.getItems()) {
			temp = rows;
			if (item.getExternalVideoUrl() != null && (item.getExternalVideoUrl().startsWith(
					"http") || item.getExternalVideoUrl().startsWith("www"))) {
				imgtemp = imageAvailable;
				imgtemp = imgtemp.replace("{VIDEO_URL}", item.getExternalVideoUrl());
			} else {
				imgtemp = imageNotAvailable;
			}
			temp = temp.replace("{FIRST_COLUMN}", imgtemp);
			String testName = item.getTestName();
			Test test = testService.findbyTest(testName, user.getCompanyId());
			String check = "&cross;";

			temp = temp.replace("{TEST_NAME}",
					(test.getTestName().replaceAll("^['\"]*", "")
							.replaceAll("['\"]*$", "")) + "  - "
							+ check);

			String testurl = getUrlForUser(user.getEmail(), test.getId(), user.getCompanyId());
			Date d1 = new Date();
			String sDate = Base64.getEncoder().encodeToString(("" + d1.getTime()).getBytes());
			Calendar c = Calendar.getInstance();
			c.setTime(d1);
			c.add(Calendar.YEAR, 2);
			Date d2 = c.getTime();
			String eDate = Base64.getEncoder().encodeToString(("" + d2.getTime()).getBytes());
			sDate = URLEncoder.encode(sDate);
			eDate = URLEncoder.encode(eDate);
			testurl += "&startDate=" + sDate + "&endDate=" + eDate;
			temp = temp.replace("{TEST_URL}", testurl);
			allrows += temp;
		}
		container = container.replace("{ROWS}", allrows);
		return container;
	}

	private String getUrlForUser(String user, Long testId, String companyId) {
		String userBytes = Base64.getEncoder().encodeToString(user.getBytes());

		String after = "userId=" + URLEncoder.encode(userBytes) + "&testId="
				+ URLEncoder.encode(testId.toString()) + "&companyId="
				+ URLEncoder.encode(companyId);
		String url = propertyConfig.getBaseUrl() + "startTestSession?" + after;
		return url;
	}

	@RequestMapping(value = "/showUserTests", method = RequestMethod.GET)
	public ModelAndView showUserTests(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		User user = (User) request.getSession().getAttribute("user");
		List<UserTestSession> sessions = userTestSessionService.findTestListForUser(user.getCompanyId(),
				user.getEmail());
		Stack<String> stk = new Stack<>();
		stk.add("success");
		stk.add("danger");
		stk.add("info");
		stk.add("warning");
		stk.add("active");
		for (UserTestSession session : sessions) {
			if (stk.size() == 0) {
				stk.add("success");
				stk.add("danger");
				stk.add("info");
				stk.add("warning");
				stk.add("active");
			}
			String disp = stk.pop();
			session.setStyle(disp);
		}

		ModelAndView mav = new ModelAndView("user_profile_student_sessions");
		mav.addObject("sessions", sessions);
		mav.addObject("email", user.getEmail());
		return mav;

	}

	@RequestMapping(value = "/showLearnerUserDashboard", method = RequestMethod.GET)
	public ModelAndView showLearenrUserDashboard(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		User user = (User) request.getSession().getAttribute("user");
		List<LMSUserModuleMapping> mappings = mappingService.getAllModulesForUser(user.getCompanyId(),
				user.getEmail());
		List<ModuleDTO> modules = new ArrayList<ModuleDTO>();
		for (LMSUserModuleMapping mapping : mappings) {
			Module module = moduleService.findUniqueModule(mapping.getModuleName(),
					user.getCompanyId());
			ModuleDTO moduleDTO = new ModuleDTO();
			moduleDTO.setModule(module);
			moduleDTO.setSharedByEmail(
					mapping.getCreatedBy() == null ? "NA" : mapping.getCreatedBy());
			User trainer = null;
			if (mapping.getCreatedBy() != null) {
				trainer = userService.findByPrimaryKey(mapping.getCreatedBy(),
						user.getCompanyId());
				moduleDTO.setSharedByFullName(
						trainer.getFirstName() + " " + trainer.getLastName());
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
			Date share = mapping.getUpdateDate() == null ? mapping.getCreateDate()
					: mapping.getUpdateDate();
			String dt = dateFormat.format(share);
			moduleDTO.setSharedDate(dt);
			moduleDTO.setLearnerEmail(user.getEmail());
			moduleDTO.setLearnerFullName(user.getFirstName() + " " + user.getLastName());
			modules.add(moduleDTO);
			for (ModuleItem item : module.getItems()) {
				String testName = item.getTestName();
				Test test = testService.findbyTest(testName, user.getCompanyId());
				String userTestUrl = getUrlForUser(user.getEmail(), test.getId(),
						user.getCompanyId());
				item.setUserSpecificLink(userTestUrl);
			}
		}
		ModelAndView mav = new ModelAndView("user_profile_student_index");
		mav.addObject("moduledtos", modules);
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("companyId", user.getCompanyId());

		return mav;

	}

	@RequestMapping(value = "/showLearnerAdminDashboard", method = RequestMethod.GET)
	public ModelAndView showLearnerAdminDashboard(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView("user_profile_index");
//	  		request.getSession().setAttribute("user", user);
//	  		request.getSession().setAttribute("companyId", user.getCompanyId());
		List<String> classifiers = userService.findInstituteGradeClassifier(user.getCompanyId(),
				user.getCollegeName());
		mav.addObject("classifiers", classifiers);
		mav.addObject("user", user);
		LMSAdminDtO adminDtO = new LMSAdminDtO();
		adminDtO.setEmail(user.getEmail());
		adminDtO.setClassifier(user.getClassifier());
		adminDtO.setGrade(user.getGrade());
		adminDtO.setCollegeName(user.getCollegeName());
		mav.addObject("adminDto", adminDtO);
		String licenses = user.getLicenses();
		List<License> lics = new ArrayList<>();
		if (licenses != null && licenses.trim().length() > 0) {
			String lic[] = licenses.split(",");
			for (String l : lic) {
				l = l.trim();
				License license = licenseService.findByPrimaryKey(l, user.getCompanyId());
				lics.add(license);
			}
		}
		mav.addObject("licenses", lics);
		List<Module> freeModules = moduleService.findFreeModules(user.getCompanyId());
		mav.addObject("freeModules", freeModules);
		return mav;

	}

	@RequestMapping(value = "/fetchModulesForAdminUserByLicense", method = RequestMethod.GET)
	@ResponseBody
	public String fetchModulesForAdminUserByLicense(
			@RequestParam(name = "licname", required = true) String licname,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		User user = (User) request.getSession().getAttribute("user");
		String all = "<div class=\"colorlib-classes colorlib-light-grey\"  >		\n\t		<div class=\"container\">				\n\t\t					<div class=\"row\">													{MODULES}																							</div>	\n\t\t			</div>	\n\t		</div>";
		String mod = "<div class=\"col-md-3 animate-box\">		\n\t	<div class=\"classes\">			\n\t\t	<div class=\"classes-img\" style=\"background-image: url(./resources/userprofile/images/elearning.jpg);\">			\n\t\t\t		<span class=\"price text-center\"><small>{LICENSE}</small></span>	\n\t\t			</div>		\n\t\t		<div class=\"desc\">			\n\t\t\t		<h3><a href=\"javascript:showModule('{MOD_NAME}')\">{MOD_NAME} (Click to Share)</a></h3>			\n\t\t\t		<p>{MOD_DESC}</p>	\n\t\t\t				<p><a href=\"javascript:showItems('MOD_NAME_ENC')\" class=\"btn-learn\">Click to Preview Items </a>\n\t\t\t</p>			\n\t\t	</div>		\n\t	</div>	\n	</div>";
		List<Module> modules = moduleService.findModulesByLicense(licname, user.getCompanyId());
		all = all.replace("{LICENSE}", licname);
		String tempModAll = "";
		String tempMod = "";
		for (Module module : modules) {
			tempMod = mod;
			tempMod = tempMod.replace("{LICENSE}", licname);
			tempMod = tempMod.replace("{MOD_NAME}", module.getModuleName());
			tempMod = tempMod.replace("MOD_NAME_ENC", URLEncoder.encode(module.getModuleName()));
			tempMod = tempMod.replace("{MOD_DESC}", module.getModuleDescription() == null ? "NA"
					: module.getModuleDescription());
			tempModAll += tempMod;
		}
		all = all.replace("{MODULES}", tempModAll);
		return all;
	}
	
	
	@RequestMapping(value = "/gotoprofile", method = RequestMethod.GET)
	public ModelAndView gotoprofile(HttpServletResponse response, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("profile_static");
		return mav;
	}

}
