package com.assessment.che;

public class Attributes {
	//private String org.eclipse.che.runtimes_id;

    private String stopped_by;

    private String infrastructureNamespace;

    private String stopped;

    private String created;

    private String stoppedAbnormally;

    private String errorMessage;

    private String stackName;

    private String updated;

	

	public String getStopped_by() {
		return stopped_by;
	}

	public void setStopped_by(String stopped_by) {
		this.stopped_by = stopped_by;
	}

	public String getInfrastructureNamespace() {
		return infrastructureNamespace;
	}

	public void setInfrastructureNamespace(String infrastructureNamespace) {
		this.infrastructureNamespace = infrastructureNamespace;
	}

	public String getStopped() {
		return stopped;
	}

	public void setStopped(String stopped) {
		this.stopped = stopped;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getStoppedAbnormally() {
		return stoppedAbnormally;
	}

	public void setStoppedAbnormally(String stoppedAbnormally) {
		this.stoppedAbnormally = stoppedAbnormally;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStackName() {
		return stackName;
	}

	public void setStackName(String stackName) {
		this.stackName = stackName;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

    
}
