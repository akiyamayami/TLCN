package com.tlcn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	//page find-proposal
	@RequestMapping(value="/find-propposal", method = RequestMethod.GET)
	public String findpropsal(Model model){
		if(checkUserhasAuthority("CHANGE_PROPOSAL")){
			// access to mode find-my-proposal for normal user
			model.addAttribute("MODE", "MODE_FIND_MY_PROPOSAL");
		}else{
			// mode find-all-proposal for P.TBVT and BGM
			model.addAttribute("MODE", "MODE_FIND_PROPOSAL");
		}
		return "Index";
	}
	// page create-proposal
	@RequestMapping(value="/create-proposal", method = RequestMethod.GET)
	public String createProposal(Model model){
		model.addAttribute("MODE", "MODE_CREATE_PROPOSAL");
		return "Index";
	}
	// page change-proposal
	@RequestMapping(value="/change-proposal", method = RequestMethod.GET)
	public String changeProposal(Model model){
		model.addAttribute("MODE", "MODE_CHANGE_PROPOSAL");
		return "Index";
	}
	// page change-proposal
	@RequestMapping(value="/confirm-proposal", method = RequestMethod.GET)
	public String confirmProposal(Model model){
		model.addAttribute("MODE", "MODE_CONFIRM_PROPOSAL");
		return "Index";
	}
	// page Login
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String Login(){
		return "Login";
	}
	
	// forward to page login
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String forwardLogin(){
		return "redirect:/login";
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
	// check user has Authority
	public boolean checkUserhasAuthority(String Authority){
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Authority));
	}
}
