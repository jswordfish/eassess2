package com.eassess.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.common.PropertyConfig;
import com.assessment.common.util.EmailGenericMessageThread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:appContext.xml" })
@Transactional
public class EmailTest {

	@Autowired
	PropertyConfig propertyConfig;

	@Test
	public void sendEMail() throws InterruptedException {

		System.out.println("Emailer called");
		String message = "Hello Gulrez Faroooqui..  !!";
		EmailGenericMessageThread thread = new EmailGenericMessageThread("gulfarooqui1@gmail.com",
				"This is Test Email", message, propertyConfig);
		Thread thread2 = new Thread(thread);
		Thread.sleep(3000);
		thread2.start();
		Thread.sleep(3000);
		System.out.println("Sent successfully::::::::::::::::   ");
	}
}
