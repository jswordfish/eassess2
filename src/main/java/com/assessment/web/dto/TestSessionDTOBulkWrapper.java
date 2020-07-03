package com.assessment.web.dto;

import java.util.ArrayList;
import java.util.List;

public class TestSessionDTOBulkWrapper {
	String error;

	List<TestSessionDTOBulk> results = new ArrayList<TestSessionDTOBulk>();

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<TestSessionDTOBulk> getResults() {
		return results;
	}

	public void setResults(List<TestSessionDTOBulk> results) {
		this.results = results;
	}

}
