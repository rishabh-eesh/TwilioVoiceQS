package com.twilio.twiliovoice;

import org.springframework.stereotype.Component;

@Component
public class Token {
	
	private String accessToken = "";
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
}
