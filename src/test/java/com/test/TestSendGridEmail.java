package com.test;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class TestSendGridEmail {
	
	@Test
	public void testSendSendGridMail() throws Exception{
		Email from = new Email("test-admin@yaksha.com", "Admin, IIHT Inc");
		Mail mail = new Mail();
		mail.setFrom(from);
		mail.setSubject("test");
		Personalization personalization = new Personalization();
		personalization.addTo(new Email("jatin.sutaria@thev2technologies.com"));
		mail.addPersonalization(personalization);
		Content content = new Content("text/html", "Hello World");
		mail.addContent(content);
		
		
		
		String key = "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";
		SendGrid sg = new SendGrid(key);
		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		//System.out.println(mail.build());
		request.setBody(mail.build());
		Response response = sg.api(request);
		System.out.println("email sent");
	}

}
