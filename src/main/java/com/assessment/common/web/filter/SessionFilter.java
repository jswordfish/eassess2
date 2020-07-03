package com.assessment.common.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assessment.data.User;

public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// ...
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//
	}
	
	private void setNoSiteCookie(ServletResponse response){
		HttpServletResponse res = (HttpServletResponse) response;
		String hed = res.getHeader("Set-Cookie");
		if(hed != null && hed.trim().length() > 0){
			if(!hed.toLowerCase().contains("samesite")){
				hed += ";SameSite=None";
			}
			if(!hed.toLowerCase().contains("secure")){
				hed += ";Secure";
			}
			res.setHeader("Set-Cookie", hed);
		}
		else if(hed == null  || hed.trim().length() == 0){
			res.setHeader("Set-Cookie", "SameSite=None;Secure");
		}
	}

	@Override
	public void doFilter(ServletRequest request, 
               ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		
//		HttpSession session = ((HttpServletRequest) request).getSession();
//		if (request.getParameter("JSESSIONID") != null) {
//		    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
//		    ((HttpServletResponse) response).addCookie(userCookie);
//		} else {
//		    String sessionId = session.getId();
//		    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
//		    ((HttpServletResponse) response).addCookie(userCookie);
//		}
		//System.out.println("123 ");
		
		//setNoSiteCookie(response);
		
		
		
		
		
		
		
		try {
			String page = ((HttpServletRequest)request).getRequestURI();
			//System.out.println("page is "+page);
			
			if(page.endsWith("healthcheck") ){
				chain.doFilter(request, response);
			}
			
			if(page.endsWith("publicTest2") || page.endsWith("showFailures") ){
				chain.doFilter(request, response);
			}
			
			if(page.endsWith("verify") || page.endsWith("/hackathon") || page.endsWith("multifileresults") || page.endsWith("showComprehensiveReportForCourse")){
				chain.doFilter(request, response);
			}
			else if(page.endsWith("/findLevel1Qs") || page.endsWith("/findLevel2Qs")){
				chain.doFilter(request, response);
			}
			else if(page.endsWith("/searchQsWs") || page.endsWith("/init") ||  page.endsWith("/validateotp") || page.endsWith("/savenewpassword") || page.endsWith("/getotp") || page.endsWith("/login") || page.endsWith("/authenticate") || page.endsWith("publicTest") || page.contains("setUpTenant") || page.contains("downloadUserSessionReportsForTest")) {
				chain.doFilter(request, response);
			}
			else if(page.endsWith("/testsByTag") || page.endsWith("/recommendedSkillsByTest") || page.endsWith("lmsadmin") || page.endsWith("getAssessmentURLForLMSLearner") || page.endsWith("getRecommendationsForTestForLmS")){
				chain.doFilter(request, response);
			}
			else if(page.contains("scripts_login")  || page.contains("images") || page.contains("css") || page.contains("scripts") || page.contains("fonts") || page.contains("html") || page.contains("startTestSession") || page.contains("yakshacode") || page.contains("compile") || page.contains("savePracticeCode")){
				chain.doFilter(request, response);
			}
			else if(page.contains("multiFileReports")){
				chain.doFilter(request, response);
			}
			else if(page.contains("metadata") || page.contains("yakshaspconsumerendpoint") || page.contains("yakshasplogoff") || page.contains("attrs") || page.contains("doLogin") || page.contains("bulkResults")){
				chain.doFilter(request, response);
			}
			else {
				 User user = (User) ((HttpServletRequest)request).getSession().getAttribute("user");
				 	if(user == null) {
				 		System.out.println("no user info and hence logging out");
				 		((HttpServletResponse)response).sendRedirect("login");
				 	}
				 	else {
				 		
				 		

				 		chain.doFilter(request, response);
				 	}
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			//((HttpServletResponse)response).sendRedirect("login");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString(); // stack trace as a string
			((HttpServletRequest)request).getSession().setAttribute("errorStack", sStackTrace);
			((HttpServletResponse)response).sendRedirect("problem");
		}

	}

}
