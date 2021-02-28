package com.code.filters.cookie;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.code.filters.alpha.AlphaCookie;

@Component("alphaCookieFilter")
public class AlphaCookieFilter implements Filter {

	private ApplicationContext applicationContext;
	
	public static final ThreadLocal<HttpServletResponse> RESPONSE_HOLDER  = new ThreadLocal<>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		RESPONSE_HOLDER.set((HttpServletResponse) response);
		
		String activeCookie = null;
		
		if (httpServletRequest.getCookies() != null) {
			for (Cookie cookie: httpServletRequest.getCookies()) {
				if ("AlphaCookie".equals(cookie.getName())) {
					activeCookie = cookie.getValue();
				}
			}
		}
		
		this.applicationContext.getBean("alphaCookie", AlphaCookie.class)
							   .override(new AlphaCookie(activeCookie));
		
		chain.doFilter(request, response);
		RESPONSE_HOLDER.remove();
	}
	
	public void persist(AlphaCookie alphaCookie) {
		Cookie cookie = new Cookie("BetaCookie", alphaCookie.getActiveCookie());
//		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(-1);
//		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		RESPONSE_HOLDER.get().addCookie(cookie);
	}
}
