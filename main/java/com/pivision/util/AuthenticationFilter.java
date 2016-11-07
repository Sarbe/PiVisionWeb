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
import org.apache.log4j.MDC;

public class AuthenticationFilter implements Filter {
	private FilterConfig config;
	private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);



	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		MDC.put("RemoteAddress", req.getRemoteAddr());
		Long auth_key =(Long)((HttpServletRequest) req).getSession().getAttribute("AUTH_KEY");
		String role   =(String)((HttpServletRequest) req).getSession().getAttribute("ROLE");

		if ( ( null == auth_key || 0 == auth_key)
				&& (	   (( HttpServletRequest) req).getRequestURI().contains("add")
						|| ((HttpServletRequest) req).getRequestURI().contains("show")
						|| ((HttpServletRequest) req).getRequestURI().contains("Edit") 
						|| ((HttpServletRequest) req).getRequestURI().contains("Modify")
						|| ((HttpServletRequest) req).getRequestURI().contains("modify")
						|| ((HttpServletRequest) req).getRequestURI().contains("publish")
						|| ((HttpServletRequest) req).getRequestURI().contains("upload")
						|| ((HttpServletRequest) req).getRequestURI().contains("presentation")
						//|| ((HttpServletRequest) req).getRequestURI().contains("create")
						|| ((HttpServletRequest) req).getRequestURI().contains("saveScheduleDetails")
						|| ((HttpServletRequest) req).getRequestURI().contains("removeSchedule"))
						
						

						//|| ((HttpServletRequest) req).getServletPath().contains("ProductDetails")
				) {


			logger.info("Blocking access to page"+((HttpServletRequest) req).getRequestURI()+" without login");
			((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath()+ "/user/start.html");
		}else 
			if(  ( null != auth_key && 0 < auth_key) && (null == role || !("admin".equalsIgnoreCase(role))) && 
					( (( HttpServletRequest) req).getRequestURI().contains("AddProduct")
							|| ((HttpServletRequest) req).getRequestURI().contains("Admin")
							|| ((HttpServletRequest) req).getRequestURI().contains("EditOrder")
							|| ((HttpServletRequest) req).getRequestURI().contains("ProductEdit")
							|| ((HttpServletRequest) req).getRequestURI().contains("Modify")
							|| ((HttpServletRequest) req).getRequestURI().contains("ManageOrders") )

					)
			{
				logger.info("Blocking access to page"+((HttpServletRequest) req).getRequestURI()+" without admin role");
				((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath()+ "/user/start.html");
			}

			else {
				chain.doFilter(req, resp);
			}
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {

		config = null;
	}
}
