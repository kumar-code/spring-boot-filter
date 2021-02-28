package com.code.filters.alpha;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component("alphaCookie")
public class AlphaCookie implements Serializable {

	private static final long serialVersionUID = 1L;

	private String activeCookie;

	public AlphaCookie() {
		super();
	}

	public AlphaCookie(String activeCookie) {
		this();
		this.activeCookie = activeCookie;
	}


	public void override(AlphaCookie alphaCookie) {
		synchronized (this) {
			this.activeCookie = alphaCookie.activeCookie;
		}
	}

	public String getActiveCookie() {
		return this.activeCookie;
	}
}
