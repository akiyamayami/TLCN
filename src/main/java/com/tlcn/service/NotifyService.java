package com.tlcn.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tlcn.model.User;

@Service
public class NotifyService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private Environment env;
	
	@Autowired
	private MessageSource messages;
	
	public NotifyService(){
		super();
	}
	
	public void SendMail(SimpleMailMessage message){
		mailSender.send(message);
	}
	
	public SimpleMailMessage constructResetTokenEmail(HttpServletRequest request, Locale locale, String token, User user) {
        String url = getAppUrl(request) + "/change-password?email=" + user.getEmail() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
	public SimpleMailMessage constructNewpassword(HttpServletRequest request, Locale locale,User user, String password) {
        String url = getAppUrl(request) + "/login";
        String message = "This is your password : " + password +", please login and change it.";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
	
	public SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
	
	public String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
