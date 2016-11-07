package com.pivision.util;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
 
public class MailMail
{
	private MailSender mailSender;
	private VelocityEngine velocityEngine;
 
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
 
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}



	public void sendMail(String from, String to, String subject, String msg) {
 
	try
	{
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
}