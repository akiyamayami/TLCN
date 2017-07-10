package com.tlcn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlcn.model.ModelFilterProposal;
import com.tlcn.service.ProposalService;
import com.tlcn.service.UserService;

@Controller
@Component
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProposalService proposalService;
	
	
	public LoginController() {
		super();
	}
	
	
	// page check-stt-car
	@RequestMapping(value="/check-stt-cars", method = RequestMethod.GET)
	public String checkSttCar(Model model){
		model.addAttribute("MODE", "MODE_CHECK_STT_CARS");
		return "Index";
	}
	// page find-car
	@RequestMapping(value="/find-cars", method = RequestMethod.GET)
	public String findCar(Model model){
		model.addAttribute("MODE", "MODE_FIND_CARS");
		return "Index";
	}
	// page Login
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String Login(){
		return "Login";
	}
	
	@RequestMapping(value="/fail", method = RequestMethod.GET)
	public String LoginFail(){
		return "Fail";
	}
	// page warnning out time Session
	@RequestMapping(value="/invalidSession", method = RequestMethod.GET)
	public String invalidSession(){
		return "invalidSession";
	}
	//page noitify access denied
	@RequestMapping(value="/accessDenied", method = RequestMethod.GET)
	public String accessDenied(){
		return "accessDenied";
	}
	
}
