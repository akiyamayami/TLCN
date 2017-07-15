package com.tlcn.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tlcn.model.Car;
import com.tlcn.model.ModelCalendar;
import com.tlcn.model.ModelCreateorChangeProposal;
import com.tlcn.model.ModelFilterProposal;
import com.tlcn.model.ModelShowNotify;
import com.tlcn.model.NotifyEvent;
import com.tlcn.model.Proposal;
import com.tlcn.model.RegisterProposal;
import com.tlcn.model.TypeProposal;
import com.tlcn.model.User;
import com.tlcn.runnable.SendEmail;
import com.tlcn.service.CarService;
import com.tlcn.service.NotifyEventService;
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
	@Autowired
	private NotifyEventService notifyEventService;
	
	
	
	public ProposalController() {
		super();
	}

	// page find-proposal
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findpropsal(Model model, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		if (checkUserhasAuthority("CHANGE_PROPOSAL")) {
			// access to mode find-my-proposal for normal user
			model.addAttribute("MODE", "MODE_FIND_MY_PROPOSAL");
			model.addAttribute("listProposal",
					proposalService.findProposalofuser(GetUser()));
		} else {
			// mode find-all-proposal for P.TBVT and BGM
			model.addAttribute("MODE", "MODE_FIND_PROPOSAL");
			model.addAttribute("listProposal", proposalService.findAll());
		}
		
		showCalendarAndNotify(model,month,year);
		model.addAttribute("filter-model", new ModelFilterProposal(null,"0",-1));
		
		return "Index";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String filterproposal(Model model, @ModelAttribute("filter-model") ModelFilterProposal filterproposal,
			BindingResult result) {
		if (checkUserhasAuthority("CHANGE_PROPOSAL")) {
			// access to mode find-my-proposal for normal user
			model.addAttribute("MODE", "MODE_FIND_MY_PROPOSAL");
			model.addAttribute("listProposal",
					proposalService.listProposalFind(filterproposal, GetUser()));
		} else {
			// mode find-all-proposal for P.TBVT and BGM
			model.addAttribute("MODE", "MODE_FIND_PROPOSAL");
			model.addAttribute("listProposal", proposalService.listProposalFind(filterproposal,null));
		}
		showCalendarAndNotify(model,null,null);
		System.out.println("date = " + filterproposal.getDatecreate() + "type = " + filterproposal.getType() + "stt = " + filterproposal.getStt());
		model.addAttribute("filter-model", filterproposal);
		
		return "Index";
	}

	// page create-proposal
	@RequestMapping(value = "/create-proposal", method = RequestMethod.GET)
	public String createProposal(Model model, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		model.addAttribute("MODE", "MODE_CREATE_PROPOSAL");
		model.addAttribute("typeForm", "/create-proposal");
		model.addAttribute("Proposal", new ModelCreateorChangeProposal());
		model.addAttribute("carsAvailble", carService.findListCarAvailable());
		showCalendarAndNotify(model,month,year);
		return "Index";
	}

	@RequestMapping(value = "/create-proposal", method = RequestMethod.POST)
	public String createProposalPOST(Model model, @ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request) {
		saveProposal(proposal,GetUser(),request);
		return "redirect:/";
	}

	// page change-proposal
	@RequestMapping(value = "/change-proposal-{proposalID}", method = RequestMethod.GET)
	public String changeProposal(Model model, @PathVariable int proposalID, HttpSession session, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		model.addAttribute("MODE", "MODE_CHANGE_PROPOSAL");
		model.addAttribute("typeForm", "/change-proposal");
		session.setAttribute("proposalID", proposalID);
		if (isProposalOfUser(proposalID,GetUser())) {
			ModelCreateorChangeProposal modelShow = convertProposalToModelShow(proposalService.findOne(proposalID));
			model.addAttribute("Proposal", modelShow);
			// change position car use to top
			List<Car> carShow = carService.findListCarAvailable();
			List<Car> listcars = new ArrayList<>();
			Car trash = carService.findOne(modelShow.getCarID());
			carShow.remove(trash);
			listcars.add(trash);
			listcars.addAll(carShow);			
			model.addAttribute("carsAvailble", listcars);
			showCalendarAndNotify(model,month,year);
			return "Index";
		}
		return "redirect:/accessDenied";
	}
	
	@RequestMapping(value = "/change-proposal", method = RequestMethod.POST)
	public String changeProposalPOST(Model model, @ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		int proposalID = (int) session.getAttribute("proposalID");
		System.out.println(proposalID);
		if (isProposalOfUser(proposalID,GetUser())) {
			updateProposal(proposalID,proposal,request);
			return "redirect:/";
		}
		return "redirect:/accessDenied";
	}

	// page confirm(see)-proposal
	@RequestMapping(value = "/confirm-proposal-{proposalID}", method = RequestMethod.GET)
	public String confirmProposal(Model model, @PathVariable int proposalID, HttpSession session, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		model.addAttribute("MODE", "MODE_CONFIRM_PROPOSAL");
		model.addAttribute("typeForm", "/confirm-proposal");
		session.setAttribute("proposalID", proposalID);
		if (proposalService.check_User_Owned_Proposal_Or_Not(proposalID,GetUser())) {
			//model.addAttribute("proposalInfo", proposalService.findOne(proposalID));
			showCalendarAndNotify(model,month,year);
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
	public User GetUser(){
		return userService.findOne(getUserLogin().getUsername());
	}
	// check user has Authority
	public boolean checkUserhasAuthority(String Authority) {
		System.out.println(getUserLogin().getUsername());
		return getUserLogin().getAuthorities().contains(new SimpleGrantedAuthority(Authority));
	}
	
	
	public void saveProposal(ModelCreateorChangeProposal model, User user, HttpServletRequest request){
		try{
			Proposal proposalnew = new Proposal(typeProposalService.findOne(1),model.getName(),model.getDetail(),model.getUsefromdate(),model.getUsetodate(),0,carService.findOne(model.getCarID()));
			proposalService.save(proposalnew);
			System.out.println(proposalnew.getProposalID());
			MultipartFile file = model.getFile();
			if(!file.isEmpty()){
				String location = request.getServletContext().getRealPath("static") + "/file/";
				System.out.println(location);
				String name = file.getOriginalFilename();
				System.out.println(name);
				String namefile = proposalnew.getProposalID() + name.substring(name.lastIndexOf("."),name.length());
				System.out.println(namefile);
				uploadfile(file,location,namefile);
				proposalnew.setFile(namefile);
			}
			RegisterProposal register = new RegisterProposal(user, proposalnew, new Date());
			registerProposalService.save(register);
			proposalnew.setUserregister(register);
			proposalService.save(proposalnew);
			addNotify("create",proposalnew.getProposalID());
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void updateProposal(int proposalID,ModelCreateorChangeProposal model, HttpServletRequest request){
		try{
			Proposal proposal = proposalService.findOne(proposalID);
			proposal.setName(model.getName());
			proposal.setDetail(model.getDetail());
			proposal.setType(typeProposalService.findOne(2));
			System.out.println("fiel is =" + model.getFile());
			MultipartFile file = model.getFile();
			if(!file.isEmpty())
			{
				String location = request.getServletContext().getRealPath("static") + "/file/";
				System.out.println(location);
				String name = file.getOriginalFilename();
				System.out.println(name);
				String namefile = proposal.getProposalID() + name.substring(name.lastIndexOf("."),name.length());
				System.out.println(namefile);
				uploadfile(file,location,namefile);
			}
			if(model.getTypedateuse().equals("manyday"))
			{
				proposal.setUsefromdate(model.getUsefromdate());
				proposal.setUsetodate(model.getUsetodate());
			}
			else{
				proposal.setUsefromdate(model.getUseindate());
				proposal.setUsetodate(model.getUseindate());
			}
			proposal.setCar(carService.findOne(model.getCarID()));
			System.out.println(proposal.getType().getName());
			proposalService.save(proposal);
			if(proposal.getStt() == 1){
				addNotify("change",proposal.getProposalID());
			}
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
	
	public boolean isProposalOfUser(int proposalID,User user){
		return proposalService.check_User_Owned_Proposal_Or_Not(proposalID,user);
	}
	
	public ModelCreateorChangeProposal convertProposalToModelShow( Proposal proposal){
		ModelCreateorChangeProposal modelShow;
		Calendar x = Calendar.getInstance();
		boolean isUseInDate = proposal.getUsefromdate().equals(proposal.getUsetodate());
		if(isUseInDate){
			x.setTime(proposal.getUsefromdate());
			modelShow = new ModelCreateorChangeProposal(proposal.getProposalID(),proposal.getName(),
					proposal.getDetail(),"inday",x.getTime(),null,proposal.getCar().getCarID());
		}
		else{
			Calendar y = Calendar.getInstance();
			x.setTime(proposal.getUsefromdate());
			y.setTime(proposal.getUsetodate());
			modelShow = new ModelCreateorChangeProposal(proposal.getProposalID(),proposal.getName(),
					proposal.getDetail(),"manyday",x.getTime(),y.getTime(),null,proposal.getCar().getCarID());
		}
		return modelShow;
	}
	
	public String createCalendar(String month, String year){
		System.out.println(month+"xx" + year);
		Calendar now = Calendar.getInstance();
		int y,m;
		if(month == null || year == null)
		{
			m = now.get(Calendar.MONTH);
			y = now.get(Calendar.YEAR);
			now.set(y, m, 1);
		}else{
			now.clear();
			y = Integer.parseInt(year);
			m = Integer.parseInt(month);
			now.set(y, m, 1);
		}
		String html = new ModelCalendar().createCalendar(y, m, now);
		return html;
	}
	
	public void addNotify(String type, int proposalID){
		switch(type){
			case "create":
				notifyEventService.save(new NotifyEvent(Calendar.getInstance(),
						typeProposalService.findOne(1), proposalID, 0, userService.findOne("akiyamayami1@gmail.com")));
				notifyEventService.save(new NotifyEvent(Calendar.getInstance(),
						typeProposalService.findOne(1), proposalID, 0, userService.findOne("lyphucloi.it@gmail.com")));
				//List<User> listuser = new ArrayList<>();
				//listuser.add(userService.findOne("lyphucloi.it@gmail.com"));
				//listuser.add(userService.findOne("akiyamayami1@gmail.com"));
				//SendNotify(listuser);
				break;
			case "change":
				notifyEventService.save(new NotifyEvent(Calendar.getInstance(),
						typeProposalService.findOne(2), proposalID, 0, userService.findOne("akiyamayami1@gmail.com")));
				notifyEventService.save(new NotifyEvent(Calendar.getInstance(),
						typeProposalService.findOne(2), proposalID, 0, userService.findOne("lyphucloi.it@gmail.com")));
				break;
			case "cancel":
				notifyEventService.save(new NotifyEvent(Calendar.getInstance(),
						typeProposalService.findOne(3), proposalID, 0, userService.findOne("akiyamayami1@gmail.com")));
				notifyEventService.save(new NotifyEvent(Calendar.getInstance(),
						typeProposalService.findOne(3), proposalID, 0, userService.findOne("lyphucloi.it@gmail.com")));
				break;
		}
	}
	public void showCalendarAndNotify(Model model, String month, String year){
		model.addAttribute("listNotify", notifyEventService.getListNotifyNewest(GetUser()));
		model.addAttribute("calendar", createCalendar(month,year));
	}
	public void SendNotify(List<User> listuser){
		Thread nThread = new Thread(new SendEmail(listuser));
		nThread.start();
	}
}
