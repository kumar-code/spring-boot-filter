package com.code.filters.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.filters.alpha.AlphaCookie;
import com.code.filters.cookie.AlphaCookieFilter;

@RestController
public class FilterController {
	
	@Autowired
	AlphaCookie alphaCookie;
	
	@Autowired
	AlphaCookieFilter alphaCookieFilter;
	
	@GetMapping("override")	// testing endpoint for overriding cookie
	public String cookieTest() {
		this.alphaCookieFilter.persist(new AlphaCookie("newValue"));
		return "Cookie Overriden, { AlphaCookie: newValue }";
	}
	
		
	@GetMapping("cookie")	// to add cookie for testing
	public String addCookieToBrowser(HttpServletResponse httpServletResponse) {
		Cookie cookie = new Cookie("AlphaCookie", "oldValue");
		cookie.setMaxAge(3600);
		httpServletResponse.addCookie(cookie);
		return "Cookie added, { AlphaCookie: old }";
	}
}
