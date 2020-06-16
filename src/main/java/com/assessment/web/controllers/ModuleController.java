package com.assessment.web.controllers;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.Exceptions.AssessmentGenericException;
import com.assessment.common.CommonUtil;
import com.assessment.data.Module;
import com.assessment.data.ModuleItem;
import com.assessment.data.User;
import com.assessment.repositories.ModuleItemRepository;
import com.assessment.services.LicenseService;
import com.assessment.services.ModuleService;
import com.assessment.services.TestService;

@Controller
public class ModuleController {
	@Autowired
	ModuleService moduleService;

	@Autowired
	ModuleItemRepository moduleItemRep;

	@Autowired
	TestService testService;

	@Autowired
	LicenseService licenseService;

	@RequestMapping(value = "/modules", method = RequestMethod.GET)
	public ModelAndView modules(@RequestParam(name = "page", required = false) Integer pageNumber,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		ModelAndView mav = new ModelAndView("modules");

		if (pageNumber == null) {
			pageNumber = 0;
		}

		User user = (User) request.getSession().getAttribute("user");
		Page<Module> modules = moduleService.getModules(user.getCompanyId(),
				PageRequest.of(pageNumber, 15));
		mav.addObject("modules", modules.getContent());
		CommonUtil.setCommonAttributesOfPagination(modules, modelMap, pageNumber, "modules", null);
		return mav;
	}

	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public ModelAndView addModule(@RequestParam(name = "moduleId", required = false) Long moduleId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Module module = null;
		if (moduleId != null) {
			module = moduleService.getModuleById(moduleId);
//				Module mod = (Module) request.getSession().getAttribute("module");
//					if(mod == null){
//						module = moduleService.getModuleById(moduleId);
//					}
//					else{
//						module = mod;
//					}

		} else {
			module = new Module();

		}
		request.getSession().setAttribute("module", module);
		request.getSession().setAttribute("moduleItems", module.getItems());
		ModelAndView mav = new ModelAndView("module");
		User user = (User) request.getSession().getAttribute("user");
		List<String> licenses = licenseService.getLicensesInString(user.getCompanyId());
		mav.addObject("module", module);
		mav.addObject("licenses", licenses);
		return mav;
	}

	@RequestMapping(value = "/deleteModule", method = RequestMethod.GET)
	public ModelAndView deleteModule(@RequestParam(name = "moduleId", required = true) Long moduleId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			moduleService.deleteModule(moduleId);
		}
		ModelAndView mav = new ModelAndView("modules");

		Page<Module> modules = moduleService.getModules(user.getCompanyId(), PageRequest.of(0, 15));
		mav.addObject("modules", modules.getContent());
		CommonUtil.setCommonAttributesOfPagination(modules, modelMap, 0, "modules", null);
		mav.addObject("message", "Module deletion -  success");// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;
	}

	@RequestMapping(value = "/moduleitem", method = RequestMethod.POST)
	public ModelAndView addModuleitem(@ModelAttribute("module") Module module,
			@RequestParam(name = "moduleItemId", required = false) Long moduleItemId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		ModelAndView mav = new ModelAndView("moduleItem");
		mav.addObject("moduleItem", moduleItem);
		List<String> testNames = testService.getTestNames(user.getCompanyId());
		mav.addObject("testNames", testNames);
		request.getSession().setAttribute("moduleItem", moduleItem);
		return mav;
	}

	@RequestMapping(value = "/removemoduleitem", method = RequestMethod.POST)
	public ModelAndView removemoduleitem(@ModelAttribute("module") Module module,
			@RequestParam(name = "moduleItemId", required = true) Long moduleItemId,
			HttpServletRequest request, HttpServletResponse response) {
		Set<ModuleItem> items = (Set<ModuleItem>) request.getSession().getAttribute("moduleItems");
		for (ModuleItem item : items) {
			if (item.getId().equals(moduleItemId)) {
				items.remove(item);
				break;
			}
		}
		request.getSession().setAttribute("moduleItems", items);
		// Module module = (Module)request.getSession().getAttribute("module");
		module.setItems(items);
		request.getSession().setAttribute("module", module);
		ModelAndView mav = new ModelAndView("module");
		User user = (User) request.getSession().getAttribute("user");
		List<String> licenses = licenseService.getLicensesInString(user.getCompanyId());
		mav.addObject("module", module);
		mav.addObject("licenses", licenses);
		mav.addObject("message", "Module Item deleted");// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;

	}

	@RequestMapping(value = "/saveModuleitem", method = RequestMethod.POST)
	public ModelAndView saveModuleitem(@ModelAttribute("moduleItem") ModuleItem moduleItem,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		ModelAndView mav = new ModelAndView("module");
		List<String> licenses = licenseService.getLicensesInString(user.getCompanyId());
		mav.addObject("module", module);
		mav.addObject("licenses", licenses);
		request.getSession().setAttribute("module", module);
		request.getSession().setAttribute("moduleItems", module.getItems());
		mav.addObject("message", "Module Item Saved");// later put it as label
		mav.addObject("msgtype", "Information");
		return mav;
	}

	@RequestMapping(value = "/saveModule", method = RequestMethod.POST)
	public ModelAndView saveModuleitem(@ModelAttribute("module") Module module, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
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
				List<String> licenses = licenseService
						.getLicensesInString(user.getCompanyId());
				mav.addObject("module", module);
				mav.addObject("licenses", licenses);
				mav.addObject("message",
						"Module name already exists. Use a different name");// later
											// put
											// it
											// as
											// label
				mav.addObject("msgtype", "Information");
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("modules");
		mav.addObject("message", "Module - " + module.getModuleName() + " saved");// later put it as label
		mav.addObject("msgtype", "Information");

		Page<Module> modules = moduleService.getModules(user.getCompanyId(), PageRequest.of(0, 15));
		mav.addObject("modules", modules.getContent());
		CommonUtil.setCommonAttributesOfPagination(modules, modelMap, 0, "modules", null);
		return mav;
	}

}
