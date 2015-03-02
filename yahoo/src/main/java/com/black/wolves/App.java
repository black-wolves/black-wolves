/**
 * 
 */
package com.black.wolves;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gaston.dapice
 *
 */
public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-config.xml");
		SendEmail sendEmail = (SendEmail) applicationContext.getBean("sendMail");
		String sender = "javahonk@gmail.com";
		String receiver = "javahonk@gmail.com";
		sendEmail.sendEMailToGmailAccount(sender, receiver,
				"Test email Java Honk", "Mail from spring framwork.");
		System.out.println("success");

		// Pre-configured mail properties to send email from yahoo
		sendEmail.sendEMailFromYahoo(applicationContext);

	}
}
