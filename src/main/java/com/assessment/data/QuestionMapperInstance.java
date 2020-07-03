package com.assessment.data;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Primary key - questionMapper & companyId
 * 
 * @author jsutaria
 *
 */
@Entity
public class QuestionMapperInstance extends Base {
	@ManyToOne
	QuestionMapper questionMapper;

	String userChoices;

	Boolean correct = false;

	Boolean answered = false;

	@Column(length = 2000)
	String questionText;

	@Transient
	String encodedQuestionText;

	@javax.validation.constraints.NotNull
	String testName;

	@javax.validation.constraints.NotNull
	String sectionName;

	@javax.validation.constraints.NotNull
	String user;

	String codingOuputBySystemTestCase;

	@Lob
	String codeByUser;

	@Lob
	String reviewerComments;

	@Column(length = 400)
	String workspaceUrl;

	@Transient
	String encodedUrl;

	@Column(length = 200)
	String workSpaceId;

	@Column(length = 400)
	String usageDocumentUrl;

	@Column
	Boolean workspaceSubmitted;

	@Transient
	String uerFullName;
	// Long userTestSessionId;

	@Transient
	String workspaceDateOfSubmission;

	Boolean confidence;

	Boolean codeCompilationErrors;

	Boolean codeRunTimeErrors;

	Boolean testCaseInputPositive;

	Boolean testCaseInputNegative;

	Boolean testCaseMinimalValue;

	Boolean testCaseMaximumValue;

	Boolean testCaseInvalidData;

	Integer noOfTestCases;

	Integer noOfTestCasesPassed;

	Integer functionalTestCases;
	Integer boundaryTestCases;
	Integer exceptionTestCases;

	Integer functionalTestCasesPassed;
	Integer boundaryTestCasesPassed;
	Integer exceptionTestCasesPassed;

	String courseName;

	String moduleName;

	String learningPathName;

	@Transient
	String lastDate;

	@Transient
	String[] fillInBlanksAnswer;

	String matchRight1;

	String matchRight2;

	String matchRight3;

	String matchRight4;

	String matchRight5;

	String matchRight6;

	@Lob
	String testCasesResultXml;

	Long testId;

	@Column(length = 1000)
	String imageUploadUrl;

	@Column(length = 1000)
	String videoUploadUrl;

	@Column(length = 5000)
	String subjectiveText;

	Boolean subjective = false;

	Boolean markComplete;

	Integer marksAssignedInPercentIncaseSubjective;

	String reviewedBy;

	@Transient
	String style;

	@Column(length = 1000)
	String reviewerCommentsForSubjectiveQuestion;

	public QuestionMapper getQuestionMapper() {
		return questionMapper;
	}

	public void setQuestionMapper(QuestionMapper questionMapper) {
		this.questionMapper = questionMapper;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public Boolean getAnswered() {
		return answered;
	}

	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}

	public String getUserChoices() {
		return userChoices;
	}

	public void setUserChoices(String userChoices) {
		this.userChoices = userChoices;
		if (this.userChoices != null) {
			if (userChoices.length() > 0) {
				setAnswered(true);
				String choices = getQuestionMapper().getQuestion().getRightChoices();
				String correct[] = choices.split("-");
				String userC[] = userChoices.split("-");
				// String correct[] = choices.split("-");
				// String userC[] = userChoices.split("-");
				if (Arrays.equals(correct, userC)) {
					setCorrect(true);
				}
			} else {
				setAnswered(false);
			}
		} else {
			String type = getQuestionMapper().getQuestion().getQuestionType().getType();
			if (type.equals(QuestionType.CODING.getType())
					|| type.equals(QuestionType.FILL_BLANKS_MCQ.getType())
					|| type.equals(QuestionType.MATCH_FOLLOWING_MCQ.getType())
					|| type.equals(QuestionType.FULL_STACK_JAVA.getType())
					|| type.equals(QuestionType.FULLSTACK.getType())) {
				setAnswered(true);
			} else {
				setAnswered(false);
			}
		}

	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getCodingOuputBySystemTestCase() {
		return codingOuputBySystemTestCase;
	}

	public void setCodingOuputBySystemTestCase(String codingOuputBySystemTestCase) {
		codingOuputBySystemTestCase = codingOuputBySystemTestCase == null ? ""
				: (codingOuputBySystemTestCase.trim());
		this.codingOuputBySystemTestCase = codingOuputBySystemTestCase;

		if (getCodeCompilationErrors()) {
			setCorrect(false);
		}

		// System.out.println("in codingOuputBySystemTestCase
		// "+codingOuputBySystemTestCase);
		// System.out.println("in codingOuputBySystemTestCase2
		// "+getQuestionMapper().getQuestion().getHiddenOutputNegative());

		if (getQuestionMapper().getQuestion().getHiddenOutputNegative().equalsIgnoreCase(
				codingOuputBySystemTestCase == null ? "" : codingOuputBySystemTestCase)) {
			setTestCaseInputNegative(true);
			// System.out.println("in setCodingOuputBySystemTestCase "+true);
			setCorrect(true);
		} else {
			setTestCaseInputNegative(false);
			// System.out.println("in setCodingOuputBySystemTestCase "+false);
			setCorrect(false);
		}
		setAnswered(true);
	}

	public String getCodeByUser() {
		return codeByUser;
	}

	public void setCodeByUser(String codeByUser) {
		this.codeByUser = codeByUser;
	}

	public String getReviewerComments() {
		return reviewerComments;
	}

	public void setReviewerComments(String reviewerComments) {
		this.reviewerComments = reviewerComments;
	}

	public String getWorkspaceUrl() {
		return workspaceUrl;
	}

	public void setWorkspaceUrl(String workspaceUrl) {
		this.workspaceUrl = workspaceUrl;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getUsageDocumentUrl() {
		return usageDocumentUrl;
	}

	public void setUsageDocumentUrl(String usageDocumentUrl) {
		this.usageDocumentUrl = usageDocumentUrl;
	}

	public Boolean getWorkspaceSubmitted() {
		return workspaceSubmitted;
	}

	public void setWorkspaceSubmitted(Boolean workspaceSubmitted) {
		this.workspaceSubmitted = workspaceSubmitted;
	}

	public String getUerFullName() {
		return uerFullName;
	}

	public void setUerFullName(String uerFullName) {
		this.uerFullName = uerFullName;
	}

	public String getWorkspaceDateOfSubmission() {
		String pattern = "dd-MMM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (getUpdateDate() == null) {
			if (getCreateDate() == null) {
				return "Not Available";
			} else {
				return simpleDateFormat.format(getCreateDate());
			}

		} else {
			return simpleDateFormat.format(getUpdateDate());
		}
	}

	public void setWorkspaceDateOfSubmission(String workspaceDateOfSubmission) {
		this.workspaceDateOfSubmission = workspaceDateOfSubmission;
	}

	public Boolean getConfidence() {
		return confidence;
	}

	public void setConfidence(Boolean confidence) {
		this.confidence = confidence;
	}

	public Boolean getTestCaseInputPositive() {
		if (this.testCaseInputPositive == null) {
			return false;
		}
		return testCaseInputPositive;
	}

	public void setTestCaseInputPositive(Boolean testCaseInputPositive) {
		this.testCaseInputPositive = testCaseInputPositive;
	}

	public Boolean getTestCaseInputNegative() {
		if (this.testCaseInputNegative == null) {
			return false;
		}
		return testCaseInputNegative;
	}

	public void setTestCaseInputNegative(Boolean testCaseInputNegative) {
		this.testCaseInputNegative = testCaseInputNegative;
	}

	public Boolean getTestCaseMinimalValue() {
		if (this.testCaseMinimalValue == null) {
			return false;
		}
		return testCaseMinimalValue;
	}

	public void setTestCaseMinimalValue(Boolean testCaseMinimalValue) {
		this.testCaseMinimalValue = testCaseMinimalValue;
	}

	public Boolean getTestCaseMaximumValue() {
		if (this.testCaseMaximumValue == null) {
			return false;
		}
		return testCaseMaximumValue;
	}

	public void setTestCaseMaximumValue(Boolean testCaseMaximumValue) {
		this.testCaseMaximumValue = testCaseMaximumValue;
	}

	public Boolean getTestCaseInvalidData() {
		if (this.testCaseInvalidData == null) {
			return false;
		}
		return testCaseInvalidData;
	}

	public void setTestCaseInvalidData(Boolean testCaseInvalidData) {
		this.testCaseInvalidData = testCaseInvalidData;
	}

	public Boolean getCodeCompilationErrors() {
		if (this.codeCompilationErrors == null) {
			return false;
		}
		return codeCompilationErrors;
	}

	public void setCodeCompilationErrors(Boolean codeCompilationErrors) {
		this.codeCompilationErrors = codeCompilationErrors;
	}

	public Boolean getCodeRunTimeErrors() {
		if (this.codeRunTimeErrors == null) {
			return false;
		}
		return codeRunTimeErrors;
	}

	public void setCodeRunTimeErrors(Boolean codeRunTimeErrors) {
		this.codeRunTimeErrors = codeRunTimeErrors;
	}

	public String getEncodedUrl() {

		if (getWorkspaceUrl() == null || getWorkspaceUrl().trim().length() == 0) {
			return "";
		}
		return URLEncoder.encode(Base64.getEncoder().encodeToString(getWorkspaceUrl().getBytes()));
	}

	public void setEncodedUrl(String encodedUrl) {
		this.encodedUrl = encodedUrl;
	}

	public String getEncodedQuestionText() {
		if (getQuestionText() == null) {
			return "";
		}
		return new org.apache.commons.codec.binary.Base64().encodeAsString(getQuestionText().getBytes());
	}

	public void setEncodedQuestionText(String encodedQuestionText) {
		this.encodedQuestionText = encodedQuestionText;
	}

	public Integer getNoOfTestCases() {
		return noOfTestCases;
	}

	public void setNoOfTestCases(Integer noOfTestCases) {
		this.noOfTestCases = noOfTestCases;
	}

	public Integer getNoOfTestCasesPassed() {
		return noOfTestCasesPassed;
	}

	public void setNoOfTestCasesPassed(Integer noOfTestCasesPassed) {
		this.noOfTestCasesPassed = noOfTestCasesPassed;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getLearningPathName() {
		return learningPathName;
	}

	public void setLearningPathName(String learningPathName) {
		this.learningPathName = learningPathName;
	}

	public Integer getFunctionalTestCases() {
		return functionalTestCases;
	}

	public void setFunctionalTestCases(Integer functionalTestCases) {
		this.functionalTestCases = functionalTestCases;
	}

	public Integer getBoundaryTestCases() {
		return boundaryTestCases;
	}

	public void setBoundaryTestCases(Integer boundaryTestCases) {
		this.boundaryTestCases = boundaryTestCases;
	}

	public Integer getExceptionTestCases() {
		return exceptionTestCases;
	}

	public void setExceptionTestCases(Integer exceptionTestCases) {
		this.exceptionTestCases = exceptionTestCases;
	}

	public Integer getFunctionalTestCasesPassed() {
		return functionalTestCasesPassed;
	}

	public void setFunctionalTestCasesPassed(Integer functionalTestCasesPassed) {
		this.functionalTestCasesPassed = functionalTestCasesPassed;
	}

	public Integer getBoundaryTestCasesPassed() {
		return boundaryTestCasesPassed;
	}

	public void setBoundaryTestCasesPassed(Integer boundaryTestCasesPassed) {
		this.boundaryTestCasesPassed = boundaryTestCasesPassed;
	}

	public Integer getExceptionTestCasesPassed() {
		return exceptionTestCasesPassed;
	}

	public void setExceptionTestCasesPassed(Integer exceptionTestCasesPassed) {
		this.exceptionTestCasesPassed = exceptionTestCasesPassed;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String[] getFillInBlanksAnswer() {
		return fillInBlanksAnswer;
	}

	public void setFillInBlanksAnswer(String[] fillInBlanksAnswer) {
		this.fillInBlanksAnswer = fillInBlanksAnswer;
	}

	public String getMatchRight1() {
		return matchRight1;
	}

	public void setMatchRight1(String matchRight1) {
		this.matchRight1 = matchRight1;
	}

	public String getMatchRight2() {
		return matchRight2;
	}

	public void setMatchRight2(String matchRight2) {
		this.matchRight2 = matchRight2;
	}

	public String getMatchRight3() {
		return matchRight3;
	}

	public void setMatchRight3(String matchRight3) {
		this.matchRight3 = matchRight3;
	}

	public String getMatchRight4() {
		return matchRight4;
	}

	public void setMatchRight4(String matchRight4) {
		this.matchRight4 = matchRight4;
	}

	public String getMatchRight5() {
		return matchRight5;
	}

	public void setMatchRight5(String matchRight5) {
		this.matchRight5 = matchRight5;
	}

	public String getMatchRight6() {
		return matchRight6;
	}

	public void setMatchRight6(String matchRight6) {
		this.matchRight6 = matchRight6;
	}

	public String getTestCasesResultXml() {
		return testCasesResultXml;
	}

	public void setTestCasesResultXml(String testCasesResultXml) {
		this.testCasesResultXml = testCasesResultXml;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getImageUploadUrl() {
		return imageUploadUrl;
	}

	public void setImageUploadUrl(String imageUploadUrl) {
		this.imageUploadUrl = imageUploadUrl;
	}

	public String getVideoUploadUrl() {
		return videoUploadUrl;
	}

	public void setVideoUploadUrl(String videoUploadUrl) {
		this.videoUploadUrl = videoUploadUrl;
	}

	public String getSubjectiveText() {
		return subjectiveText;
	}

	public void setSubjectiveText(String subjectiveText) {
		this.subjectiveText = subjectiveText;
	}

	public Boolean getSubjective() {
		return subjective;
	}

	public void setSubjective(Boolean subjective) {
		this.subjective = subjective;
	}

	public Boolean getMarkComplete() {
		return markComplete;
	}

	public void setMarkComplete(Boolean markComplete) {
		this.markComplete = markComplete;
	}

	public Integer getMarksAssignedInPercentIncaseSubjective() {
		return marksAssignedInPercentIncaseSubjective;
	}

	public void setMarksAssignedInPercentIncaseSubjective(Integer marksAssignedInPercentIncaseSubjective) {
		this.marksAssignedInPercentIncaseSubjective = marksAssignedInPercentIncaseSubjective;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	@Transient
	public String getTimeOfAnswer() {
		Date dt = getUpdateDate() == null ? getCreateDate() : getUpdateDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
		if (dt != null) {
			return dateFormat.format(dt);
		} else {
			return "NA";
		}
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getReviewerCommentsForSubjectiveQuestion() {
		return reviewerCommentsForSubjectiveQuestion;
	}

	public void setReviewerCommentsForSubjectiveQuestion(String reviewerCommentsForSubjectiveQuestion) {
		this.reviewerCommentsForSubjectiveQuestion = reviewerCommentsForSubjectiveQuestion;
	}

}
