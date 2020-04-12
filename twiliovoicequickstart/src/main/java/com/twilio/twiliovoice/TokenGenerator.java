package com.twilio.twiliovoice;

import org.springframework.stereotype.Component;

import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;

@Component
public class TokenGenerator {

	public String generateToken(final String identity) {
		
		 // Required for all types of tokens
	    String twilioAccountSid = "AC8af4c2a68dacd0dff9a6b046fb623209";
	    String twilioApiKey = "SKf670432ebc6a5c0fd616cea6b3bb1118";
	    String twilioApiSecret = "Z3qajcWcclthwBWEQynSK8oLcfdCbqY5";
	    String twilioAppSid = "";
	    String twilioPushCredentialSidString = "";

	    // Create Voice grant
	    VoiceGrant grant = new VoiceGrant();
	    grant.setOutgoingApplicationSid(twilioAppSid);
	    grant.setPushCredentialSid(twilioPushCredentialSidString);

	    // Create access token
	    AccessToken token = new AccessToken.Builder(
	      twilioAccountSid,
	      twilioApiKey,
	      twilioApiSecret
	    ).identity(identity).grant(grant).build();

	    System.out.println(token.toJwt());
		
		return token.toJwt();
	}
}

