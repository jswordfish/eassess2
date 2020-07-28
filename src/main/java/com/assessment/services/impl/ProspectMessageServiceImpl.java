package com.assessment.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.data.ProspectMessage;
import com.assessment.repositories.ProspectMessageRepository;
import com.assessment.services.ProspectMessageService;
@org.springframework.stereotype.Service
@Transactional
public class ProspectMessageServiceImpl implements ProspectMessageService{
	
	@Autowired
	ProspectMessageRepository rep;

	@Override
	public ProspectMessage addProspectMessage(ProspectMessage message) {
		// TODO Auto-generated method stub
		return rep.save(message);
	}

	@Override
	public List<ProspectMessage> getMessages() {
		// TODO Auto-generated method stub
		return rep.getMessages();
	}

}
