package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class qwe {
	public static void main(String[] args) {
		/*Thread x = new Thread(new Rummble());
		//x.start();
		SendMail send = new SendMail();
		for (int i = 0; i < 3; i++) {
			send.sendmail("lyphucloi.it@gmail.com", "123");
		}
		int i = 0;
		while(true){
			System.out.println(i);
			i++;
		}*/
		
		//x.set(2017, 7, 15,12,10,0);
		//SimpleDateFormat dt = new SimpleDateFormat("hh:mm"); 
		//Date y = dt.format(x.getTime());
		//Date now = x.getTime();
		//.set(2017, 7, 20);
		//Date next = x.getTime();
		//x = Calendar.getInstance();
		//if(now.getTime() <= x.getTime().getTime() && next.getTime() >= x.getTime().getTime() )
		//	System.out.println("x");
		/*
		 List<String> list = Arrays.asList("A","B","C","D");
		 
		 List<String> result = list.stream().collect(Collectors.toList());
		 result.forEach(x -> System.out.println(x));
		 */
		/*String name = "qweqwpdf";
		String ext = name.substring(name.lastIndexOf(".")+1,name.length());
		System.out.println(name.lastIndexOf("."));*/
		
		
		//String regex = "/^([01]?[0-9]|2[0-3]):[0-5][0-9]$/";
		String validatorEmail = "/^[a-zA-Z0-9_-]{3,16}$/";
		
		System.out.println(Pattern.matches(validatorEmail,"1231qwdqw"));
	}
	

}
