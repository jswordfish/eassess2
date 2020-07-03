package com.assessment.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.data.QuestionMapperInstance;

public interface QuestionMapperInstanceService {

	public QuestionMapperInstance removeDublicateAndGetInstance(String questionText, String testName,
			String sectionName, String user, String companyId);

	public QuestionMapperInstance findUniqueQuestionMapperInstanceForUser(String questionText, String testName,
			String sectionName, String user, String companyId);

	public List<QuestionMapperInstance> findQuestionMapperInstancesForUserForTest(String testName, String user,
			String companyId);

	public boolean canEditTest(String sectionName, String testName, String companyId);

	public List<QuestionMapperInstance> getInstances(String qualifier1, String companyId);

	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String companyId);

	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String qualifier3,
			String companyId);

	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String qualifier3,
			String qualifier4, String companyId);

	public List<QuestionMapperInstance> getInstances(String qualifier1, String qualifier2, String qualifier3,
			String qualifier4, String qualifier5, String companyId);

	// public List<QuestionMapperInstance> getInstances(String qualifier1,String
	// qualifier2, String qualifier3, String qualifier4, String qualifier5,String
	// qualifier6, String companyId);

	public List<QuestionMapperInstance> getInstancesOR(String qualifier, String companyId);

	List<QuestionMapperInstance> findFullStackQuestionMapperInstancesForJava(String companyId);

	List<QuestionMapperInstance> findFullStackQuestionMapperInstancesForDotNet(String companyId);

	List<QuestionMapperInstance> findFullStackQuestionMapperInstancesForJavaScript(String companyId);

	List<QuestionMapperInstance> findQuestionMapperInstancesForUserForCourseContext(String courseContext,
			String user, String companyId);

	List<String> findQuestionMapperInstancesForUserLastAttemptForCourseContext(String courseContext, String user,
			String companyId);

	List<String> findUniqueTestsForCourseContext(String courseContext, String user, String companyId);

	List<String> findUniqueUsersForCourseContextAndTest(String testName, String courseContext, String user,
			String companyId);

	List<QuestionMapperInstance> findQuestionMapperInstancesForUserForCourseContextAndTest(String testName,
			String courseContext, String user, String companyId);

	List<QuestionMapperInstance> findAllFullStackQuestionMapperInstances(String companyId);

	public Float getWeightedTestScore(String user, String testName, String companyId);

	public void deleteDuplicateAnswers(List<QuestionMapperInstance> qms);

	Page<QuestionMapperInstance> findAllFullStackQuestionMapperInstances(String companyId, Pageable pageable);

	List<QuestionMapperInstance> findSubjectQuestionMapperInstancesForUserAndTest(String testName, String user,
			String companyId);

	QuestionMapperInstance findById(Long id);

}
