package com.assessment.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.assessment.common.CompileData;
import com.assessment.common.CompileOutput;
import com.assessment.data.Company;
import com.assessment.data.Test;
import com.assessment.data.TestProgram;
import com.assessment.services.CompanyService;
import com.assessment.services.SQLCodingAutomationService;
import com.assessment.services.TestProgramService;
import com.assessment.services.impl.CompilerService;
import com.assessment.web.dto.LanguageDto;

@Controller
public class CompilerController {
	@Autowired
	CompilerService compilerService;
	
	@Autowired
	SQLCodingAutomationService automationService;
	
	@Autowired
	TestProgramService programService;
	
	@Autowired
	CompanyService companyService;
	
	Logger logger = LoggerFactory.getLogger(CompilerController.class);
	
	private String evaluateMySQLCoding(String query){
		 List results;
		try {
			results = automationService.fireDirectQuery(query);
		} 
		catch(PersistenceException e){
			//System.out.println(e.getCause().getMessage());
			try {
				SQLGrammarException e1 = (SQLGrammarException)e.getCause();
				//System.out.println("111 "+e1.getSQLException().getMessage());
			//	System.out.println("222 " +e1.getSQLException().getLocalizedMessage());
				return e1.getSQLException().getMessage();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "System Problem with Docker infra "+e1.getMessage();//tp message
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("Query Problem"+e.getMessage());
			return "Query Problem"+e.getMessage();
		}
		 String ret = "";
		 
		 List<String> ress = new ArrayList();
		 if(results != null && results.size() > 0){
			 if(results.get(0) instanceof String){
				 ress = (List<String>) results;
				 
				 for(String s : ress){
					 ret += s + "   \n";
				 }
				 return ret;
			 }
			 else if(results.get(0) instanceof String[]){
				 //System.out.println("multiple results");
			List<String[]> op = (List<String[]> ) results;
				 for(String[] row : op){
					 for(Object col : row){
						 ret += col +"    ";
					 }
					 ret += "\n";
				 }
			 }
			 else if(results.get(0) instanceof Object[]){
				 //System.out.println("multiple results 111");
			List<Object[]> op =  results;
				 for(Object[] row : op){
					 for(Object col : row){
						 ret += col.toString() +"    ";
					 }
					 ret += "\n";
				 }
			 }
			 else{
				// System.out.println("multiple results but no where");
				// System.out.println(results.get(0).getClass());
			 }
		 }
		return ret;	 
		 
	 }
	
	
	@RequestMapping(value = "/compile", method = RequestMethod.POST , consumes="application/json")
	  public @ResponseBody String compile(HttpServletRequest request, HttpServletResponse response,@RequestBody CompileData data) {
		try {
//			data = URLDecoder.decode(data);
//			ObjectMapper mapper = new ObjectMapper();
//			CompileData dat = mapper.readValue(data, CompileData.class);
			if(data.getLanguage().equals("13")){
				return evaluateMySQLCoding(data.getCode());
			}
			
			CompileOutput op=  compilerService.compile(data);
			return op.getOutput() +"\n\n"+op.getErrors();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Compiler Services not available. Contact your Test Administrator";
		} 
	  }
	
	//compileAndRunSystemTest
	@RequestMapping(value = "/compileAndRunSystemTest", method = RequestMethod.POST , consumes="application/json" )
	  public @ResponseBody String compileAndRunSystemTest(HttpServletRequest request, HttpServletResponse response,@RequestBody CompileData data) {
		try {
			
			CompileOutput op=  compilerService.compile(data);
				if(op.getErrors() != null && op.getErrors().trim().length() > 0){
					return op.getErrors() +"\n"+op.getOutput();
				}
			return op.getOutput().replaceAll("\n", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Compiler Services not available. Contact your Test Administrator";
		} 
	  }
	
	@RequestMapping(value = "/savePracticeCode", method = RequestMethod.POST )
	  public ModelAndView savePracticeCode(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("program") TestProgram program ) {
		
		String code  = program.getCode();
		code = code.replaceAll("\r", "");
		String rep = "\\\\n";
		 String rept = "\\\\t";
		code = code.replaceAll("\n", rep);
		code = code.replaceAll("\t", rept);
		program.setCode(code);
		if(program.getId() == null){
			String name = "";
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
			String dt = dateFormat.format(date);
			name+= program.getLang()+"-"+dt;
			program.setName(name);
			program.setCreateDate(date);
			programService.add(program);
		}
		else{
			programService.update(program);
		}
		
		return precticeTest(program.getLangid(), "Your Code - "+program.getName()+" is saved successfully.", program.getCompanyId(), program.getUser(), program.getId());
	 }

	
	
	@RequestMapping(value = "/yakshacode", method = RequestMethod.GET )
	  public ModelAndView precticeTest(@RequestParam(value="langid", required=false) String langid, @RequestParam(value="message", required=false) String message, @RequestParam(value="companyId") String companyId, @RequestParam(value="user") String user, @RequestParam(value="testid",required=false) Long testid) {
		Company company = companyService.findByCompanyId(companyId);
			if(company == null || user == null){
				ModelAndView mav = new ModelAndView("error");
				mav.addObject("message", "This looks to be an integration problem between LMs & Yaksha. Yaksha does not have sufficient data to identify you. Please contact LMS Admin");
				return mav;
				
			}
			
			
		
		Map<String, LanguageDto> map = new HashMap<>();
		LanguageDto  dto1 = new LanguageDto("10", "C#");
		map.put("10", dto1);
		LanguageDto  dto2 = new LanguageDto("7", "C/C++");
		map.put("7", dto2);
		LanguageDto  dto3 = new LanguageDto("2", "Clojure");
		map.put("2", dto3);
		LanguageDto  dto4 = new LanguageDto("8", "Java");
		map.put("8", dto4);
		LanguageDto  dto5 = new LanguageDto("6", "Go");
		map.put("6", dto5);
		LanguageDto  dto6 = new LanguageDto("4", "Plain JavaScript");
		map.put("4", dto6);
		LanguageDto  dto7 = new LanguageDto("3", "PHP");
		map.put("3", dto7);
		LanguageDto  dto8 = new LanguageDto("0", "Python");
		map.put("0", dto8);
		LanguageDto  dto9 = new LanguageDto("1", "Ruby");//
		map.put("1", dto9);
//		LanguageDto  dto10 = new LanguageDto("5", "Scala");//
//		map.put("5", dto10);
		LanguageDto  dto11 = new LanguageDto("9", "VB.NET");//
		map.put("9", dto11);
		LanguageDto  dto12 = new LanguageDto("11", "Bash");
		map.put("11", dto12);
		LanguageDto  dto13 = new LanguageDto("12", "Objective-C");
		map.put("12", dto13);
		LanguageDto  dto14 = new LanguageDto("14", "Perl");
		map.put("14", dto14);
		LanguageDto  dto15 = new LanguageDto("15", "Rust");
		map.put("15", dto15);
		
		List<LanguageDto> dtos = new ArrayList<LanguageDto>();
		dtos.add(dto1);
		dtos.add(dto2);
		dtos.add(dto3);
		dtos.add(dto4);
		dtos.add(dto5);
		dtos.add(dto6);
		dtos.add(dto7);
		dtos.add(dto8);
		dtos.add(dto9);
		//dtos.add(dto10);
		dtos.add(dto11);
		dtos.add(dto12);
		dtos.add(dto13);
		dtos.add(dto14);
		dtos.add(dto15);
		
		TestProgram program;
		
		
		if(testid == null){
			program = new TestProgram();
				if(langid != null){
					LanguageDto dto = map.get(langid);
					program.setLangid(dto.getLangid());
					program.setLang(dto.getLang());
				}
				else{
					program.setLangid("8");
					program.setLang("Java");
					langid = "8";
				}
			
			program.setCompanyName(company.getCompanyName());
			program.setCompanyId(company.getCompanyId());
			program.setUser(user);
		}
		else{
			program = programService.getById(testid);
				if(langid ==null){
					langid = program.getLangid();
				}
				else if(!langid.equals(program.getLangid())){
				return precticeTest("8", "You can not change language of a saved program. Create a new one", companyId, user, null);
				}
		}
		
		
		
		
		
		
		ModelAndView mv=new ModelAndView("practice");
		LanguageDto current = map.get(langid);
		mv.addObject("current", current);
		
		String code  = program.getCode();
		code = code.replaceAll("\r", "");
		String rep = "\\\\n";
		 String rept = "\\\\t";
		code = code.replaceAll("\n", rep);
		code = code.replaceAll("\t", rept);
		program.setCode(code);
		
		mv.addObject("program", program);
		mv.addObject("list", dtos);
		
		List<TestProgram> programs = programService.getTestProgramsForUser(companyId, user);
		//System.out.println("testid is "+testid);
		//logger.info("testid is "+testid);
			for(TestProgram program2 : programs){
				//System.out.println("testid is "+testid+" program2.getId() "+program2.getId());
				//logger.info("testid is "+testid+" program2.getId() "+program2.getId());
				if(testid != null && program2.getId().equals(testid)){
					//System.out.println("true");
					//logger.info("true");
					program2.setStyle("font-size:115%;color:#990099");
				}
			}
		mv.addObject("programs", programs);
		
		if(message != null){
			mv.addObject("message", message);// later put it as label
			mv.addObject("msgtype", "Information");
		}
		//String code="Java Code";
		
		return mv;
		
	}
}
