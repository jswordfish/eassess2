package com.assessment.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.QuestionMapperInstance;
import com.assessment.data.QuestionType;
import com.assessment.repositories.QuestionMapperInstanceRepository;
import com.assessment.repositories.QuestionMapperRepository;
import com.assessment.services.QuestionMapperInstanceService;

@Service
@Transactional
public class QuestionMapperInstanceServiceImpl implements
		QuestionMapperInstanceService {

	@Autowired
	QuestionMapperInstanceRepository questionMapperInstanceRepository;
	
	Logger logger = LoggerFactory.getLogger(QuestionMapperInstanceServiceImpl.class);
	
	@Autowired
	EntityManagerFactory emf;
	
	public List<QuestionMapperInstance> getInstances(String qualifier1, String companyId){
		EntityManager entityManager = emf.createEntityManager();
		Query q = entityManager.createNativeQuery("select * from QuestionMapperInstance questionma0_  join QuestionMapper questionma1_  join Question question2_ where questionma0_.questionMapper_id=questionma1_.id and questionma1_.question_id=question2_.id and question2_.qualifier1=:qualifier1 AND question2_.companyId=:companyId", QuestionMapperInstance.class);
		q.setParameter("qualifier1", qualifier1);
		q.setParameter("companyId", companyId);
		List<QuestionMapperInstance> list = q.getResultList();
		
		
		
		return list;
//		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<QuestionMapperInstance> query = builder.createQuery(QuestionMapperInstance.class);
//		Root<QuestionMapperInstance> root = query.from(QuestionMapperInstance.class);
//		Predicate where = builder.conjunction();
//		where = builder.equal(root.get("questionMapper").get("question").get("qualifier1"), qualifier1);
//		query = query.where(where);
//		
//		Query queryObj = entityManager.createQuery(query);
//		String wry = queryObj.toString();
//		System.out.println("wry "+wry);
//		List<QuestionMapperInstance> list = queryObj.getResultList();
//		return list;
	}
	
	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String companyId){
		EntityManager entityManager = emf.createEntityManager();
		Query q = entityManager.createNativeQuery("select * from QuestionMapperInstance questionma0_  join QuestionMapper questionma1_  join Question question2_ where questionma0_.questionMapper_id=questionma1_.id and questionma1_.question_id=question2_.id and question2_.qualifier1=:qualifier1 AND question2_.companyId=:companyId AND question2_.qualifier2=:qualifier2", QuestionMapperInstance.class);
		q.setParameter("qualifier1", qualifier1);
		q.setParameter("qualifier2", qualifier2);
		q.setParameter("companyId", companyId);
		List<QuestionMapperInstance> list = q.getResultList();
		return list;

	}

	@Override
	public QuestionMapperInstance findUniqueQuestionMapperInstanceForUser(
			String questionText, String testName, String sectionName,
			String user, String companyId) {
		return questionMapperInstanceRepository
				.findUniqueQuestionMapperInstanceForUser(questionText,
						testName, sectionName, user, companyId);
	}

	@Override
	public List<QuestionMapperInstance> findQuestionMapperInstancesForUserForTest(
			String testName, String user, String companyId) {
		return questionMapperInstanceRepository
				.findQuestionMapperInstancesForUserForTest(testName, user,
						companyId);
	}

	@Override
	public boolean canEditTest(String sectionName, String testName, String companyId) {
		// TODO Auto-generated method stub
		List<QuestionMapperInstance> list = questionMapperInstanceRepository.findQuestionMapperInstancesForTestAndSection(sectionName, testName, companyId);
		return list.size() > 0 ? false:true;
	}

	@Override
	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String qualifier3,
			String companyId) {
		EntityManager entityManager = emf.createEntityManager();
		Query q = entityManager.createNativeQuery("select * from QuestionMapperInstance questionma0_  join QuestionMapper questionma1_  join Question question2_ where questionma0_.questionMapper_id=questionma1_.id and questionma1_.question_id=question2_.id and question2_.qualifier1=:qualifier1 AND question2_.companyId=:companyId AND question2_.qualifier2=:qualifier2 AND question2_.qualifier3=:qualifier3", QuestionMapperInstance.class);
		q.setParameter("qualifier1", qualifier1);
		q.setParameter("qualifier2", qualifier2);
		q.setParameter("qualifier3", qualifier3);
		q.setParameter("companyId", companyId);
		List<QuestionMapperInstance> list = q.getResultList();
		return list;
	}

	@Override
	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String qualifier3,
			String qualifier4, String companyId) {
		EntityManager entityManager = emf.createEntityManager();
		Query q = entityManager.createNativeQuery("select * from QuestionMapperInstance questionma0_  join QuestionMapper questionma1_  join Question question2_ where questionma0_.questionMapper_id=questionma1_.id and questionma1_.question_id=question2_.id and question2_.qualifier1=:qualifier1 AND question2_.companyId=:companyId AND question2_.qualifier2=:qualifier2 AND question2_.qualifier3=:qualifier3 AND question2_.qualifier4=:qualifier4", QuestionMapperInstance.class);
		q.setParameter("qualifier1", qualifier1);
		q.setParameter("qualifier2", qualifier2);
		q.setParameter("qualifier3", qualifier3);
		q.setParameter("qualifier4", qualifier4);
		q.setParameter("companyId", companyId);
		List<QuestionMapperInstance> list = q.getResultList();
		return list;
	}

	@Override
	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String qualifier3,
			String qualifier4, String qualifier5, String companyId) {
		EntityManager entityManager = emf.createEntityManager();
		Query q = entityManager.createNativeQuery("select * from QuestionMapperInstance questionma0_  join QuestionMapper questionma1_  join Question question2_ where questionma0_.questionMapper_id=questionma1_.id and questionma1_.question_id=question2_.id and question2_.qualifier1=:qualifier1 AND question2_.companyId=:companyId AND question2_.qualifier2=:qualifier2 AND question2_.qualifier3=:qualifier3 AND question2_.qualifier4=:qualifier4 AND question2_.qualifier5=:qualifier5", QuestionMapperInstance.class);
		q.setParameter("qualifier1", qualifier1);
		q.setParameter("qualifier2", qualifier2);
		q.setParameter("qualifier3", qualifier3);
		q.setParameter("qualifier4", qualifier4);
		q.setParameter("qualifier5", qualifier5);
		q.setParameter("companyId", companyId);
		List<QuestionMapperInstance> list = q.getResultList();
		return list;
	}

	

	@Override
	public List<QuestionMapperInstance> getInstancesOR(String qualifier, String companyId) {
		EntityManager entityManager = emf.createEntityManager();
		Query q = entityManager.createNativeQuery("select * from QuestionMapperInstance questionma0_  join QuestionMapper questionma1_  join Question question2_ where questionma0_.questionMapper_id=questionma1_.id and questionma1_.question_id=question2_.id  AND question2_.companyId=:companyId AND ( question2_.qualifier1=:qualifier OR question2_.qualifier2=:qualifier OR question2_.qualifier3=:qualifier OR question2_.qualifier4=:qualifier OR question2_.qualifier5=:qualifier) ORDER BY questionma0_.createDate desc", QuestionMapperInstance.class);
		q.setParameter("qualifier", qualifier);
		q.setParameter("companyId", companyId);
		List<QuestionMapperInstance> list = q.getResultList();
		return list;
	}

	@Override
	public List<QuestionMapperInstance> findFullStackQuestionMapperInstancesForJava(String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findFullStackQuestionMapperInstancesForJava(companyId);
	}

	@Override
	public List<QuestionMapperInstance> findFullStackQuestionMapperInstancesForDotNet(String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findFullStackQuestionMapperInstancesForDotNet(companyId);
	}

	@Override
	public List<QuestionMapperInstance> findFullStackQuestionMapperInstancesForJavaScript(String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findFullStackQuestionMapperInstancesForJavaScript(companyId);
	}

	@Override
	public List<QuestionMapperInstance> findQuestionMapperInstancesForUserForCourseContext(String courseContext,
			String user, String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findQuestionMapperInstancesForUserForCourseContext(courseContext, user, companyId);
	}

	@Override
	public List<String> findQuestionMapperInstancesForUserLastAttemptForCourseContext(
			String courseContext, String user, String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findUniqueUsersForCourseContext(courseContext, user, companyId);
	}

	@Override
	public List<String> findUniqueTestsForCourseContext(String courseContext, String user, String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findUniqueTestsForCourseContext(courseContext, user, companyId);
	}

	@Override
	public List<String> findUniqueUsersForCourseContextAndTest(String testName, String courseContext, String user,
			String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findUniqueUsersForCourseContextAndTest(testName, courseContext, user, companyId);
	}

	@Override
	public List<QuestionMapperInstance> findQuestionMapperInstancesForUserForCourseContextAndTest(String testName,
			String courseContext, String user, String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findQuestionMapperInstancesForUserForCourseContextAndTest(testName, courseContext, user, companyId);
	}

	@Override
	public List<QuestionMapperInstance> findAllFullStackQuestionMapperInstances(String companyId) {
		// TODO Auto-generated method stub
		return questionMapperInstanceRepository.findAllFullStackQuestionMapperInstances(companyId);
	}
	
	public Page<QuestionMapperInstance> findAllFullStackQuestionMapperInstances(@Param("companyId") String companyId,  Pageable pageable){
		return questionMapperInstanceRepository.findAllFullStackQuestionMapperInstances(companyId, pageable);
	}

	@Override
	public Float getWeightedTestScore(String user, String testName, String companyId) {
		// TODO Auto-generated method stub
		List<QuestionMapperInstance> instances = findQuestionMapperInstancesForUserForTest(testName, user, companyId);
		Map<Integer, List<QuestionMapperInstance>> map = new HashMap<Integer, List<QuestionMapperInstance>>();
		for(QuestionMapperInstance instance : instances){
			Integer weight = instance.getQuestionMapper().getQuestion().getQuestionWeight();
			if(weight == null){
				weight = 1;
			}
			if(map.get(weight) == null){
				List<QuestionMapperInstance> list = new ArrayList<QuestionMapperInstance>();
				list.add(instance);
				map.put(weight, list);
			}
			else{
				map.get(weight).add(instance);
			}
		}
		Map<Integer, Float> map_weight_percentage = new HashMap<Integer, Float>();
		for(Integer weight : map.keySet()){
			List<QuestionMapperInstance> instances2 = map.get(weight);
			Integer noOfCorrect = 0;
			for(QuestionMapperInstance instance : instances2){
				if(instance.getCorrect()){
					noOfCorrect++;
				}
			}
			Float percentageForWeightQs = (float) (100 * noOfCorrect / instances2.size());
			map_weight_percentage.put(weight, percentageForWeightQs);
		}
		Integer totalWeight = 0;
		Float totalScore = 0f;
		for(Integer weight : map_weight_percentage.keySet()){
			totalWeight += weight;
			Float percentageForWeight = map_weight_percentage.get(weight);
			totalScore += percentageForWeight * weight;
		}
		Float weightedScoreForTest = totalScore / totalWeight;
		return weightedScoreForTest;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public QuestionMapperInstance removeDublicateAndGetInstance(String questionText, String testName,
			String sectionName, String user, String companyId) {
		QuestionMapperInstance questionMapperInstance = null;
		// TODO Auto-generated method stub
		try {
			logger.info("questiontext "+questionText);
			logger.info("testName "+testName);
			logger.info("user "+user);
			logger.info("sectionName "+sectionName);
			List<QuestionMapperInstance> annswers = questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUserSet(questionText, testName, sectionName, user, companyId);
			//questionMapperInstance = 	questionMapperInstanceRepository.findUniqueQuestionMapperInstanceForUser(questionText, testName, sectionName, user, companyId);
			logger.info("annswers size "+annswers.size());
			if(annswers == null || annswers.size() == 0){
				logger.info("no ans");
				if(questionText == null){
					logger.info("**************");
					return null;
				}
				if(questionText != null && questionText.contains("\r") || questionText.contains("\t") || questionText.contains("\n")){
					System.out.println(questionText);
					questionText = questionText.replaceAll("[" + System.lineSeparator() + "]", "%");
					//System.out.println(questionText);
					logger.info("questiontext "+questionText);
					logger.info("searcUniqueQuestionMapperInstanceForUserSet called");
					annswers = questionMapperInstanceRepository.searcUniqueQuestionMapperInstanceForUserSet(questionText, testName, sectionName, user, companyId);
					//System.out.println("annswers "+annswers);
					logger.info("searcUniqueQuestionMapperInstanceForUserSet called "+(annswers==null?0:annswers.size()));
					if(annswers == null || annswers.size() == 0){
						logger.info("0000 1");
						return null;
					}
					else if(annswers.size() > 0){
						QuestionType type = annswers.get(0).getQuestionMapper().getQuestion().getQuestionType();
						logger.info("size returned "+annswers.size());
						if(type.getType().equalsIgnoreCase(QuestionType.FULL_STACK_JAVA.getType()) || type.getType().equalsIgnoreCase(QuestionType.FULLSTACK.getType())){
							logger.info("size returned xxx "+annswers.size());
							return annswers.get((annswers.size()-1));
						}
						
					}
				}
				else{
					logger.info("0000 2");
					return null;
				}
				
			}
			
			if(annswers.size() == 1){
				return annswers.get(0);
			}
			
			if(annswers.size() > 1){
				deleteDuplicateAnswers(annswers);
			}
			
			
		} catch (javax.persistence.NonUniqueResultException e) {
			// TODO Auto-generated catch block
			//should not come here
			e.printStackTrace();
			logger.error("should not come here duplicate anss for "+questionText+"-"+ testName+"-"+  sectionName+"-"+  user+"-"+  companyId);
			List<QuestionMapperInstance> qms = questionMapperInstanceRepository.findDuplicateQuestionMapperInstanceForUser(questionText, testName, sectionName, user, companyId);
			for(QuestionMapperInstance q : qms){
				questionMapperInstanceRepository.delete(q);
			}
		}
		return questionMapperInstance;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteDuplicateAnswers(List<QuestionMapperInstance> qms){
		for(QuestionMapperInstance q : qms){
			questionMapperInstanceRepository.delete(q);
		}
	}

	

}
