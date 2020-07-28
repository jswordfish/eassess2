package com.assessment.services;

import java.util.List;

import com.assessment.data.ProspectMessage;

public interface ProspectMessageService {
	
	public ProspectMessage addProspectMessage(ProspectMessage message);
	
	public List<ProspectMessage> getMessages();

}
