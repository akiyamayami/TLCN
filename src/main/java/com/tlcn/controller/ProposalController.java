package com.tlcn.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.tlcn.model.Car;
import com.tlcn.model.ModelCreateorChangeProposal;
import com.tlcn.model.ModelFilterProposal;
import com.tlcn.model.Proposal;
import com.tlcn.model.RegisterProposal;
import com.tlcn.model.User;
import com.tlcn.service.CarService;
import com.tlcn.service.ProposalService;
import com.tlcn.service.RegisterProposalService;
import com.tlcn.service.TypeProposalService;
import com.tlcn.service.UserService;

@Controller
@Component
public class ProposalController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProposalService proposalService;
	@Autowired
	private TypeProposalService typeProposalService;
	@Autowired
	private CarService carService;
	@Autowired
	private RegisterProposalService registerProposalService;
		
	
	
	
	public ProposalController() {
		super();
	}

	// page find-proposal
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findpropsal(Model model) {
		if (checkUserhasAuthority("CHANGE_PROPOSAL")) {
			// access to mode find-my-proposal for normal user
			model.addAttribute("MODE", "MODE_FIND_MY_PROPOSAL");
			model.addAttribute("listProposal",
					proposalService.findProposalofuser(userService.findOne(getUserLogin().getUsername())));
		} else {
			// mode find-all-proposal for P.TBVT and BGM
			model.addAttribute("MODE", "MODE_FIND_PROPOSAL");
			model.addAttribute("listProposal", proposalService.findAll());
		}
		model.addAttribute("filter-model", new ModelFilterProposal());
		return "Index";
	}

	////////////
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String filterproposal(Model model, @ModelAttribute("filter-model") ModelFilterProposal filterproposal,
			BindingResult result) {
		
		return "Index";
	}

	// page create-proposal
	@RequestMapping(value = "/create-proposal", method = RequestMethod.GET)
	public String createProposal(Model model) {
		model.addAttribute("MODE", "MODE_CREATE_PROPOSAL");
		model.addAttribute("Proposal", new ModelCreateorChangeProposal());
		model.addAttribute("carsAvailble", carService.findListCarAvailable());
		return "Index";
	}

	@RequestMapping(value = "/create-proposal", method = RequestMethod.POST)
	public String createProposalPOST(Model model, @ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request) {
		//model.addAttribute("MODE", "MODE_CREATE_PROPOSAL");
		///model.addAttribute("Proposal", new ModelCreateorChangeProposal());
		saveProposal(proposal,userService.findOne(getUserLogin().getUsername()),request);
		return "redirect:/";
	}

	// page change-proposal
	@RequestMapping(value = "/change-proposal-{proposalID}", method = RequestMethod.GET)
	public String changeProposal(Model model, @PathVariable int proposalID) {
		model.addAttribute("MODE", "MODE_CHANGE_PROPOSAL");
		if (proposalService.check_User_Owned_Proposal_Or_Not(proposalID,
				userService.findOne(getUserLogin().getUsername()))) {
			model.addAttribute("modelProposal", proposalService.findOne(proposalID));
			return "Index";
		}
		return "redirect:/accessDenied";

	}

	// page confirm(see)-proposal
	@RequestMapping(value = "/confirm-proposal-{proposalID}", method = RequestMethod.GET)
	public String confirmProposal(Model model, @PathVariable int proposalID) {
		model.addAttribute("MODE", "MODE_CONFIRM_PROPOSAL");
		if (proposalService.check_User_Owned_Proposal_Or_Not(proposalID,
				userService.findOne(getUserLogin().getUsername()))) {
			model.addAttribute("modelProposal", proposalService.findOne(proposalID));
			return "Index";
		}
		return "redirect:/accessDenied";
	}

	// delete proposal
	/*
	 * @RequestMapping(value="/delete-proposal-{proposalID}", method =
	 * RequestMethod.GET) public String deleteProposal(Model
	 * model, @PathVariable int proposalID){
	 * if(proposalService.check_User_Owned_Proposal_Or_Not(proposalID,
	 * userService.findOne(getUserLogin().getUsername()))){ proposalService }
	 * return "redirect:/"; }
	 */
	public UserDetails getUserLogin() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	// check user has Authority
	public boolean checkUserhasAuthority(String Authority) {
		System.out.println(getUserLogin().getUsername());
		return getUserLogin().getAuthorities().contains(new SimpleGrantedAuthority(Authority));
	}
	
	public String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public void saveProposal(ModelCreateorChangeProposal model, User user, HttpServletRequest request){
		try{
			Proposal proposalnew = new Proposal(typeProposalService.findOne(1),model.getName(),model.getDetail(),model.getUsefromdate(),model.getUsetodate(),0,carService.findOne(model.getCarID()));
			proposalService.save(proposalnew);
			System.out.println(proposalnew.getProposalID());
			MultipartFile file = model.getFile();
			String location = request.getServletContext().getRealPath("static") + "/file/";
			System.out.println(location);
			String name = file.getOriginalFilename();
			System.out.println(name);
			String namefile = proposalnew.getProposalID() + name.substring(name.lastIndexOf("."),name.length());
			System.out.println(namefile);
			uploadfile(file,location,namefile);
			RegisterProposal register = new RegisterProposal(user, proposalnew, new Date());
			registerProposalService.save(register);
			proposalnew.setUserregister(register);
			proposalnew.setFile(namefile);
			proposalService.save(proposalnew);
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void uploadfile(MultipartFile file, String localtion,String namefile){
		byte[] bytes;
		try {
			bytes = file.getBytes();
			File serverFile = new File(localtion + namefile);
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDataShowProposal(Model model){
		//model.addAttribute("ListCar", arg1);
	}
}
