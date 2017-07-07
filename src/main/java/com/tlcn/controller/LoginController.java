package com.tlcn.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlcn.service.UserService;

@Controller
@Component
public class LoginController {
	@Autowired
	private UserService userService;

	public LoginController() {
		super();
	}
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String home(){
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for(GrantedAuthority x : userDetails.getAuthorities()){
			System.out.println(x.getAuthority());
		}
		return "Index";
	}
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String Index(){
		return "Login";
	}
	
	@RequestMapping(value="/fail", method = RequestMethod.GET)
	public String LoginFail(){
		return "Fail";
	}
	
	@RequestMapping(value="/invalidSession", method = RequestMethod.GET)
	public String invalidSession(){
		return "invalidSession";
	}
	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public String accessDenied(){
		return "accessDenied";
	}
	
}
