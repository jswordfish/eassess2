package com.assessment.services.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.che.WorkspaceChe;
import com.assessment.data.CheCluster;
import com.assessment.eclipseche.services.EclipseCheService;
import com.assessment.repositories.CheClusterRepository;
import com.assessment.services.CheService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
@Service
@Transactional
public class CheServiceImpl implements CheService{
	@Autowired
	CheClusterRepository rep;
	
	Logger logger = LoggerFactory.getLogger(CheServiceImpl.class);

	@Override
	public List<CheCluster> getAllClusters(String companyId) {
		// TODO Auto-generated method stub
		return rep.findCheClusters(companyId);
	}

	@Override
	public synchronized void saveOrUpdate(CheCluster cheCluster) {
		// TODO Auto-generated method stub
		CheCluster cheCluster2 = rep.findUniqueCheCluster(cheCluster.getCompanyId(), cheCluster.getUrl());
		if(cheCluster2 == null){
			cheCluster.setCreateDate(new Date());
			rep.save(cheCluster);
		}
		else{
			cheCluster2.setClusterName(cheCluster.getClusterName());
			cheCluster2.setUrl(cheCluster.getUrl());
			cheCluster2.setNoOfWorkspaces(cheCluster.getNoOfWorkspaces());
			cheCluster2.setUpdateDate(new Date());
			rep.save(cheCluster2);
		}
	}

	@Override
	public synchronized String getApiURLAndSaveDetailsForCluster(String companyId) {
		// TODO Auto-generated method stub
		System.out.println("in getApiURLAndSaveDetailsForCluster "+companyId);
		List<CheCluster>  cheClusters = getAllClusters(companyId);
		System.out.println("in getApiURLAndSaveDetailsForCluster size "+cheClusters.size());
		for(CheCluster cheCluster : cheClusters){
			if(!cheCluster.getCapacityReached()){
				//check from back ennd if any workspaces are created manually
				String url = cheCluster.getUrl();
				System.out.println("url is "+url);
				Integer cap = 100;
				try {
					cap = capaciyCountFromChe(url);
					System.out.println("cap is "+cap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Can't call api's of che clusters", e);
				}
				if(cap < 28){
					cheCluster.setNoOfWorkspaces((cap+1));
					rep.save(cheCluster);
					return cheCluster.getUrl();
				}
				else{
					cheCluster.setNoOfWorkspaces(cap);
					cheCluster.setCapacityReached(true);
					rep.save(cheCluster);
				}
			}
		}
		return null;
	}
	
	public Integer capaciyCountFromChe(String url) throws Exception{
		//https://che-che.eclipse.vijaygalaxy.org/api/workspace?skipCount=0&maxItems=30
		ObjectMapper mapper = new ObjectMapper();
		String urlall = url+"/api/workspace?skipCount=0&maxItems=40";
		URL url2 = new URL(urlall);
		HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	
	     String data = new EclipseCheService().getResponse(conn);
	     List<WorkspaceChe> workspaceResponses = mapper.readValue(data.getBytes(), new TypeReference<List<WorkspaceChe>>() {});
	     System.out.println("size "+workspaceResponses.size());
	     return workspaceResponses.size();
	}

}


