package com.assessment.web.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.Exceptions.AssessmentGenericException;
import com.assessment.common.CommonUtil;
import com.assessment.data.CandidateProfileParams;
import com.assessment.data.LearnersProfileParam;
import com.assessment.data.License;
import com.assessment.data.Module;
import com.assessment.data.ModuleItem;
import com.assessment.data.PieChart1;
import com.assessment.data.PieChart2;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.User;
import com.assessment.data.UserTestSession;
import com.assessment.reports.manager.UserTrait;
import com.assessment.repositories.LearnersProfileParamRepository;
import com.assessment.repositories.PieChart1Repository;
import com.assessment.repositories.PieChart2Repository;
import com.assessment.repositories.UserRepository;
import com.assessment.services.CandidateProfileParamsService;
import com.assessment.services.LearnersProfileService;
import com.assessment.services.LicenseService;
import com.assessment.services.ModuleService;
import com.assessment.services.Piechart1Service;
import com.assessment.services.Piechart2Service;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.TestService;
import com.assessment.services.UserService;
import com.assessment.services.UserTestSessionService;
import com.assessment.web.dto.UserTestDto;
import com.google.gson.Gson;

@Controller
public class LMSAdminController {

	@Autowired
	LicenseService licenseService;
	@Autowired
	ModuleService moduleService;
	@Autowired
	TestService testService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserTestSessionService userTestSessionService;
	@Autowired
	UserService userService;

	@Autowired
	LearnersProfileParamRepository profileParamRepository;

	@Autowired
	QuestionMapperInstanceService questionMapperInstanceService;
	@Autowired
	CandidateProfileParamsService candidateProfileParamsService;

	@Autowired
	LearnersProfileService profileService;

	@Autowired
	Piechart1Service piechart1Service;
	@Autowired
	PieChart1Repository pieChart1Repository;
	@Autowired
	Piechart2Service piechart2Service;
	@Autowired
	PieChart2Repository pieChart2Repository;

	@GetMapping("/lmsAdminDashboard")
	public ModelAndView gotolmsAdminDashboard(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("user_profile_index");
		User user = (User) request.getSession().getAttribute("user");
		List<License> license = new ArrayList<>();
		if (user.getLic() != null) {
			for (String licenseName : user.getLic()) {
				License lic = licenseService.findByPrimaryKey(licenseName, user.getCompanyId());
				license.add(lic);
			}
			mav.addObject("licenses", license);
			mav.addObject("licenseName", user.getLicenses().replaceAll(",.*", ""));
		}
		return mav;
	}

	@GetMapping("/getModule")
	@ResponseBody
	public Map<String, Object> getModule(@RequestParam(name = "licenseName", required = false) String licenseName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		List<Module> modules = moduleService.findModulesByLicense(licenseName, user.getCompanyId());
		System.out.println("list of Modules:::  ");
		System.out.println("list of Modules:::  " + modules);
		map.put("modules", modules);
		return map;
	}

	@RequestMapping(value = "/lmsModules", method = RequestMethod.GET)
	public ModelAndView modules(@RequestParam(name = "page", required = false) Integer pageNumber, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		ModelAndView mav = new ModelAndView("lms_modules");

		if (pageNumber == null) {
			pageNumber = 0;
		}

		User user = (User) request.getSession().getAttribute("user");
		Page<Module> modules = moduleService.findByLicenseNamesIn(user.getLic(), user.getCompanyId(), PageRequest.of(pageNumber, 15));
		mav.addObject("modules", modules.getContent());
		CommonUtil.setCommonAttributesOfPagination(modules, modelMap, pageNumber, "lmsModules", null);
		return mav;
	}

	@RequestMapping(value = "/lmsModule", method = RequestMethod.GET)
	public ModelAndView lmsModule(@RequestParam(name = "moduleId", required = false) Long moduleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Module module = null;

		if (moduleId != null) {
			module = moduleService.getModuleById(moduleId);
		} else {
			module = new Module();

		}
		request.getSession().setAttribute("module", module);
		request.getSession().setAttribute("moduleItems", module.getItems());
		ModelAndView mav = new ModelAndView("lms_module");
		User user = (User) request.getSession().getAttribute("user");
		List<String> licenses = user.getLic();
		mav.addObject("module", module);
		mav.addObject("licenses", licenses);
		return mav;
	}

	@RequestMapping(value = "/lmsModuleItem", method = RequestMethod.POST)
	public ModelAndView lmsModuleItem(@ModelAttribute("module") Module module, @RequestParam(name = "moduleItemId", required = false) Long moduleItemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModuleItem moduleItem = null;
		User user = (User) request.getSession().getAttribute("user");
		Set<ModuleItem> items = (Set<ModuleItem>) request.getSession().getAttribute("moduleItems");
		if (moduleItemId != null) {
			// moduleItem = moduleItemRep.findById(moduleItemId).get();

			for (ModuleItem item : items) {
				if (item.getId().equals(moduleItemId)) {
					moduleItem = item;
					break;
				}
			}

			if (moduleItem == null) {
				// situation should not occur
				throw new AssessmentGenericException("Module item null in edit mode");
			}
		} else {
			moduleItem = new ModuleItem();
			moduleItem.setId(System.currentTimeMillis());
			items.add(moduleItem);
			request.getSession().setAttribute("moduleItems", items);
		}
		module.setItems(items);
		request.getSession().setAttribute("module", module);
		ModelAndView mav = new ModelAndView("lms_moduleItem");
		mav.addObject("moduleItem", moduleItem);
		List<String> testNames = testService.getTestNames(user.getCompanyId());
		mav.addObject("testNames", testNames);
		request.getSession().setAttribute("moduleItem", moduleItem);
		return mav;
	}

	@RequestMapping(value = "/lmsSaveModuleitem", method = RequestMethod.POST)
	public ModelAndView lmsSaveModuleitem(@ModelAttribute("moduleItem") ModuleItem moduleItem, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Set<ModuleItem> items = (Set<ModuleItem>) request.getSession().getAttribute("moduleItems");
		Module module = (Module) request.getSession().getAttribute("module");
		ModuleItem moduleItem2 = (ModuleItem) request.getSession().getAttribute("moduleItem");
		moduleItem.setId(moduleItem2.getId());
		for (ModuleItem item : items) {
			if (item.getId() == moduleItem2.getId()) {
				items.remove(item);
				break;
			}
		}
		module.setItems(items);
		items.add(moduleItem);
		ModelAndView mav = new ModelAndView("lms_module");
		List<String> licenses = user.getLic();
		mav.addObject("module", module);
		mav.addObject("licenses", licenses);
		request.getSession().setAttribute("module", module);
		request.getSession().setAttribute("moduleItems", module.getItems());
		mav.addObject("message", "Module Item Saved");// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;
	}

	@RequestMapping(value = "/lmsSaveModule", method = RequestMethod.POST)
	public ModelAndView lmsSaveModule(@ModelAttribute("module") Module module, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		Set<ModuleItem> items = (Set<ModuleItem>) request.getSession().getAttribute("moduleItems");
		module.setItems(items);
		module.setCompanyName(user.getCompanyName());
		module.setCompanyId(user.getCompanyId());
		for (ModuleItem item : module.getItems()) {
			item.setCompanyId(user.getCompanyId());
			item.setCompanyName(user.getCompanyName());
		}
		try {
			Long id = module.getId();
			moduleService.saveOrUpdate(module);
//			if(id != null){
//				moduleService.deleteModule(id);
//			}
		} catch (AssessmentGenericException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			if (e.getMessage().equalsIgnoreCase("MODULE_NAME_EXISTING")) {
				request.getSession().setAttribute("module", module);
				request.getSession().setAttribute("moduleItems", module.getItems());
				ModelAndView mav = new ModelAndView("module");
				List<String> licenses = licenseService.getLicensesInString(user.getCompanyId());
				mav.addObject("module", module);
				mav.addObject("licenses", licenses);
				mav.addObject("message", "Module name already exists. Use a different name");// later
											// put
											// it
											// as
											// label
				mav.addObject("msgtype", "Information");
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("lms_modules");
		mav.addObject("message", "Module - " + module.getModuleName() + " saved");// later put it as label
		mav.addObject("msgtype", "Information");

		Page<Module> modules = moduleService.findByLicenseNamesIn(user.getLic(), user.getCompanyId(), PageRequest.of(0, 15));
		mav.addObject("modules", modules.getContent());
		CommonUtil.setCommonAttributesOfPagination(modules, modelMap, 0, "modules", null);
		return mav;
	}

	@RequestMapping(value = "/lmsDeleteModule", method = RequestMethod.GET)
	public ModelAndView deleteModule(@RequestParam(name = "moduleId", required = true) Long moduleId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			moduleService.deleteModule(moduleId);
		}
		ModelAndView mav = new ModelAndView("lms_modules");

		Page<Module> modules = moduleService.getModules(user.getCompanyId(), PageRequest.of(0, 15));
		mav.addObject("modules", modules.getContent());
		CommonUtil.setCommonAttributesOfPagination(modules, modelMap, 0, "modules", null);
		mav.addObject("message", "Module deletion -  success");// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;
	}

	@GetMapping("/showAllResults")
	public ModelAndView learnersResults(HttpServletRequest request, @RequestParam(name = "page", required = false) Integer pageNumber, ModelMap modelMap) {
		ModelAndView mav = new ModelAndView("results");
		User user = (User) request.getSession().getAttribute("user");
		if (pageNumber == null) {
			pageNumber = 0;
		}
		List<UserTestSession> listUser = userTestSessionService.findByCompanyIdAndCollegeName(user.getCompanyId(), user.getCollegeName());
		mav.addObject("listUser", listUser);
		return mav;
	}

	@GetMapping("/allTestResults")
	public ModelAndView allTestResults(HttpServletRequest request, @RequestParam(name = "email", required = false) String userEmail) {
		ModelAndView mav = new ModelAndView("allTestResults");
		User user = (User) request.getSession().getAttribute("user");
		List<UserTestSession> list = userTestSessionService.findTestListForUser(user.getCompanyId(), userEmail);
		List<UserTestDto> userTestDtos = new ArrayList<UserTestDto>();
		for (UserTestSession session : list) {
			UserTestDto dto = new UserTestDto();
			User user2 = userService.findByPrimaryKey(session.getUser(), session.getCompanyId());
			dto.setFullName(user2.getFirstName() + " " + user2.getLastName());
			dto.setTotalMarksReceived(session.getTotalMarksRecieved());
			dto.setTotalMarks(session.getTotalMarks());
			dto.setTestName(session.getTestName());
			dto.setPass(session.getPass());
			dto.setDate(session.getCreateDate());
			userTestDtos.add(dto);
		}
		mav.addObject("userTestDtos", userTestDtos);
		return mav;
	}

//	
	@GetMapping("/profileParam")
	@ResponseBody
	public Map<String, Object> calculateProfileParam() {
		Map<String, Object> map1 = new HashMap<>();
		List<User> listusers = userRepository.findByUserType();
		List<UserTestSession> listUserTestSession = new ArrayList<UserTestSession>();
		List<LearnersProfileParam> listParam = new ArrayList<LearnersProfileParam>();
		List<PieChart1> chart1s = new ArrayList<>();
		List<PieChart2> chart2s = new ArrayList<>();
		for (User user : listusers) {
			List<UserTestSession> userTestSessions = userTestSessionService.findTestListForUser("e-assess", user.getEmail().replace("\\[.*", ""));
			for (UserTestSession session : userTestSessions) {
				listUserTestSession.add(session);
			}
			System.out.println("size of user TestSession:    " + userTestSessions.size());

			System.out.println("size of All user TestSession:    " + listUserTestSession.size());
			System.out.println("user Email:     " + user.getEmail());
			List<CandidateProfileParams> candidateProfileParams = candidateProfileParamsService.findCandidateProfileParamsByCompanyId("e-assess");
			Map<CandidateProfileParams, List<QuestionMapperInstance>> map = new HashMap<>();
			List<QuestionMapperInstance> answers = new ArrayList<QuestionMapperInstance>();
			for (UserTestSession testSession : listUserTestSession) {
				List<QuestionMapperInstance> answers2 = questionMapperInstanceService.findQuestionMapperInstancesForUserForTest(testSession.getTestName(), testSession.getUser(), testSession.getCompanyId());
				for (QuestionMapperInstance instance : answers2) {
					answers.add(instance);
				}
			}
			Map<String, Integer> qualifier2 = new HashMap<String, Integer>();
			Map<String, Integer> qualifier3 = new HashMap<String, Integer>();
			Map<String, Integer> correct = new HashMap<String, Integer>();
			Map<String, Integer> correct2 = new HashMap<String, Integer>();
			for (QuestionMapperInstance instance : answers) {
				if (instance.getQuestionMapper().getQuestion().getQualifier2() != null && !instance.getQuestionMapper().getQuestion().getQualifier2().isEmpty()) {
					String qq2 = instance.getQuestionMapper().getQuestion().getQualifier1()+"-"+instance.getQuestionMapper().getQuestion().getQualifier2();
					Integer j = qualifier2.get(qq2);
					qualifier2.put(qq2, (j == null) ? 1 : j + 1);
					if (instance.getCorrect()) {
						Integer k = correct.get(qq2);
						correct.put(qq2, (k == null) ? 1 : k + 1);
					}
				}

				if (instance.getQuestionMapper().getQuestion().getQualifier3() != null && !instance.getQuestionMapper().getQuestion().getQualifier3().equalsIgnoreCase("")) {
					String qq3 = instance.getQuestionMapper().getQuestion().getQualifier2() + "-" + instance.getQuestionMapper().getQuestion().getQualifier3();
					Integer j = qualifier3.get(qq3);
					qualifier3.put(qq3, (j == null) ? 1 : j + 1);
					if (instance.getCorrect()) {
						Integer k = correct2.get(qq3);
						correct2.put(qq3, (k == null) ? 1 : k + 1);
					}
				}

			}
			for (Map.Entry<String, Integer> val : qualifier2.entrySet()) {
				PieChart1 chart1 = new PieChart1();
				for (Map.Entry<String, Integer> val2 : correct.entrySet()) {
					if (val.getKey().equalsIgnoreCase(val2.getKey())) {
//						Float ff = (float) (val2.getValue()*100/val.getValue());
						Long result = Math.round(val2.getValue() * 100.0 / val.getValue());
						chart1.setCompanyId(user.getCompanyId());
						chart1.setCompanyName(user.getCompanyName());
						chart1.setPercent(result);
						String qq[] = val.getKey().split("-");
						chart1.setQualifer1(qq[0]);
						chart1.setQualifier2(qq[1]);
						chart1.setUserEmail(user.getEmail());
						chart1s.add(chart1);
					}
				}

			}
			System.out.println("charts::::::::::::    "+chart1s);
			for (Map.Entry<String, Integer> val : qualifier3.entrySet()) {
				PieChart2 chart2 = new PieChart2();
				for (Map.Entry<String, Integer> val2 : correct2.entrySet()) {
					if (val.getKey().equalsIgnoreCase(val2.getKey())) {
//						Float ff = (float) (val2.getValue()*100/val.getValue());
						Long result = Math.round(val2.getValue() * 100.0 / val.getValue());
						chart2.setCompanyId(user.getCompanyId());
						chart2.setCompanyName(user.getCompanyName());
						chart2.setPercent(result);
						String qq[] = val.getKey().split("-");
						chart2.setQualifier2(qq[0]);
						chart2.setQualifier3(qq[1]);
						chart2.setUserEmail(user.getEmail());
						chart2s.add(chart2);
					}
				}

			}

			System.out.println("Charts::::::::                " + chart1s);
			System.out.println("Charts2::::::::                " + chart2s);

			for (QuestionMapperInstance ans : answers) {
				CandidateProfileParams param = new CandidateProfileParams(ans.getQuestionMapper().getQuestion().getQualifier1(), ans.getQuestionMapper().getQuestion().getQualifier2(),
						ans.getQuestionMapper().getQuestion().getQualifier3(), ans.getQuestionMapper().getQuestion().getQualifier4(), ans.getQuestionMapper().getQuestion().getQualifier5());
				if (map.get(param) == null) {
					List<QuestionMapperInstance> ins = new ArrayList<>();
					ins.add(ans);
					map.put(param, ins);
				} else {
					map.get(param).add(ans);
				}
			}
			DecimalFormat df = new DecimalFormat("#.##");
			Map<CandidateProfileParams, Float> mapPer = new HashMap<>();
			Map<CandidateProfileParams, String> mapTrait = new HashMap<>();
			for (CandidateProfileParams param : map.keySet()) {
				List<QuestionMapperInstance> answersForQualifier = map.get(param);
				int noOfCorrect = 0;
				for (QuestionMapperInstance ans : answersForQualifier) {
					if (ans.getCorrect()) {
						noOfCorrect++;
					}
				}

				Float percent = Float.parseFloat(df.format(noOfCorrect * 100 / answersForQualifier.size()));
				mapPer.put(param, percent);
				int index = candidateProfileParams.indexOf(param);
				if (index != -1) {
					CandidateProfileParams paramWithData = candidateProfileParams.get(index);
					String trait = "";
					if (percent < 20) {
						trait = paramWithData.getLESS_THAN_TWENTY_PERCENT();
					} else if (percent >= 20 && percent < 50) {
						trait = paramWithData.getBETWEEN_TWENTY_AND_FIFTY();
					} else if (percent >= 50 && percent < 75) {
						trait = paramWithData.getBETWEEN_FIFTY_AND_SEVENTYFIVE();
					} else if (percent >= 75 && percent < 90) {
						trait = paramWithData.getBETWEEN_SEVENTYFIVE_AND_NINETY();
					} else if (percent > 90) {
						trait = paramWithData.getMORE_THAN_NINETY();
					}
					mapTrait.put(param, trait);
				}

			}
			List<UserTrait> traits = new ArrayList<>();
			for (CandidateProfileParams param : mapTrait.keySet()) {
				UserTrait trait = new UserTrait();
				String qual = param.getQualifier1();
				if (param.getQualifier2() != null && !param.getQualifier2().equals("NA")) {
					qual += "-" + param.getQualifier2();
				}
				if (param.getQualifier3() != null && !param.getQualifier3().equals("NA")) {
					qual += "-" + param.getQualifier3();
				}
				if (param.getQualifier4() != null && !param.getQualifier4().equals("NA")) {
					qual += "-" + param.getQualifier4();
				}
				if (param.getQualifier5() != null && !param.getQualifier5().equals("NA")) {
					qual += "-" + param.getQualifier5();
				}

				trait.setTrait(qual);
				trait.setDescription(mapTrait.get(param));
				traits.add(trait);
			}

			for (UserTrait trait : traits) {
				System.out.println("trait::: " + trait.getTrait() + "   ::::   " + trait.getDescription());
			}
			for (UserTrait trait : traits) {

				LearnersProfileParam lp = new LearnersProfileParam();
				String[] traitss = trait.getTrait().split("-");
				for (int i = 0; i < traitss.length; i++) {
					if (i == 0) {
						lp.setQualifier1(traitss[i].toUpperCase());
					}
					if (i == 1) {
						lp.setQualifier2(traitss[i].toUpperCase());
					}
					if (i == 2) {
						lp.setQualifier3(traitss[i].toUpperCase());
					}
					if (i == 3) {
						lp.setQualifier4(traitss[i].toUpperCase());
					}
					if (i == 4) {
						lp.setQualifier5(traitss[i].toUpperCase());
					}
				}
				lp.setQparamValue(trait.getDescription());
				lp.setCompanyId(user.getCompanyId());
				lp.setCompanyName(user.getCompanyName());
				lp.setUserEmail(user.getEmail());
				listParam.add(lp);

			}
		}
		profileService.saveOrUpdate(listParam);
		piechart1Service.saveOrUpdate(chart1s);
		piechart2Service.saveOrUpdate(chart2s);
		map1.put("msg", "Done");
//		User usr = userService.findByPrimaryKey(user, companyId);

		return map1;
	}

	@GetMapping("/user_profile_student_profile")
	public ModelAndView userProfile(@RequestParam("email") String email) {
		ModelAndView mav = new ModelAndView("userProfile");
		List<LearnersProfileParam> li = profileParamRepository.findByuserEmail(email);
		List<PieChart1> listPichart = pieChart1Repository.findByuserEmail(email);
		Set<String> set = new HashSet<String>();

		for (LearnersProfileParam param : li) {
			set.add(param.getQualifier1());
		}

		Map<String, Map<String, Map<String, Map<String, List<String>>>>> listQualifer = new HashMap<String, Map<String, Map<String, Map<String, List<String>>>>>();
		for (LearnersProfileParam param : li) {

			if (listQualifer.containsKey(param.getQualifier1())) {
				if (listQualifer.get(param.getQualifier1()).containsKey(param.getQualifier2())) {
					if (listQualifer.get(param.getQualifier1()).get(param.getQualifier2()).containsKey(param.getQualifier3())) {
						if (listQualifer.get(param.getQualifier1()).get(param.getQualifier2()).get(param.getQualifier3()).containsKey(param.getQualifier4())) {
							if (param.getQualifier5() == null) {
								continue;
							}
							listQualifer.get(param.getQualifier1()).get(param.getQualifier2()).get(param.getQualifier3()).get(param.getQualifier4()).add(param.getQualifier5());
							System.out.println(":::::::    " + listQualifer);

						} else {
							if (param.getQualifier4() == null) {
								continue;
							}
							List<String> listq = new ArrayList<>();
							if (!(param.getQualifier5() == null)) {
								listq.add(param.getQualifier5());
							}
							listQualifer.get(param.getQualifier1()).get(param.getQualifier2()).get(param.getQualifier3()).put(param.getQualifier4(), listq);
							System.out.println(":::::::    " + listQualifer);
						}
					} else {
						if (param.getQualifier3() == null) {
							continue;
						}
						List<String> listq = new ArrayList<>();
						if (!(param.getQualifier5() == null)) {
							listq.add(param.getQualifier5());
						}
						Map<String, List<String>> map2 = new HashMap<String, List<String>>();
						if (!(param.getQualifier4() == null)) {
							map2.put(param.getQualifier4(), listq);
						}
						if (!(map2 == null)) {
							listQualifer.get(param.getQualifier1()).get(param.getQualifier2()).put(param.getQualifier3(), map2);
							System.out.println(":::::::    " + listQualifer);
						}
					}
				} else {
					if (param.getQualifier2() == null) {
						continue;
					}
					List<String> listq = new ArrayList<>();
					if (!(param.getQualifier5() == null)) {
						listq.add(param.getQualifier5());
					}
					Map<String, List<String>> map2 = new HashMap<String, List<String>>();
					if (!(param.getQualifier4() == null)) {
						map2.put(param.getQualifier4(), listq);
					}
					Map<String, Map<String, List<String>>> map3 = new HashMap<String, Map<String, List<String>>>();
					if (!(param.getQualifier3() == null)) {
						map3.put(param.getQualifier3(), map2);
					}
					if (!(map3 == null)) {
						listQualifer.get(param.getQualifier1()).put(param.getQualifier2(), map3);
						System.out.println(":::::::    " + listQualifer);
					}
				}
			} else {
				List<String> listq = new ArrayList<>();
				if (!(param.getQualifier5() == null)) {
					listq.add(param.getQualifier5());
				}
				Map<String, List<String>> map2 = new HashMap<String, List<String>>();
				if (!(param.getQualifier4() == null)) {
					map2.put(param.getQualifier4(), listq);
				}
				Map<String, Map<String, List<String>>> map3 = new HashMap<>();
				if (!(param.getQualifier3() == null)) {
					map3.put(param.getQualifier3(), map2);
				}
				Map<String, Map<String, Map<String, List<String>>>> map4 = new HashMap<>();
				if (!(param.getQualifier2() == null)) {
					map4.put(param.getQualifier2(), map3);
				}
				if (!(map4 == null)) {
					listQualifer.put(param.getQualifier1(), map4);
				}
				System.out.println(":::::::    " + listQualifer);
			}
		}
		System.out.println(":::::::::::::::::::::::     " + listQualifer);
		mav.addObject("mapList", listQualifer);
		mav.addObject("pichart1", listPichart);
		mav.addObject("list", li);
		mav.addObject("email", email);
		return mav;
	}

	@GetMapping("/getChart1")
	@ResponseBody
	public Map<String, Object> getChart1(@RequestParam String email) {
		Map<String, Object> map = new HashMap<>();
		List<PieChart1> listPichart = pieChart1Repository.findByuserEmail(email);
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(listPichart);
		System.out.println("json::::::::::::::                       " + jsonCartList);
		map.put("chart", jsonCartList);
		map.put("email", email);
		return map;
	}

	@GetMapping("/getChart2")
	@ResponseBody
	public Map<String, Object> getChart2(@RequestParam String email, @RequestParam String qualifier2) {
		Map<String, Object> map = new HashMap<>();
		List<PieChart2> listPichart = pieChart2Repository.findByUserEmailAndQualifier2(email, qualifier2);
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(listPichart);
		System.out.println("json::::::::::::::                       " + jsonCartList);
		map.put("chart", jsonCartList);
		map.put("email", email);
		return map;
	}

	@GetMapping("getRecom")
	@ResponseBody
	public Map<String, String> getRecom(@RequestParam("param") String param, @RequestParam("email") String email, HttpServletRequest request) {

//		User user = (User) request.getSession().getAttribute("user");
		String[] paramArray = param.split("-");
		List<LearnersProfileParam> paramVal;
		Map<String, String> map = new HashMap<>();
//		String QValue = "";
		if (paramArray.length == 1) {
			paramVal = profileParamRepository.getQValue1(email, paramArray[0]);
			for (LearnersProfileParam li : paramVal) {
				if (li.getQualifier5() != null) {
					map.put(li.getQualifier5(), li.getQparamValue());
				} else if (li.getQualifier4() != null && li.getQualifier5() == null) {
					map.put(li.getQualifier4(), li.getQparamValue());
				} else if (li.getQualifier3() != null && li.getQualifier4() == null) {
					map.put(li.getQualifier3(), li.getQparamValue());
				} else if (li.getQualifier2() != null && li.getQualifier3() == null) {
					map.put(li.getQualifier2(), li.getQparamValue());
				} else if (li.getQualifier1() != null && li.getQualifier2() == null) {
					map.put(li.getQualifier1(), li.getQparamValue());
				}
			}
		} else if (paramArray.length == 2) {
			paramVal = profileParamRepository.getQValue2(email, paramArray[0], paramArray[1]);
			for (LearnersProfileParam li : paramVal) {
				if (li.getQualifier5() != null) {
					map.put(li.getQualifier5(), li.getQparamValue());
				} else if (li.getQualifier4() != null && li.getQualifier5() == null) {
					map.put(li.getQualifier4(), li.getQparamValue());
				} else if (li.getQualifier3() != null && li.getQualifier4() == null) {
					map.put(li.getQualifier3(), li.getQparamValue());
				} else if (li.getQualifier2() != null && li.getQualifier3() == null) {
					map.put(li.getQualifier2(), li.getQparamValue());
				}
			}
		} else if (paramArray.length == 3) {
			paramVal = profileParamRepository.getQValue3(email, paramArray[0], paramArray[1], paramArray[2]);
			for (LearnersProfileParam li : paramVal) {
				if (li.getQualifier5() != null) {
					map.put(li.getQualifier5(), li.getQparamValue());
				} else if (li.getQualifier4() != null && li.getQualifier5() == null) {
					map.put(li.getQualifier4(), li.getQparamValue());
				} else if (li.getQualifier3() != null && li.getQualifier4() == null) {
					map.put(li.getQualifier3(), li.getQparamValue());
				}
			}
		} else if (paramArray.length == 4) {
			paramVal = profileParamRepository.getQValue4(email, paramArray[0], paramArray[1], paramArray[2], paramArray[3]);
			for (LearnersProfileParam li : paramVal) {
				if (li.getQualifier5() != null) {
					map.put(li.getQualifier5(), li.getQparamValue());
				} else if (li.getQualifier4() != null && li.getQualifier5() == null) {
					map.put(li.getQualifier4(), li.getQparamValue());
				}
			}
		} else {
			paramVal = profileParamRepository.getQValue5(email, paramArray[0], paramArray[1], paramArray[2], paramArray[3], paramArray[4]);
			for (LearnersProfileParam li : paramVal) {
				if (li.getQualifier5() != null) {
					map.put(li.getQualifier5(), li.getQparamValue());
				}
			}
		}
		System.out.println("test: " + map);
		return map;
	}

}
