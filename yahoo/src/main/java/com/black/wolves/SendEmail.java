/**
 * 
 */
package com.black.wolves;

import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author gaston.dapice
 *
 */
public class SendEmail {

	private MailSender mailSender;
	private MailSender yahooMailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setYahooMailSender(MailSender yahooMailSender) {
		this.yahooMailSender = yahooMailSender;
	}

	public void sendEMailToGmailAccount(String from, String to, String subject, String msg) {
		// creating message
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}

	public void sendEMailFromYahoo(ApplicationContext applicationContext) {
		SimpleMailMessage simpleMailMessage = (SimpleMailMessage) applicationContext.getBean("sendMailFromYahoo");
		SimpleMailMessage mailMessage = new SimpleMailMessage(simpleMailMessage);
		yahooMailSender.send(mailMessage);

	}
}
