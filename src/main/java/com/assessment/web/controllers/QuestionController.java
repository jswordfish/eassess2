package com.assessment.web.controllers;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.exolab.castor.xml.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.assessment.data.Company;
import com.assessment.data.DifficultyLevel;
import com.assessment.data.FullStackOptions;
import com.assessment.data.ProgrammingLanguage;
import com.assessment.data.Question;
import com.assessment.data.QuestionMapper;
import com.assessment.data.QuestionType;
import com.assessment.data.Test;
import com.assessment.data.TestCase;
import com.assessment.data.TestCases;
import com.assessment.data.User;
import com.assessment.data.UserType;
import com.assessment.services.CompanyService;
import com.assessment.services.QuestionService;
import com.assessment.services.UserService;
import com.assessment.web.dto.SectionDto;
import com.assessment.web.dto.VerificationResultsQ;

@Controller
public class QuestionController {
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CompanyService companyService;

	@Autowired
	PropertyConfig propertyConfig;

	Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@RequestMapping(value = "/goback", method = RequestMethod.GET)
	public ModelAndView goback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
//		List<Question> questions = questionService.findQuestions(user.getCompanyId());
//		mav.addObject("qs", questions);
		return listQuestions(null, response, request, mav.getModelMap());
		// return mav;
	}

	@RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
	public ModelAndView addQuestion(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam(name = "qid", required = false) Long qid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("add_question");

		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		if (qid == null) {
			Question question = new Question();
			mav.addObject("question", question);
			mav.addObject("question_label", "Add new Question");
		} else {
			Question question = questionService.findById(qid);
			question.setType(question.getQuestionType() != null
					? question.getQuestionType().getType()
					: QuestionType.MCQ.getType());
			question.setLang(question.getLanguage() != null ? question.getLanguage().getLanguage()
					: ProgrammingLanguage.JAVA.getLanguage());

			question.setUpFromInUpdateMode();
			mav.addObject("question", question);
			mav.addObject("editQMode", "true");
			mav.addObject("question_label", "Update this Question");
		}

		// Page<Question> questions =
		// questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId(), pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		mav.addObject("stacks", FullStackOptions.values());
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"addQuestion", null);
		return mav;
	}

	@RequestMapping(value = "/removeQuestionFromList", method = RequestMethod.GET)
	public ModelAndView removeQuestionFromList(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam(name = "qid", required = false) Long qid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("question_list");
		if (qid != null) {
			Boolean canDelete = questionService.canDeleteQuestion(qid);
			if (canDelete) {
				questionService.removeQuestion(qid);
				mav.addObject("message", "Question successfully deleted");// later put it as
										// label
				mav.addObject("msgtype", "success");
			} else {
				mav.addObject("message",
						"This Question is associated with one or more Tests. Can not delete this Q");// later
													// put
													// it
													// as
													// label
				mav.addObject("msgtype", "failure");
			}
		} else {
			mav.addObject("message", "Nothing to remove");// later put it as label
			mav.addObject("msgtype", "failure");
		}

		Question question = new Question();
		mav.addObject("question", question);
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		// Page<Question> questions =
		// questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId(), pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"question_list", null);
		return mav;
	}

	@RequestMapping(value = "/removeQuestion", method = RequestMethod.GET)
	public ModelAndView removeQuestion(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam(name = "qid", required = false) Long qid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("add_question");
		if (qid != null) {
			Boolean canDelete = questionService.canDeleteQuestion(qid);
			if (canDelete) {
				questionService.removeQuestion(qid);
				mav.addObject("message", "Question successfully deleted");// later put it as
										// label
				mav.addObject("msgtype", "success");
			} else {
				mav.addObject("message",
						"This Question is associated with one or more Tests. Can not delete this Q");// later
													// put
													// it
													// as
													// label
				mav.addObject("msgtype", "failure");
			}
		} else {
			mav.addObject("message", "Nothing to remove");// later put it as label
			mav.addObject("msgtype", "failure");
		}

		Question question = new Question();
		mav.addObject("question", question);
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		// Page<Question> questions =
		// questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId(), pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"addQuestion", null);
		return mav;
	}

	@RequestMapping(value = "/saveQuestion", method = RequestMethod.POST)
	public ModelAndView saveQuestion(
			@RequestParam(name = "addtestcases", required = false) MultipartFile addtestcases,
			@RequestParam(name = "addimage", required = false) MultipartFile addimage,
			@RequestParam(name = "addaudio", required = false) MultipartFile addaudio,
			@RequestParam(name = "addvideo", required = false) MultipartFile addvideo,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("question") Question question) throws Exception {
		logger.info("in save Q q.getconstr " + question.getConstrnt());
		ModelAndView mav = null;
		User user = (User) request.getSession().getAttribute("user");
		List<Question> questions = new ArrayList<Question>();

		if (addtestcases != null && addtestcases.getSize() != 0) {
			String error = "";
			try {
				System.out.println("1.......");
				logger.info("1.......");
				TestCases testCases = (TestCases) Unmarshaller.unmarshal(TestCases.class,
						new InputStreamReader(addtestcases.getInputStream()));
//				JAXBContext jaxbContext     = JAXBContext.newInstance( TestCases.class );
//				javax.xml.bind.Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//				TestCases testCases = (TestCases)jaxbUnmarshaller.unmarshal(new InputStreamReader(addtestcases.getInputStream()));
				List<TestCase> cases = testCases.getCases();
				System.out.println("2.......");
				logger.info("2.......");
				for (TestCase case1 : cases) {
					System.out.println("2.1.......");
					logger.info("2.1.......");
					if (case1.getTestCaseType() == null || case1.getTestCaseType()
							.trim().length() == 0) {
						error = "Test Cases Xml Upload - No Test Case Type Specified for test case "
								+ case1.getName();
						break;
					} else if ((!case1.getTestCaseType()
							.equalsIgnoreCase("functional"))
							&& (!case1.getTestCaseType()
									.equalsIgnoreCase("boundary"))
							&& (!case1.getTestCaseType()
									.equalsIgnoreCase("exception"))) {
						error = "Test Cases Xml Upload - Invalid case type specified for "
								+ case1.getName();
						break;
					}

					if (case1.getExpectedOuput() == null || case1.getExpectedOuput()
							.trim().length() == 0) {
						error = "Test Cases Xml Upload - Invalid expected output specified for "
								+ case1.getName();
						break;
					}

					if (case1.getName() == null
							|| case1.getName().trim().length() == 0) {
						error = "Test Cases Xml Upload - Name not present";
						break;
					}

					if (case1.getWeight() == null) {
						error = "Test Cases Xml Upload - Invalid Weight specified for "
								+ case1.getName();
						break;
					}

					if (case1.getMandatory() == null) {
						error = "Test Cases Xml Upload - Invalid Mandatory specified for "
								+ case1.getName();
						break;
					}

					if (case1.getDesc() == null
							|| case1.getDesc().trim().length() == 0) {
						error = "Test Cases Xml Upload - Invalid Desc specified for "
								+ case1.getName();
						break;
					}
					System.out.println("2.1.......end");
					logger.info("2.1.......end");
				}
				System.out.println("3.......");
				logger.info("3.......");
				String testcasesxml = new String(addtestcases.getBytes(), "UTF-8");
				logger.info("3.......testcases xml");
				System.out.println("3.......testcases xml");
				question.setTestcasesXml(testcasesxml);
				logger.info("3.......testcases xml setting done error " + error);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.error("upload test acses prob", e);
				System.out.println("4.......");
				error = "Test Cases Xml Upload - Invalid Test Cases file " + e.getMessage();
			}

			logger.info("in error loop");
			if (error.trim().length() != 0) {
				// questions = questionService.findQuestions(user.getCompanyId());
				logger.info("in error loop 1");
				Page<Question> qs = questionService
						.getAllLevel1Questions(user.getCompanyId(), 0);
				logger.info("in error loop 2");
				mav = new ModelAndView("add_question");
				mav.addObject("question", question);
				mav.addObject("message", error);// later put it as label
				mav.addObject("msgtype", "failure");
				mav.addObject("types", QuestionType.values());
				mav.addObject("qs", qs.getContent());
				mav.addObject("levels", DifficultyLevel.values());
				mav.addObject("stacks", FullStackOptions.values());
				logger.info("in error loop ret");
				return mav;
			}
		}

		if (addimage != null && addimage.getSize() != 0) {
			String destination = propertyConfig.getFileServerLocation() + File.separator + "images"
					+ File.separator + addimage.getOriginalFilename();
			File file = new File(destination);
			if (file.exists()) {
				if (addimage.getOriginalFilename() != null && addimage.getOriginalFilename()
						.trim().length() > 0) {
					FileUtils.forceDelete(file);
				}

			}
			if (addimage.getOriginalFilename() != null
					&& addimage.getOriginalFilename().trim().length() > 0) {
				String imageUrl = propertyConfig.getFileServerWebUrl() + "images/"
						+ addimage.getOriginalFilename();
				question.setImageUrl(imageUrl);
				addimage.transferTo(file);
			}

		}
		logger.info("addimage check gone");
		if (addaudio != null && addaudio.getSize() != 0) {
			String destination = propertyConfig.getFileServerLocation() + File.separator + "audios"
					+ File.separator + addaudio.getOriginalFilename();
			File file = new File(destination);
			if (file.exists()) {
				if (addaudio.getOriginalFilename() != null && addaudio.getOriginalFilename()
						.trim().length() > 0) {
					FileUtils.forceDelete(file);
				}

			}

			if (addaudio.getOriginalFilename() != null
					&& addaudio.getOriginalFilename().trim().length() > 0) {
				addaudio.transferTo(file);
				String audioUrl = propertyConfig.getFileServerWebUrl() + "audios/"
						+ addaudio.getOriginalFilename();
				question.setAudioURL(audioUrl);
			}

		}
		logger.info("addaudio check gone");
		if (addvideo != null && addvideo.getSize() != 0) {
			String destination = propertyConfig.getFileServerLocation() + File.separator + "videos"
					+ File.separator + addvideo.getOriginalFilename();
			File file = new File(destination);
			if (file.exists()) {
				if (addvideo.getOriginalFilename() != null && addvideo.getOriginalFilename()
						.trim().length() > 0) {
					FileUtils.forceDelete(file);
				}
			}

			if (addvideo.getOriginalFilename() != null
					&& addvideo.getOriginalFilename().trim().length() > 0) {
				addvideo.transferTo(file);
				String videoUrl = propertyConfig.getFileServerWebUrl() + "videos/"
						+ addvideo.getOriginalFilename();
				question.setVideoURL(videoUrl);
			}

		}

		try {
			logger.info("before setup");
			question.setup();
			logger.info("after setup");
		} catch (AssessmentGenericException e) {
			// TODO Auto-generated catch block
			// questions = questionService.findQuestions(user.getCompanyId());
			Page<Question> qs = questionService.getAllLevel1Questions(user.getCompanyId(), 0);
			mav = new ModelAndView("add_question");
			mav.addObject("question", question);
			mav.addObject("message", "Select atleast 1 Correct answer");// later put it as label
			mav.addObject("msgtype", "failure");
			mav.addObject("types", QuestionType.values());
			mav.addObject("qs", qs.getContent());
			mav.addObject("levels", DifficultyLevel.values());
			mav.addObject("stacks", FullStackOptions.values());
			return mav;
		}
		logger.info("after setup further");
		question.setCompanyId(user.getCompanyId());
		question.setCompanyName(user.getCompanyName());
		if ((!question.getInputCode().contains("<br />")) && question.getInputCode() != null) {
			question.setInputCode(question.getInputCode().replaceAll("\\r\\n|\\r|\\n", "<br />"));
		}

		if ((!question.getInstructionsIfAny().contains("<br />"))
				&& question.getInstructionsIfAny() != null) {
			question.setInstructionsIfAny(
					question.getInstructionsIfAny().replaceAll("\n", "<br />"));
		}

		if (question.getQuestionType().getType().equals(QuestionType.CODING.getType()) || question
				.getQuestionType().getType().equals(QuestionType.MCQ.getType())) {
			question.setFullstack(FullStackOptions.NONE);
		} else if (question.getQuestionType().getType().equals(QuestionType.FILL_BLANKS_MCQ.getType())
				|| question.getQuestionType().getType()
						.equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())) {
			question.setFullstack(FullStackOptions.NONE);
		} else if (question.getQuestionType().getType()
				.equals(QuestionType.IMAGE_UPLOAD_BY_USER.getType())
				|| question.getQuestionType().getType()
						.equals(QuestionType.VIDEO_UPLOAD_BY_USER.getType())
				|| question.getQuestionType().getType()
						.equals(QuestionType.SUBJECTIVE_TEXT.getType())) {
			question.setFullstack(FullStackOptions.NONE);
		} else {
			String reviewer = question.getReviewerEmail();
			logger.info("review check");
			if (reviewer == null || reviewer.trim().length() == 0) {
				// questions = questionService.findQuestions(user.getCompanyId());
				logger.info("review check 1");
				Page<Question> qs = questionService
						.getAllLevel1Questions(user.getCompanyId(), 0);
				logger.info("review check 2");
				mav = new ModelAndView("add_question");
				mav.addObject("question", question);
				mav.addObject("message",
						"Select a Reviewer for the Full Stack Problem statement");// later
												// put
												// it
												// as
												// label
				mav.addObject("msgtype", "failure");
				mav.addObject("types", QuestionType.values());
				mav.addObject("qs", qs.getContent());
				mav.addObject("levels", DifficultyLevel.values());
				mav.addObject("stacks", FullStackOptions.values());
				return mav;
			}
			User usr = userService.findByPrimaryKey(reviewer, user.getCompanyId());
			if (usr != null && (!usr.getUserType().getType()
					.equals(UserType.REVIEWER.getType()))) {
				// questions = questionService.findQuestions(user.getCompanyId());
				logger.info("review check 11");
				Page<Question> qs = questionService
						.getAllLevel1Questions(user.getCompanyId(), 0);
				logger.info("review check 111");
				mav = new ModelAndView("add_question");
				mav.addObject("question", question);
				mav.addObject("message",
						"The reviewer email selected does have privileges to be a Reviewer. Enter some other Reviewer email id");// later
																// put
																// it
																// as
																// label
				mav.addObject("msgtype", "failure");
				mav.addObject("types", QuestionType.values());
				mav.addObject("qs", qs.getContent());
				mav.addObject("levels", DifficultyLevel.values());
				mav.addObject("stacks", FullStackOptions.values());
				return mav;
			} else if (usr == null) {
				User user2 = new User();
				user2.setUserType(UserType.REVIEWER);
				user2.setEmail(reviewer);
				user2.setPassword("" + reviewer.hashCode());
				user2.setCompanyId(user.getCompanyId());
				user2.setCompanyName(user.getCompanyName());
				logger.info("saving user 1");
				userService.saveOrUpdate(user2);
				logger.info("saving user 2");
			}

		}

		if (question.getId() != null) {
			System.out.println("before saving Qu");
			logger.info("before saving Qu");
			questionService.updateQuestion(question);
			System.out.println("before saving Qu");
			logger.info("before saving Qu");
		} else {
			logger.info("before saving Q");
			questionService.createQuestion(question);
			logger.info("after saving Q");
		}

//		questions = questionService.findQuestions(user.getCompanyId());
//		mav = new ModelAndView("question_list");
//		mav.addObject("qs", questions);
//		return mav;
		mav = new ModelAndView("add_question");
		mav.addObject("message", "Question Save Success");// later put it as label
		mav.addObject("msgtype", "Success");
		// return listQuestions(null, response, request, mav.getModelMap());
		question = new Question();
		question.setType(question.getQuestionType() != null ? question.getQuestionType().getType()
				: QuestionType.MCQ.getType());

		question.setLang(question.getLanguage() != null ? question.getLanguage().getLanguage()
				: ProgrammingLanguage.JAVA.getLanguage());
		mav.addObject("question", question);
		mav.addObject("question_label", "Add new Question");
		logger.info("after saving Q ...1");
		Page<Question> questions2 = questionService.findQuestionsByPage(user.getCompanyId(), 0);
		logger.info("after saving Q ...2");
		mav.addObject("qs", questions2.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		mav.addObject("stacks", FullStackOptions.values());
		logger.info("after saving Q ...3");
		CommonUtil.setCommonAttributesOfPagination(questions2, mav.getModelMap(), 0, "addQuestion", null);
		logger.info("after saving Q ...4");
		return mav;
	}

	@RequestMapping(value = "/searchQByQualifier1", method = RequestMethod.GET)
	public ModelAndView searchQByQualifier1(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String qualifier1, HttpServletRequest request,
			HttpServletResponse response) {
		String referer = request.getHeader("Referer");
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserType.LMS_ADMIN)) {
			mav.setViewName("lms_question_list");
		}
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.findQuestionsByQualifier1AndPage(user.getCompanyId(),
				qualifier1, pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		Map<String, String> params = new HashMap<>();
		params.put("qualifier1", qualifier1);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQByQualifier1", params);
		return mav;
	}

	@RequestMapping(value = "/searchQByQualifier1And2", method = RequestMethod.GET)
	public ModelAndView searchQByQualifier1And2(
			@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String qualifier1, @RequestParam String qualifier2,
			HttpServletRequest request, HttpServletResponse response) {
		String referer = request.getHeader("Referer");
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserType.LMS_ADMIN)) {
			mav.setViewName("lms_question_list");
		}
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.findQuestionsByQualifier2AndPage(user.getCompanyId(),
				qualifier1, qualifier2, pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		Map<String, String> params = new HashMap<>();
		params.put("qualifier1", qualifier1);
		params.put("qualifier2", qualifier2);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQByQualifier1And2", params);
		return mav;
	}

	@RequestMapping(value = "/searchQByQualifier1And2And3", method = RequestMethod.GET)
	public ModelAndView searchQByQualifier1And2And3(
			@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String qualifier1, @RequestParam String qualifier2,
			@RequestParam String qualifier3, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserType.LMS_ADMIN)) {
			mav.setViewName("lms_question_list");
		}
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.findQuestionsByQualifier3AndPage(user.getCompanyId(),
				qualifier1, qualifier2, qualifier3, pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		Map<String, String> params = new HashMap<>();
		params.put("qualifier1", qualifier1);
		params.put("qualifier2", qualifier2);
		params.put("qualifier3", qualifier3);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQByQualifier1And2And3", params);
		return mav;
	}

	@RequestMapping(value = "/searchQByQualifier1And2And3And4", method = RequestMethod.GET)
	public ModelAndView searchQByQualifier1And2And3And4(
			@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String qualifier1, @RequestParam String qualifier2,
			@RequestParam String qualifier3, @RequestParam String qualifier4,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserType.LMS_ADMIN)) {
			mav.setViewName("lms_question_list");
		}
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.findQuestionsByQualifier4AndPage(user.getCompanyId(),
				qualifier1, qualifier2, qualifier3, qualifier4, pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		Map<String, String> params = new HashMap<>();
		params.put("qualifier1", qualifier1);
		params.put("qualifier2", qualifier2);
		params.put("qualifier3", qualifier3);
		params.put("qualifier4", qualifier4);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQByQualifier1And2And3And4", params);
		return mav;
	}

	@RequestMapping(value = "/searchQByQualifier1And2And3And4And5", method = RequestMethod.GET)
	public ModelAndView searchQByQualifier1And2And3And4And5(
			@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String qualifier1, @RequestParam String qualifier2,
			@RequestParam String qualifier3, @RequestParam String qualifier4,
			@RequestParam String qualifier5, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserType.LMS_ADMIN)) {
			mav.setViewName("lms_question_list");
		}
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.findQuestionsByQualifier5AndPage(user.getCompanyId(),
				qualifier1, qualifier2, qualifier3, qualifier4, qualifier5, pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		Map<String, String> params = new HashMap<>();
		params.put("qualifier1", qualifier1);
		params.put("qualifier2", qualifier2);
		params.put("qualifier3", qualifier3);
		params.put("qualifier4", qualifier4);
		params.put("qualifier5", qualifier5);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQByQualifier1And2And3And4And5", params);
		return mav;
	}

	@RequestMapping(value = "/searchQuestions", method = RequestMethod.GET)
	public ModelAndView searchQuestions(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String searchText, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getUserType().equals(UserType.LMS_ADMIN)) {
			mav.setViewName("lms_question_list");
		}
		if (pageNumber == null) {
			pageNumber = 0;
		}
		Page<Question> questions = questionService.searchQuestions(user.getCompanyId(), searchText,
				pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		Map<String, String> params = new HashMap<>();
		params.put("searchText", searchText);
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQuestions", params);
		return mav;
	}

	@RequestMapping(value = "/searchQuestions2", method = RequestMethod.GET)
	public ModelAndView searchQuestions2(@RequestParam(name = "page", required = false) Integer pageNumber,
			@RequestParam String searchText, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("add_test_step2_new3");
		Question question = new Question();
		mav.addObject("question", question);
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
//		
		SectionDto sectionDto = (SectionDto) request.getSession().getAttribute("sectionDTO");

		Set<Question> questions2 = sectionDto.getQuestions();
		for (Question q : questions2) {
			q.setSelected(true);
		}
		mav.addObject("qs", questions2);

		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		mav.addObject("sectionDto", sectionDto);
		Test test2 = (Test) request.getSession().getAttribute("test");
		mav.addObject("test", test2);

		Page<Question> questions = questionService.searchQuestions(user.getCompanyId(), searchText,
				pageNumber);
		List<String> quest = (List<String>) request.getSession().getAttribute("qu");
		mav.addObject("qu", quest);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		CommonUtil.setCommonAttributesOfPagination(questions, mav.getModelMap(), pageNumber,
				"searchQuestions2", null);
		return mav;
	}

	/*
	 * This method is used for Fill inthe blank MCQ Question UPLOAD BY EXCEL SHEET
	 * APEND Qtype,bnlanks Question,seprated Adedd By Suhel Date 20-04-2020
	 */
	@RequestMapping(value = "/uploadFillBlank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> blankInQuestion(HttpServletResponse response, MultipartHttpServletRequest request,
			ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			MultipartFile multipartFile = request.getFile("fileQuestions1");

			InputStream stream = multipartFile.getInputStream();
			// File file = ResourceUtils.getFile("classpath:fill-in-theBlank.xml");
//			 File file = new File("fill-in-theBlank.xml");
			File file = ResourceUtils.getFile("classpath:fill-in-theBlank.xml");
			List<Question> questions = new ArrayList<>();
			try {
				questions = ExcelReader.parseExcelFileToBeans(stream, file);
			} catch (org.jxls.reader.XLSDataReadException e) {
				// TODO Auto-generated catch block
				logger.error("problem in reading excel", e);
				String errormsg = "Excel Data not Found";
				// model.put("error",errormsg);
				map.put("error1", "No Data in excel");
				return map;
			}
			logger.info("in upload method qs size " + questions.size());

			if (questions.size() == 0) {
				String errormsg = "Excel Data not Found";
				// model.put("error",errormsg);
				map.put("error1", "No Data in excel");
				return map;
			}
			// change for uploading validation by suhel
			String compId = questions.get(0).getCompanyId();
			System.out.println("comp id is " + compId);
			Company company = companyService.findByCompanyId(compId);
			System.out.println("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			logger.info("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			for (Question q : questions) {

				int nooffillblanks = q.getNoOfFillBlanks();
				int count = 0;
				String fillInBlankOptions = q.getFillInBlankOptions();
				String[] fillInBlankSplit = fillInBlankOptions.split(",");
				if (fillInBlankSplit.length != nooffillblanks) {
					String errormsg = "Number of fill in blanks and options listed are different for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				if (q.getQualifier1() == null || q.getQualifier1().trim().length() == 0) {
					String errormsg = "Qualifier1 not available for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				if (q.getDiff() == null || q.getDiff().trim().length() == 0) {
					String errormsg = "Difficulty level not available for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				if (q.getType() == null || q.getType().trim().length() == 0 || !q.getType()
						.equalsIgnoreCase(QuestionType.FILL_BLANKS_MCQ
								.getType())) {
					String errormsg = "Invalid Question Type for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				q.setQualifier2((q.getQualifier2() == null
						|| q.getQualifier2().trim().length() == 0) ? "NA"
								: q.getQualifier2());
				q.setQualifier3((q.getQualifier3() == null
						|| q.getQualifier3().trim().length() == 0) ? "NA"
								: q.getQualifier3());
				q.setQualifier4((q.getQualifier4() == null
						|| q.getQualifier4().trim().length() == 0) ? "NA"
								: q.getQualifier4());
				q.setQualifier5((q.getQualifier5() == null
						|| q.getQualifier5().trim().length() == 0) ? "NA"
								: q.getQualifier5());

				System.out.println(q.getQuestionText());
				String fib = fillInBlankOptions.replace(",", System.lineSeparator());
				fillInBlankSplit = fib.split(System.lineSeparator());
				String formatted = "";
				int cnt = 0;
				for (String s : fillInBlankSplit) {
					if (cnt < (fillInBlankSplit.length - 1)) {
						s = s.trim();
						formatted += s + System.lineSeparator();
					} else {
						s = s.trim();
						formatted += s;
					}
					cnt++;
				}
				q.setFillInBlankOptions(formatted);
				q.setCompanyId(company.getCompanyId());
				q.setCompanyName(company.getCompanyName());
				questionService.createQuestion(q);
			}
			logger.info("upload qs in db complete");
			// stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem in uploading qs", e);
			throw new AssessmentGenericException("problem in uploading qs", e);
		}
		return map;
	}

	// upload uploadMacherQuestion question and answer
	@RequestMapping(value = "/uploadMacherQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> matcherQuestion(HttpServletResponse response, MultipartHttpServletRequest request,
			ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			MultipartFile multipartFile = request.getFile("fileQuestions2");
			// Long size = multipartFile.getSize();
			String fileName = multipartFile.getName();
			// String contentType = multipartFile.getContentType();
			InputStream stream = multipartFile.getInputStream();
//			File file = new File("matcher.xml");
			File file = ResourceUtils.getFile("classpath:matcher.xml");
			List<Question> questions = new ArrayList<>();
			try {
				questions = ExcelReader.parseExcelFileToBeans(stream, file);
			} catch (org.jxls.reader.XLSDataReadException e) {
				// TODO Auto-generated catch block
				logger.error("problem in reading excel", e);
				String errormsg = "Excel Data not Found";
				// model.put("error",errormsg);
				map.put("error1", "No Data in excel");
				return map;
			}

			logger.info("in upload method qs size " + questions.size());
			if (questions.size() == 0) {
				String errormsg = "Excel Data not Found";
				// model.put("error",errormsg);
				map.put("error1", "No data in excel");
				return map;
			}

			// change for uploading validation by suhel

			String compId = questions.get(0).getCompanyId();
			System.out.println("comp id is " + compId);
			Company company = companyService.findByCompanyId(compId);
			System.out.println("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			logger.info("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			for (Question q : questions) {
				System.out.println(q.getQuestionText());
				q.setCompanyId(company.getCompanyId());
				q.setCompanyName(company.getCompanyName());

				if (q.getQualifier1() == null || q.getQualifier1().trim().length() == 0) {
					String errormsg = "Qualifier1 not available for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				if (q.getMatchLeft1() == null && q.getMatchLeft1().trim().length() == 0) {
					String errormsg = "Match 1 option not available for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				if (q.getMatchLeft2() == null && q.getMatchLeft2().trim().length() == 0) {
					String errormsg = "Match 2 option not available for "
							+ q.getQuestionText();
					// model.put("error",errormsg);
					map.put("error", errormsg);
					return map;
				}

				if (q.getMatchLeft3() != null && q.getMatchLeft3().trim().length() != 0) {
					if (q.getMatchRight3() == null || q.getMatchRight3().trim()
							.length() == 0) {
						String errormsg = "Match 3 option not available for "
								+ q.getQuestionText();
						// model.put("error",errormsg);
						map.put("error", errormsg);
						return map;
					}

				}

				if (q.getMatchLeft4() != null && q.getMatchLeft4().trim().length() != 0) {
					if (q.getMatchRight4() == null || q.getMatchRight4().trim()
							.length() == 0) {
						String errormsg = "Match 4 option not available for "
								+ q.getQuestionText();
						// model.put("error",errormsg);
						map.put("error", errormsg);
						return map;
					}

				}

				if (q.getMatchLeft5() != null && q.getMatchLeft5().trim().length() != 0) {
					if (q.getMatchRight5() == null || q.getMatchRight5().trim()
							.length() == 0) {
						String errormsg = "Match 5 option not available for "
								+ q.getQuestionText();
						// model.put("error",errormsg);
						map.put("error", errormsg);
						return map;
					}

				}

				if (q.getMatchLeft6() != null && q.getMatchLeft6().trim().length() != 0) {
					if (q.getMatchRight6() == null || q.getMatchRight6().trim()
							.length() == 0) {
						String errormsg = "Match 6 option not available for "
								+ q.getQuestionText();
						// model.put("error",errormsg);
						map.put("error", errormsg);
						return map;
					}

				}

				q.setQualifier2((q.getQualifier2() == null
						|| q.getQualifier2().trim().length() == 0) ? "NA"
								: q.getQualifier2());
				q.setQualifier3((q.getQualifier3() == null
						|| q.getQualifier3().trim().length() == 0) ? "NA"
								: q.getQualifier3());
				q.setQualifier4((q.getQualifier4() == null
						|| q.getQualifier4().trim().length() == 0) ? "NA"
								: q.getQualifier4());
				q.setQualifier5((q.getQualifier5() == null
						|| q.getQualifier5().trim().length() == 0) ? "NA"
								: q.getQualifier5());

				questionService.createQuestion(q);
			}
			logger.info("upload qs in db complete");
			stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem in uploading qs", e);
			throw new AssessmentGenericException("problem in uploading qs", e);
		}
		return map;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	@ResponseBody
	public void uploadQuestions(HttpServletResponse response, MultipartHttpServletRequest request)
			throws Exception {
		try {
			MultipartFile multipartFile = request.getFile("fileQuestions");
			Long size = multipartFile.getSize();
			String fileName = multipartFile.getName();
			String contentType = multipartFile.getContentType();
			InputStream stream = multipartFile.getInputStream();
			File file = ResourceUtils.getFile("classpath:questions.xml");
			List<Question> questions = ExcelReader.parseExcelFileToBeans(stream, file);
			logger.info("in upload method qs size " + questions.size());
			if (questions.size() == 0) {
				throw new AssessmentGenericException("NO_DATA_IN_EXCEL");
			}
			String compId = questions.get(0).getCompanyId();
			// System.out.println("comp id is "+compId);
			Company company = companyService.findByCompanyId(compId);
			// System.out.println("Company got in uploadQuestions "+company.getId() +"
			// "+company.getCompanyName());
			logger.info("Company got in uploadQuestions " + company.getId() + " "
					+ company.getCompanyName());
			for (Question q : questions) {
				// System.out.println(q.getQuestionText());
				q.setCompanyId(company.getCompanyId());
				q.setCompanyName(company.getCompanyName());
				q.setChoice1(q.getChoice1().trim());
				q.setChoice2(q.getChoice2().trim());
				q.setChoice3(q.getChoice3() == null ? "" : q.getChoice3().trim());
				q.setChoice4(q.getChoice4() == null ? "" : q.getChoice4().trim());
				q.setChoice5(q.getChoice5() == null ? "" : q.getChoice5().trim());
				q.setChoice6(q.getChoice6() == null ? "" : q.getChoice6().trim());
				q.setRightChoices(q.getRightChoices().trim());
				questionService.createQuestion(q);
			}
			logger.info("upload qs in db complete");
//			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem in uploading qs", e);
			throw new AssessmentGenericException("problem in uploading qs", e);
//			return "Company id is not valid";
		}
	}

	@RequestMapping(value = "/question_list", method = RequestMethod.GET)
	public ModelAndView listQuestions(@RequestParam(name = "page", required = false) Integer pageNumber,
			HttpServletResponse response, HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		ModelAndView mav = new ModelAndView("question_list");
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		// Page<Question> questions =
		// questionService.findQuestionsByPage(user.getCompanyId(), pageNumber);
		Page<Question> questions = questionService.getAllLevel1Questions(user.getCompanyId(), pageNumber);
		mav.addObject("qs", questions.getContent());
		mav.addObject("levels", DifficultyLevel.values());
		mav.addObject("types", QuestionType.values());
		mav.addObject("languages", ProgrammingLanguage.values());
		CommonUtil.setCommonAttributesOfPagination(questions, modelMap, pageNumber, "question_list",
				null);
		return mav;
	}

	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public ModelAndView verification(@RequestParam(name = "page", required = false) Integer pageNumber,
			HttpServletResponse response, HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		ModelAndView mav = new ModelAndView("verify");

		return mav;
	}

	@RequestMapping(value = "/verifydata", method = RequestMethod.POST)
	public ModelAndView verifydata(HttpServletResponse response, MultipartHttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView("verifyresults2");
		try {
			MultipartFile multipartFile = request.getFile("fileToUpload");
			Long size = multipartFile.getSize();
			String fileName = multipartFile.getName();
			String contentType = multipartFile.getContentType();
			InputStream stream = multipartFile.getInputStream();
			File file = new File("questions.xml");
			List<Question> questions = ExcelReader.parseExcelFileToBeans(stream, file);
			List<VerificationResultsQ> vers = new ArrayList<>();
			for (Question q : questions) {
				Company c = companyService.findByCompanyId(q.getCompanyId());
				String problem = "";
				if (c == null) {
					problem += "Invalid Company Id " + q.getCompanyId() + ".";
				}

				if (q.getRightChoices() == null) {
					problem += " No Correct Answer selected.";
				}

				if (!(q.getRightChoices().contains("Choice 1")
						|| q.getRightChoices().contains("Choice 2")
						|| q.getRightChoices().contains("Choice 3")
						|| q.getRightChoices().contains("Choice 4")
						|| q.getRightChoices().contains("Choice 5")
						|| q.getRightChoices().contains("Choice 6"))) {
					problem += " Invalid Correct Choice " + q.getRightChoices();
				}
				if (problem.length() > 0) {
					VerificationResultsQ ver = new VerificationResultsQ();
					ver.setQuestionText(q.getQuestionText());
					ver.setQuestionProblem(problem);
					vers.add(ver);
				}

			}
			mav.addObject("results", vers);
			logger.info("verifydata - verification complete");
			return mav;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem verifydata - verification", e);
			mav.addObject("problem", "Problem in File Upload. Contact Admin");
			// throw new AssessmentGenericException("problem in uploading qs", e);
			return mav;
		}
	}
}
