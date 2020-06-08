package com.test.eclipseche.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.che.WorkspaceChe;
import com.assessment.common.AssessmentGenericException;
import com.assessment.eclipseche.config.response.WorkspaceResponse;
import com.assessment.eclipseche.services.EclipseCheService;
import com.assessment.services.CheService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:appContext.xml"})
@Transactional
public class TestWorkspaceService {
	EclipseCheService eclipseCheService = new EclipseCheService();
	ObjectMapper mapper = new ObjectMapper();
	
	
	@Autowired
	CheService cheService;
	
	String url = "https://che-che.eclipse6.yaksha.online  ";
	
	@Test
	public void testCheCapacity() throws Exception{
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
	}
	
	@Test
	public void testDotNetWithMySQLService() throws Exception{
		String url = cheService.getApiURLAndSaveDetailsForCluster("IH");
		if(url == null){
 			throw new AssessmentGenericException("NO_CLUSTER_AVAILABLE_CHECK_CLUSTER_ADMIN");
 		}
 		eclipseCheService.setPosition(url.length());
 		url += "/api/workspace/devfile?start-after-create=false&namespace=che";
 		eclipseCheService.setUrl(url);
		//String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Java_FullStack.json"));
		
	//	String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Aspnet_4.8.json"));
 		String json = FileUtils.readFileToString(new File("assessment/eclipseChe/DotNet_NewEclipseChe_MONGODB.json"));
 		
		String name = "dotnet"+System.currentTimeMillis();
		Integer code = name.hashCode();
		if(code < 0){
			code = code * -1;
		}
	
	json = json.replace("${APP_USER}", name);
	json = json.replace("${APP_USER_CODE}", "dotnet"+code);
	json = json.replace("TIME_STAMP", System.currentTimeMillis()+"");
		//json = json.replace("${APP_USER}", "a01");
		json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
		
		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
		System.out.println(workspaceResponse.getLinks().getIde());
	}
	
	@Test
	public void testDotNetWithHigherVersion() throws Exception{
		String url = cheService.getApiURLAndSaveDetailsForCluster("IH");
		if(url == null){
 			throw new AssessmentGenericException("NO_CLUSTER_AVAILABLE_CHECK_CLUSTER_ADMIN");
 		}
 		eclipseCheService.setPosition(url.length());
 		url += "/api/workspace/devfile?start-after-create=false&namespace=che";
 		eclipseCheService.setUrl(url);
		//String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Java_FullStack.json"));
		
		String json = FileUtils.readFileToString(new File("assessment\\eclipseChe\\DotNet_NewEclipseChe_MySQL.json"));
		
		String name = "dotnet"+System.currentTimeMillis();
		json = json.replace("${APP_USER}", name);
		
		Integer code = name.hashCode();
			if(code < 0){
				code = code * -1;
			}
		
		json = json.replace("${APP_USER}", "dotnet"+System.currentTimeMillis());
		json = json.replace("${APP_USER_CODE}", "dotnet"+code);
		//${APP_USER_CODE}
		//json = json.replace("${APP_USER}", "a01");
		json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
		
		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
		System.out.println(workspaceResponse.getLinks().getIde());
	}
	
	
	@Test
	public void testLoadTestEclipseChe() throws Exception{
		
		
		try {
			for(int i=0;i<134;i++){
				String url = cheService.getApiURLAndSaveDetailsForCluster("IH");
		 		if(url == null){
		 			throw new AssessmentGenericException("NO_CLUSTER_AVAILABLE_CHECK_CLUSTER_ADMIN");
		 		}
		 		eclipseCheService.setPosition(url.length());
		 		url += "/api/workspace/devfile?start-after-create=false&namespace=che";
		 		eclipseCheService.setUrl(url);
				//String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Java_FullStack.json"));
				
				String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Java_FullStack_NewEclipseChe.json"));
				
				json = json.replace("${APP_USER}", "lemon"+i+System.currentTimeMillis());
				//json = json.replace("${APP_USER}", "a01");
				json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
				
				WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
				System.out.println(workspaceResponse.getLinks().getIde());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testCreateWorkSpace() throws Exception{
		try {
			//for(int i=0;i<32;i++){
				//String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Java_FullStack.json"));
				
				String json = FileUtils.readFileToString(new File("assessment/eclipseChe/Java_FullStack_NewEclipseChe.json"));
				
				json = json.replace("${APP_USER}", "Testnew");
				
				
				String name = "Testnew"+System.currentTimeMillis();
				Integer code = name.hashCode();
				if(code < 0){
					code = code * -1;
				}
				
				json = json.replace("${APP_USER_CODE}", "java"+code);
				//json = json.replace("${APP_USER}", "a01");
				json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
				
				WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
				System.out.println(workspaceResponse.getLinks().getIde());
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateWorkSpacePHP() throws Exception{
		String json = FileUtils.readFileToString(new File("assessment/eclipseChe/PHP_MySQL.json"));
		json = json.replace("${APP_USER}", "php-3456-67894224"+System.currentTimeMillis());
		//json = json.replace("${APP_USER}", "a01");
		json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
		
		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
		System.out.println(workspaceResponse.getLinks().getIde());
	}
	
	@Test
	public void testCreateWorkSpaceAngularJavascript() throws Exception{
		String json = FileUtils.readFileToString(new File("assessment/eclipseChe/ANGULAR_JAVASCRIPT_MYSQL.json"));
		json = json.replace("${APP_USER}", "angular-3456-67894224"+System.currentTimeMillis());
		//json = json.replace("${APP_USER}", "a01");
		json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
		
		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
		System.out.println(workspaceResponse.getLinks().getIde());
	}
	
	@Test
	public void testCreateWorkSpaceCSharp() throws Exception{
		String json = FileUtils.readFileToString(new File("assessment/eclipseChe/c-sharp.json"));
		json = json.replace("${APP_USER}", "c-sharp-"+System.currentTimeMillis());
		//json = json.replace("${APP_USER}", "a01");
		json = json.replace("${APP_DESC}", "Sample............................Project\n\n\n.........");
		
		WorkspaceResponse workspaceResponse = eclipseCheService.createWorkSpace(json);
		System.out.println(workspaceResponse.getLinks().getIde());
	}
	
	
	@Test
	public void testWorkspacesFetch() throws Exception{
		URL url2 = new URL("https://che-che.eclipse.yaksha.online/api/workspace?skipCount=0&maxItems=900");
		HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	
	     String data = getResponse(conn);
	     List<WorkspaceResponse> workspaceResponses = mapper.readValue(data.getBytes(), new TypeReference<List<WorkspaceResponse>>() {});
//	     for(WorkspaceResponse response : workspaceResponses){
//	    	 System.out.println(response.getLinks().getIde());
//	     }
	     
	     	     	for(WorkspaceResponse response : workspaceResponses){
	     	     		Map<String, Object> map = response.getAdditionalProperties();
	     	     		Map metadata = (Map) map.get("devfile");
	     	     		Map name = (Map) metadata.get("metadata");
	     	     		String nm = (String)name.get("name");
	     	     		System.out.println("name "+nm);
	     	     		
	     		if(nm.contains("lemon") ){
	     			System.out.println(nm);
	     			deleteWorkspace(response.getId());
	     		}
	     		
	     	}
	     System.out.println(workspaceResponses.size());
	}
	
	private void deleteWorkspace(String id) throws Exception{
		String url = "https://che-che.eclipse.yaksha.online/api/workspace/"+id;
		URL url2 = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
		conn.setRequestMethod("DELETE");
		conn.setRequestProperty("Content-Type", "application/json");
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String data = getResponse(conn);
		System.out.println(data);
	}
	
	public String getResponse(HttpURLConnection con) {
		if(con!=null){
			
			try {
				
			   BufferedReader br = 
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));
						
			   String input;
			   String output="";
						
			   while ((input = br.readLine()) != null){
				   output +=input;
			   }
			   br.close();
			   return output;
						
			} catch (IOException e) {
			   e.printStackTrace();
			}
					
		       }
				
		   return null;
	}
	
	@Test
	public void testGetWorkspaces()throws Exception{
		URL url2 = new URL("https://che-che.eclipse.vijaygalaxy.org/api/workspace?skipCount=0&maxItems=100");
		HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	
	     String data = getResponse(conn);
	     List<WorkspaceChe> workspaceResponses = mapper.readValue(data.getBytes(), new TypeReference<List<WorkspaceChe>>() {});
	     System.out.println(workspaceResponses.size());
	     
	  
	}
}
