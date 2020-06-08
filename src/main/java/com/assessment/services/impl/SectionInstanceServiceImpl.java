package com.assessment.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.Exceptions.AssessmentGenericException;
import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.QuestionType;
import com.assessment.data.Section;
import com.assessment.data.SectionInstance;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.SectionInstanceRepository;
import com.assessment.repositories.SectionRepository;
import com.assessment.services.QuestionMapperInstanceService;
import com.assessment.services.SectionInstanceService;
import com.assessment.services.SectionService;

@Service
@Transactional
public class SectionInstanceServiceImpl implements SectionInstanceService{
	
	Logger logger = LoggerFactory.getLogger(SectionInstanceServiceImpl.class);
	
	@Autowired
	SectionInstanceRepository sectionInstanceRepository;
	
	@Autowired
	SectionService sectionService;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	QuestionMapperInstanceRepository questionMapperInstanceRepository;
	
	@Autowired
	QuestionMapperInstanceService mapperInstanceService;
	
	@Autowired
	EntityManager entityManager;
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	private void validateMandatoryFields(SectionInstance sectionInstance) {
		Set<ConstraintViolation<SectionInstance>> violations = validator.validate(sectionInstance);
		if(violations.size() > 0){
			throw new AssessmentGenericException("NOT_SUFFICIENT_PARAMS");
		}
		
		
	}
	
	private void validateMandatoryFields(QuestionMapperInstance questionMapperInstance) {
		Set<ConstraintViolation<QuestionMapperInstance>> violations = validator.validate(questionMapperInstance);
		if(violations.size() > 0){
			throw new AssessmentGenericException("NOT_SUFFICIENT_PARAMS");
		}
		
		
	}

	@Override
	public void saveSection(SectionInstance sectionInstance, List<QuestionMapperInstance> questionMapperInstances) {
		// TODO Auto-generated method stub
	//	System.out.println("in savesection");
		validateMandatoryFields(sectionInstance);
		List<SectionInstance> pastInstances = getSectionInstances(sectionInstance.getSectionName(), sectionInstance.getCompanyId(), sectionInstance.getUser());
		Section section = sectionRepository.findByPrimaryKey(sectionInstance.getTestName(), sectionInstance.getSectionName(), sectionInstance.getCompanyId());
		int sectionTime = section.getSectionTimeInMinutes() == null?30:section.getSectionTimeInMinutes();
		int timeYet = 0;
		for(SectionInstance ins : pastInstances) {
			Long startTime = ins.getStartTime();
			Long endTime = ins.getEndTime();
			int mins = (int) ((endTime - startTime)/(1000 * 60));
			timeYet += mins;
		}
		
		if(timeYet >= sectionTime) {
			//enable later
			//throw new AssessmentGenericException("SECTION_TIME_LIMIT_CROSSED");
		}
		
		List<QuestionMapperInstance> createAnswers = new ArrayList<QuestionMapperInstance>();
		List<QuestionMapperInstance> updateAnswers = new ArrayList<QuestionMapperInstance>();
		
		for(QuestionMapperInstance questionMapperInstance : questionMapperInstances) {
			//System.out.println("in savequestions");
			validateMandatoryFields(questionMapperInstance);
			//System.out.println("questionMapperInstance.getQuestionText() "+questionMapperInstance.getQuestionText());
			//System.out.println("questionMapperInstance.getTestName() "+questionMapperInstance.getTestName());
			//System.out.println("questionMapperInstance.getSectionName() "+questionMapperInstance.getSectionName());
		//	System.out.println("questionMapperInstance.getUser() "+questionMapperInstance.getUser());
		//	System.out.println("questionMapperInstance.getCompanyId() "+questionMapperInstance.getCompanyId());;
			
			QuestionMapperInstance questionMapperInstance2 = null;
			questionMapperInstance2 = mapperInstanceService.removeDublicateAndGetInstance(questionMapperInstance.getQuestionText(), questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
//			try {
//				questionMapperInstance2 = 	questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUser(questionMapperInstance.getQuestionText(), questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
//			} catch (javax.persistence.NonUniqueResultException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				logger.error("duplicate anss for "+questionMapperInstance.getQuestionText(), questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
//				List<QuestionMapperInstance> qms = questionMapperInstanceRepository.findDuplicateQuestionMapperInstanceForUser(questionMapperInstance.getQuestionText(), questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
//				for(QuestionMapperInstance q : qms){
//					questionMapperInstanceRepository.delete(q);
//				}
//			}
			
			if(questionMapperInstance2 != null) {
				//update answer
				questionMapperInstance2.setUserChoices(questionMapperInstance.getUserChoices());
				questionMapperInstance2.setUpdateDate(new Date());
				checkAnswer(questionMapperInstance2);
				//questionMapperInstanceRepository.save(questionMapperInstance2);
				updateAnswers.add(questionMapperInstance2);
			}
			else {
				//new answer
				questionMapperInstance.setCreateDate(new Date());
				checkAnswer(questionMapperInstance);
				questionMapperInstance.setQuestionText(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
				//questionMapperInstanceRepository.save(questionMapperInstance);
				createAnswers.add(questionMapperInstance);
			}
			
		}
		saveAnswersInBatch(createAnswers);
		updateAnswersInBatch(updateAnswers);
		sectionInstanceRepository.save(sectionInstance);
	}
	
	public void saveAnswersInBatch(List<QuestionMapperInstance> answers){
		for (int i = 0; i < answers.size(); i++) {
	        if (i > 0 && i % 20 == 0) {
	            entityManager.flush();
	            entityManager.clear();
	        }
	        QuestionMapperInstance answer = answers.get(i);
	        entityManager.persist(answer);
	    }
	}
	
	public void updateAnswersInBatch(List<QuestionMapperInstance> answers){
		for (int i = 0; i < answers.size(); i++) {
	        if (i > 0 && i % 20 == 0) {
	            entityManager.flush();
	            entityManager.clear();
	        }
	        QuestionMapperInstance answer = answers.get(i);
	        entityManager.unwrap(Session.class).update(answer);
	    }
	}
	
	private void checkAnswer(QuestionMapperInstance instance) {
		/**Added if condition code for coding question type answer verification
		 * 
		 */
		if(instance.getQuestionMapper().getQuestion().getQuestionType() != null && instance.getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.CODING.getType())){
			String op = instance.getCodingOuputBySystemTestCase();
			op = (op == null)?"":op.trim();
			//System.out.println(" saving section check answer op "+op+" instance.getQuestionMapper().getQuestion().getHiddenOutputNegative() "+instance.getQuestionMapper().getQuestion().getHiddenOutputNegative());
			if(instance.getQuestionMapper().getQuestion().getHiddenOutputNegative().equalsIgnoreCase(op)){
				//System.out.println("in check ansewr "+true);
				instance.setCorrect(true);
				
			}
			else{
				//System.out.println("in check ansewr "+false);
				instance.setCorrect(false);
			}
			instance.setAnswered(true);
			return;
		}
		/**End Added if condition code for coding question type answer verification
		 * 
		 */
		
		if(instance.getQuestionMapper().getQuestion().getQuestionType() != null && instance.getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.FILL_BLANKS_MCQ.getType())){
			String systemChoices = instance.getQuestionMapper().getQuestion().getFillInBlankOptions();
			String userEnteredBlanks = instance.getUserChoices();
			
			if(userEnteredBlanks== null || userEnteredBlanks.trim().length() == 0){
				instance.setCorrect(false);
				instance.setAnswered(false);
				return;
			}
			instance.setAnswered(true);
			
			if(systemChoices != null && userEnteredBlanks != null && systemChoices.equalsIgnoreCase(userEnteredBlanks)){
				instance.setCorrect(true);
				
			}
			else{
				instance.setCorrect(false);
			}
			return;
		}
		
		if(instance.getQuestionMapper().getQuestion().getQuestionType() != null && instance.getQuestionMapper().getQuestion().getQuestionType().getType().equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())){
			instance = evaluateMTF(instance);
			return;
		}
		
		/**
		 * Check if right
		 */
		if(instance.getAnswered()) {
			String rightChoices = instance.getQuestionMapper().getQuestion().getRightChoices();
			String rt[] = rightChoices.split("-");
			String userChoices[] = instance.getUserChoices().split("-");
			instance.setCorrect(Arrays.equals(rt, userChoices));
		}
	}
	
	private QuestionMapperInstance evaluateMTF(QuestionMapperInstance questionMapperInstance){
		 Map<String, String> correctCombinations = new HashMap<String, String>();
		 String left1 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchLeft1();
		 String right1 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchRight1();
		 correctCombinations.put(left1, right1);
		 
		 String left2 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchLeft2();
		 String right2 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchRight2();
		 correctCombinations.put(left2, right2);
		 
		 String left3 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchLeft3();
		 String right3 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchRight3();
		 if(left3 != null && right3 != null && left3.trim().length()!= 0 && right3.trim().length() != 0){
			 correctCombinations.put(left3, right3);
		 }
		 
		 
		 String left4 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchLeft4();
		 String right4 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchRight4();
		 if(left4 != null && right4 != null && left4.trim().length()!= 0 && right4.trim().length() != 0){
			 correctCombinations.put(left4, right4);
		 }
		 
		 String left5 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchLeft5();
		 String right5 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchRight5();
		 if(left5 != null && right5 != null && left5.trim().length()!= 0 && right5.trim().length() != 0){
			 correctCombinations.put(left5, right5);
		 }
		 
		 String left6 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchLeft6();
		 String right6 = questionMapperInstance.getQuestionMapper().getQuestion().getMatchRight6();
		 if(left6 != null && right6 != null && left6.trim().length()!= 0 && right6.trim().length() != 0){
			 correctCombinations.put(left6, right6);
		 }
		 
		 String rightA = questionMapperInstance.getMatchRight1();
		 if(rightA != null){
			 String expected = correctCombinations.get(left1);
			 if(expected == null){
				 questionMapperInstance.setAnswered(false);
				 questionMapperInstance.setCorrect(false);
				 return questionMapperInstance;
			 }
			 else if(!expected.equals(rightA)){
				 questionMapperInstance.setAnswered(true);
				 questionMapperInstance.setCorrect(false);
				 return questionMapperInstance;
			 }
		 }
		 
		 rightA = questionMapperInstance.getMatchRight2();
		
		 if(rightA != null){
			 String expected = correctCombinations.get(left2);
			 if(expected == null){
				 questionMapperInstance.setAnswered(false);
				 questionMapperInstance.setCorrect(false);
				 return questionMapperInstance;
			 }
			 else if(!expected.equals(rightA)){
				 questionMapperInstance.setAnswered(true);
				 questionMapperInstance.setCorrect(false);
				 return questionMapperInstance;
			 }
		 }
		 
		 rightA = questionMapperInstance.getMatchRight3();
		
		 if(rightA != null){
			 if(left3 != null){
				 String expected = correctCombinations.get(left3);
				 if(expected == null){
					 questionMapperInstance.setAnswered(false);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
				 else if(!expected.equals(rightA)){
					 questionMapperInstance.setAnswered(true);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
			 }
			 
		 }
		 
		 rightA = questionMapperInstance.getMatchRight4();
		
		 if(rightA != null){
			 if(left4 != null){
				 String expected = correctCombinations.get(left4);
				 if(expected == null){
					 questionMapperInstance.setAnswered(false);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
				 else if(!expected.equals(rightA)){
					 questionMapperInstance.setAnswered(true);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
			 }
			 
		 }
		 
		 rightA = questionMapperInstance.getMatchRight5();
		
		 if(rightA != null){
			 if(left5 != null){
				 String expected = correctCombinations.get(left5);
				 if(expected == null){
					 questionMapperInstance.setAnswered(false);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
				 else if(!expected.equals(rightA)){
					 questionMapperInstance.setAnswered(true);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
			 }
			 
		 }
		 
		 rightA = questionMapperInstance.getMatchRight6();
		 
		 if(rightA != null){
			 if(left6 != null){
				 String expected = correctCombinations.get(left6);
				 if(expected == null){
					 questionMapperInstance.setAnswered(false);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
				 else if(!expected.equals(rightA)){
					 questionMapperInstance.setAnswered(true);
					 questionMapperInstance.setCorrect(false);
					 return questionMapperInstance;
				 }
			 }
			 
		 }
		 
		 questionMapperInstance.setAnswered(true);
		 questionMapperInstance.setCorrect(true);
		 return questionMapperInstance;
	}

	@Override
	public List<SectionInstance> getSectionInstances(String sectionName, String companyId, String user) {
		// TODO Auto-generated method stub
		return sectionInstanceRepository.findSectionForUser(sectionName, user, companyId);
	}

	@Override
	public QuestionMapperInstance saveOrUpdateAnswer(QuestionMapperInstance questionMapperInstance) {
		// TODO Auto-generated method stub
		QuestionMapperInstance questionMapperInstance2 = null;
		String qText = questionMapperInstance.getQuestionText();
		if(qText == null){
			qText = questionMapperInstance.getQuestionMapper()==null?qText:questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText();
		}
		questionMapperInstance2 = mapperInstanceService.removeDublicateAndGetInstance(qText, questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
		if(questionMapperInstance2 != null) {
			//update answer
			questionMapperInstance2.setUserChoices(questionMapperInstance.getUserChoices());
			String type = questionMapperInstance2.getQuestionMapper().getQuestion().getQuestionType().getType();
			/**
			 * 
			 */
			if(type.equals(QuestionType.CODING.getType()) || type.equals(QuestionType.MATCH_FOLLOWING_MCQ.getType()) || type.equals(QuestionType.FILL_BLANKS_MCQ.getType())){
				Mapper mapper = new DozerBeanMapper();
				Long id = questionMapperInstance2.getId();
				mapper.map(questionMapperInstance, questionMapperInstance2);
				questionMapperInstance2.setId(id);
			}
			questionMapperInstance2.setUpdateDate(new Date());
			checkAnswer(questionMapperInstance2);
			return questionMapperInstanceRepository.save(questionMapperInstance2);
		}
		else {
			//new answer
			questionMapperInstance.setCreateDate(new Date());
			checkAnswer(questionMapperInstance);
			questionMapperInstance.setQuestionText(questionMapperInstance.getQuestionMapper().getQuestion().getQuestionText());
			return questionMapperInstanceRepository.save(questionMapperInstance);
		}
	}

	@Override
	public void saveSectionOnly(SectionInstance sectionInstance) {
		// TODO Auto-generated method stub
		validateMandatoryFields(sectionInstance);
		List<SectionInstance> pastInstances = getSectionInstances(sectionInstance.getSectionName(), sectionInstance.getCompanyId(), sectionInstance.getUser());
		/**
		 * No need for fetching Section below as we don't do anything with it
		 */
		//Section section = sectionRepository.findByPrimaryKey(sectionInstance.getTestName(), sectionInstance.getSectionName(), sectionInstance.getCompanyId());
		//int sectionTime = section.getSectionTimeInMinutes() == null?30:section.getSectionTimeInMinutes();
		int timeYet = 0;
		for(SectionInstance ins : pastInstances) {
			Long startTime = ins.getStartTime();
			Long endTime = ins.getEndTime();
			int mins = (int) ((endTime - startTime)/(1000 * 60));
			timeYet += mins;
		}
		
		//if(timeYet >= sectionTime) {
			//enable later
			//throw new AssessmentGenericException("SECTION_TIME_LIMIT_CROSSED");
		//}
		sectionInstance.setTotalTimeTaken(timeYet);
		sectionInstanceRepository.save(sectionInstance);
	}

	@Override
	public void addOnlyIfAnswersNotPresent(SectionInstance sectionInstance,List<QuestionMapperInstance> instances) {
		// TODO Auto-generated method stub
		List<SectionInstance> pastInstances = getSectionInstances(sectionInstance.getSectionName(), sectionInstance.getCompanyId(), sectionInstance.getUser());
		/**
		 * No need for fetching Section below as we don't do anything with it
		 */
		//Section section = sectionRepository.findByPrimaryKey(sectionInstance.getTestName(), sectionInstance.getSectionName(), sectionInstance.getCompanyId());
		//int sectionTime = section.getSectionTimeInMinutes() == null?30:section.getSectionTimeInMinutes();
		int timeYet = 0;
		for(SectionInstance ins : pastInstances) {
			Long startTime = ins.getStartTime();
			Long endTime = ins.getEndTime();
			int mins = (int) ((endTime - startTime)/(1000 * 60));
			timeYet += mins;
		}
		
		//if(timeYet >= sectionTime) {
			//enable later
			//throw new AssessmentGenericException("SECTION_TIME_LIMIT_CROSSED");
		//}
		sectionInstance.setTotalTimeTaken(timeYet);
		sectionInstanceRepository.save(sectionInstance);
		
		
		List<QuestionMapperInstance> createAnswers = new ArrayList<QuestionMapperInstance>();
		for(QuestionMapperInstance questionMapperInstance : instances){
			validateMandatoryFields(questionMapperInstance);
			QuestionMapperInstance questionMapperInstance2 = null;
			List<QuestionMapperInstance> answers = questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUserSet(questionMapperInstance.getQuestionText(), questionMapperInstance.getTestName(), questionMapperInstance.getSectionName(), questionMapperInstance.getUser(), questionMapperInstance.getCompanyId());
			if(answers == null || answers.size() == 0){
				questionMapperInstance.setCreateDate(new Date());
				checkAnswer(questionMapperInstance);
				createAnswers.add(questionMapperInstance);
			}
		}
		saveAnswersInBatch(createAnswers);	
		
	}

}
