package com.twilio.twiliovoice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.voice.Client;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Number;

@RestController
public class TokenController {
 
//	static final String IDENTITY = "alice";
    static final String CALLER_ID = "client:quick_start";
    // Use a valid Twilio number by adding to your account via https://www.twilio.com/console/phone-numbers/verified
    static final String CALLER_NUMBER = "+919458892705";
	
	// Token
	@Autowired
	private Token token;
	
	@Autowired
	private TokenGenerator tokenGenerator;
	
	@RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)
	private String hello() {
		return "Twilio voice quick start server code by Rishabh...";
	}
	
	@RequestMapping(value = "/accessToken", produces = "application/json", method = RequestMethod.GET)
	private Token accessToken(@RequestParam("identity") String identity) {
		
		System.out.println(identity);
		String twilioAccessToken = tokenGenerator.generateToken(identity); 
		System.out.println(identity + ": " + twilioAccessToken);
		token.setAccessToken(twilioAccessToken);
		
		return token;
	}
	
//	@RequestMapping(value = "/makeCall", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
//	private String makeCall(@RequestBody Map<String, String> body) {
//		String to = body.get("to"); 
//		return call(to);
//	}
	
	@RequestMapping(value = "/makeCall", produces = "application/json", method = RequestMethod.GET)
	private String makeCall(@RequestParam("to") String to) {
		return call(to);
	}
	
	private static String call(final String to) {
        VoiceResponse voiceResponse;
        String toXml = null;
        if (to == null || to.isEmpty()) {
            Say say = new Say.Builder("Congratulations! You have made your first call! Good bye.").build();
            voiceResponse = new VoiceResponse.Builder().say(say).build();
        } else if (isPhoneNumber(to)) {
            Number number = new Number.Builder(to).build();
            Dial dial = new Dial.Builder().callerId(CALLER_NUMBER).number(number)
                    .build();
            voiceResponse = new VoiceResponse.Builder().dial(dial).build();
        } else {
            Client client = new Client.Builder(to).build();
            Dial dial = new Dial.Builder().callerId(CALLER_ID).client(client)
                    .build();
            voiceResponse = new VoiceResponse.Builder().dial(dial).build();
        }
        try {
            toXml = voiceResponse.toXml();
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
        return toXml;
    }
	
	private static boolean isPhoneNumber(String s) {
        if (s.length() == 1) {
            return isNumeric(s);
        } else if (s.charAt(0) == '+') {
            return isNumeric(s.substring(1));
        } else {
            return isNumeric(s);
        }
    }
	
	private static boolean isNumeric(String s) {
        int len = s.length();
        for (int i = 0; i < len; ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}










