package com.assessment.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.assessment.data.UserTestSession;
import com.assessment.reports.manager.AssessmentTestData;

public interface UserTestSessionService {
	
public UserTestSession findUserTestSession(String user, String testName, String companyId);
	
	public UserTestSession saveOrUpdate(UserTestSession userTestSession);
	
	public List<AssessmentTestData> getAllResultsData(String companyId);
	
	public List<UserTestSession> findUserSessionsForTest(String testName, String companyId);
	
	List<UserTestSession> findTestListForUser( String companyId, String user);
	
	List<UserTestSession> findResultsForDay(String companyId, Date start, Date end);
	
	List<UserTestSession> findSubjectiveResultsNotMarkedAsCompleteForInstitution(String companyId, String collegeName, String grade, String classifier);

	List<UserTestSession> findSubjectiveResultsMarkedAsCompleteForInstitution(@Param("companyId") String companyId, @Param("collegeName") String collegeName, @Param("grade") String grade, @Param("classifier") String classifier);

	UserTestSession findSessinById(Long sessionId);
	
	List<UserTestSession> findByCompanyIdAndCollegeName(String companyId, String collegeName);
}
