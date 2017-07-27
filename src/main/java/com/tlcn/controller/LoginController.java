package com.tlcn.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

import com.tlcn.dto.ModelFilterProposal;
import com.tlcn.dto.ModelFormProposal;
import com.tlcn.model.Proposal;
import com.tlcn.report.ProposalReport;
import com.tlcn.service.ProposalService;
import com.tlcn.service.UserService;

import net.sf.jasperreports.engine.JRException;

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
	
	
	
	
	// page Login
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String Login(Principal user) throws JRException{
		/*ProposalReport x = new ProposalReport();
		List<ModelFormProposal> proposals = new ArrayList<>();
		proposals.add(proposalService.genarateFormProposal(null));
		x.generatePdfReport(proposals);*/
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
	
	@RequestMapping(value="/hackerDetected", method = RequestMethod.GET)
	public String hackerDetected(){
		return "hackerDetected";
	}
}
