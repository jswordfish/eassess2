package com.assessment.web.dto;

import com.assessment.data.TestCase;
import com.assessment.data.TestCases;

public class TestCaseStatistics {
	
	TestCases testCases;
	
	Integer totalTestCases;
	
	Integer totalTestCasesPassed;
	
	Integer totalFunctionalTestCases;
	
	Integer totalFunctionalTestCasesPassed;
	
	Integer totalBoundaryTestCases;
	
	Integer totalBoundaryTestCasedPassed;
	
	Integer totalExceptionTestCases;
	
	Integer totalExceptionTestCasesPassed;

	

	public TestCases getTestCases() {
		return testCases;
	}

	public void setTestCases(TestCases testCases) {
		this.testCases = testCases;
	}

	public Integer getTotalTestCases() {
		return totalTestCases;
	}

	public void setTotalTestCases(Integer totalTestCases) {
		this.totalTestCases = totalTestCases;
	}

	public Integer getTotalFunctionalTestCases() {
		return totalFunctionalTestCases;
	}

	public void setTotalFunctionalTestCases(Integer totalFunctionalTestCases) {
		this.totalFunctionalTestCases = totalFunctionalTestCases;
	}

	public Integer getTotalFunctionalTestCasesPassed() {
		return totalFunctionalTestCasesPassed;
	}

	public void setTotalFunctionalTestCasesPassed(Integer totalFunctionalTestCasesPassed) {
		this.totalFunctionalTestCasesPassed = totalFunctionalTestCasesPassed;
	}

	public Integer getTotalBoundaryTestCases() {
		return totalBoundaryTestCases;
	}

	public void setTotalBoundaryTestCases(Integer totalBoundaryTestCases) {
		this.totalBoundaryTestCases = totalBoundaryTestCases;
	}

	public Integer getTotalBoundaryTestCasedPassed() {
		return totalBoundaryTestCasedPassed;
	}

	public void setTotalBoundaryTestCasedPassed(Integer totalBoundaryTestCasedPassed) {
		this.totalBoundaryTestCasedPassed = totalBoundaryTestCasedPassed;
	}

	public Integer getTotalExceptionTestCases() {
		return totalExceptionTestCases;
	}

	public void setTotalExceptionTestCases(Integer totalExceptionTestCases) {
		this.totalExceptionTestCases = totalExceptionTestCases;
	}

	public Integer getTotalExceptionTestCasesPassed() {
		return totalExceptionTestCasesPassed;
	}

	public void setTotalExceptionTestCasesPassed(Integer totalExceptionTestCasesPassed) {
		this.totalExceptionTestCasesPassed = totalExceptionTestCasesPassed;
	}

	public Integer getTotalTestCasesPassed() {
		return totalTestCasesPassed;
	}

	public void setTotalTestCasesPassed(Integer totalTestCasesPassed) {
		this.totalTestCasesPassed = totalTestCasesPassed;
	}
	
	

}
