package com.assessment.common.web;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
@Component
public class CookieServiceInterceptor extends HandlerInterceptorAdapter{

	
	
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
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        Collection<String> setCookieHeaders = response.getHeaders(HttpHeaders.SET_COOKIE);

        if (setCookieHeaders.isEmpty()){
        	setNoSiteCookie(response);
        }
            //return;
        boolean firstHeader = true;
        String hed = response.getHeader("Set-Cookie");
        for (String header : setCookieHeaders) {
            if (firstHeader) {
            	if(hed != null && hed.trim().length() > 0){
            			if(!hed.toLowerCase().contains("samesite")){
            				hed += ";SameSite=None";
            			}
            			if(!hed.toLowerCase().contains("secure")){
            				hed += ";Secure";
            			}
            		response.setHeader("Set-Cookie", hed);
            	}
            	else if(hed == null  || hed.trim().length() == 0){
            		response.setHeader("Set-Cookie", "SameSite=None;Secure");
            	}
            	
                firstHeader = false;
                continue;
            }
            
            
            if(hed != null && hed.trim().length() > 0){
    			if(!hed.toLowerCase().contains("samesite")){
    				hed += ";SameSite=None";
    			}
    			if(!hed.toLowerCase().contains("secure")){
    				hed += ";Secure";
    			}
    		response.addHeader("Set-Cookie", hed);
	    	}
	    	else if(hed == null  || hed.trim().length() == 0){
	    		response.addHeader("Set-Cookie", "SameSite=None;Secure;");
	    	}
        }

//        final String sameSiteAttribute = "; SameSite=None";
//        final String secureAttribute = "; Secure";
//
//        Optional.ofNullable(setCookieHeaders)
//                .map(Collection::stream)
//                .orElseGet(Stream::empty)
//                .filter(StringUtils::isNotBlank)
//                .filter(header -> !header.toLowerCase().contains("samesite"))
//                .map(header -> header.concat(sameSiteAttribute))
//                .map(header -> {
//                    if (header.toLowerCase().contains("secure")) {
//                        return header;
//                    } else {
//                        return header.concat(secureAttribute);
//                    }
//                })
//                .forEach(finalHeader -> response.setHeader(HttpHeaders.SET_COOKIE, finalHeader));
    }
}
