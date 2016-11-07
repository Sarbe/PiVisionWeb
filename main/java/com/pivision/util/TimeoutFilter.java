package com.pivision.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class TimeoutFilter implements Filter {  


	private static final Logger logger = Logger.getLogger(TimeoutFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {  
	}  

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {  
		if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {  
			HttpServletRequest requestHttp = (HttpServletRequest) request;  
			HttpServletResponse responseHttp = (HttpServletResponse) response;  

			if (checkSession(requestHttp)) {  
				if (checkResource(requestHttp)) { 


				
					if ( 
					   (( HttpServletRequest) requestHttp).getRequestURI().contains("AddProduct")
						|| ((HttpServletRequest) requestHttp).getRequestURI().contains("Admin")
						|| ((HttpServletRequest) requestHttp).getRequestURI().contains("Edit") 
						|| ((HttpServletRequest) requestHttp).getRequestURI().contains("Modify")
						|| ((HttpServletRequest) requestHttp).getRequestURI().contains("MyOrders")
						|| ((HttpServletRequest) requestHttp).getRequestURI().contains("ManageOrders")
						|| ((HttpServletRequest) requestHttp).getRequestURI().contains("Confirm")

						//|| ((HttpServletRequest) req).getServletPath().contains("ProductDetails")
				)
					{
						logger.info("URL other than home , product or login!");
						String timeoutUrl = requestHttp.getContextPath() + "/" + ConfigPropertiesProvider.getInstance().getValue("TIMEOUT_PAGE");  
						responseHttp.sendRedirect(timeoutUrl);  
						return; 
					}
					else
					{
						logger.info("URL other than home , product or login!");
						String timeoutUrl = requestHttp.getContextPath() + "/" + ConfigPropertiesProvider.getInstance().getValue("HOME_PAGE");  
						responseHttp.sendRedirect(timeoutUrl);  
						return;
					}
					
					 
				}  
			}  
			filterChain.doFilter(request, response); 
		}  
	}

	private boolean checkResource(HttpServletRequest request) {  

		String requestPath = request.getRequestURI();  
		logger.info("Checking URL"+requestPath+":"+requestPath.contains(ConfigPropertiesProvider.getInstance().getValue("TIMEOUT_PAGE"))+ConfigPropertiesProvider.getInstance().getValue("HOME_PAGE"));
		return !(requestPath.contains( ConfigPropertiesProvider.getInstance().getValue("HOME_PAGE"))||requestPath.contains(ConfigPropertiesProvider.getInstance().getValue("TIMEOUT_PAGE")) || requestPath.contains(ConfigPropertiesProvider.getInstance().getValue("LOGIN_PAGE"))  || requestPath.contains(ConfigPropertiesProvider.getInstance().getValue("PRODUCT_PAGE")) || requestPath.contains(ConfigPropertiesProvider.getInstance().getValue("CATEGORY_PAGE")) );  
	}  

	private boolean checkSession(HttpServletRequest request) {  
		return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();  
	} 

	public void destroy() {  
	}  

} 

