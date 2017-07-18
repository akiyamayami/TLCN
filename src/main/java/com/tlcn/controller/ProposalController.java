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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.fabric.xmlrpc.base.Array;
import com.tlcn.model.Car;
import com.tlcn.model.ConfirmProposal;
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
import com.tlcn.runnable.SendSms;
import com.tlcn.service.CarService;
import com.tlcn.service.ConfirmProposalService;
import com.tlcn.service.NotifyEventService;
import com.tlcn.service.ProposalService;
import com.tlcn.service.RegisterProposalService;
import com.tlcn.service.SttProposalService;
import com.tlcn.service.TypeProposalService;
import com.tlcn.service.UserService;
import com.tlcn.validator.ProposalValidator;


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
	@Autowired
	private SttProposalService sttProposalService;
	@Autowired
	private ConfirmProposalService confirmProposalService;
	
	
	@Autowired
	private ProposalValidator proposaValidator;
	
	/*
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(proposaValidator);
	}
	*/
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
		//Thread y = new Thread(new SendSms());
		//y.start();
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
		if(result.hasErrors()){
			return "redirect:/create-proposal";
		}
		saveProposal(proposal,GetUser(),request);
		return "redirect:/";
	}

	// page change-proposal
	@RequestMapping(value = "/change-proposal-{proposalID}", method = RequestMethod.GET)
	public String changeProposal(Model model, @PathVariable int proposalID, HttpSession session, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		model.addAttribute("MODE", "MODE_CHANGE_PROPOSAL");
		model.addAttribute("typeForm", "/change-proposal");
		session.setAttribute("proposalID", proposalID);
		if (isProposalOfUser(proposalID,GetUser()) && !isProposalExpired(proposalID)) {
			Proposal x = proposalService.findOne(proposalID);
			showCalendarAndNotify(model,month,year);
			ModelCreateorChangeProposal modelShow = convertProposalToModelShow(x);
			model.addAttribute("Proposal", modelShow);
			// change position car use to top
			showCarWhenChangeProposal(modelShow,model,x);
			return "Index";
		}
		return "redirect:/accessDenied";
	}
	
	@RequestMapping(value = "/change-proposal", method = RequestMethod.POST)
	public String changeProposalPOST(Model model, @ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		int proposalID = (int) session.getAttribute("proposalID");
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
		Proposal proposal = proposalService.findOne(proposalID);
		ModelCreateorChangeProposal modelShow = convertProposalToModelShow(proposal);
		model.addAttribute("Proposal", modelShow);
		showCalendarAndNotify(model,month,year);
		List<Car> listcars = new ArrayList<Car>();
		listcars.add(proposal.getCar());
		model.addAttribute("carsAvailble", listcars);
		return "Index";
	}
	@RequestMapping(value = "/confirm-proposal", method = RequestMethod.POST)
	public String confirmProposalPOST(Model model,@ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		model.addAttribute("MODE", "MODE_CONFIRM_PROPOSAL");
		model.addAttribute("typeForm", "/confirm-proposal");
		int proposalID = (int) session.getAttribute("proposalID");
		Proposal x = proposalService.findOne(proposalID);
		approveProposal(x);
		return "Index";
	}

	private void approveProposal(Proposal proposal) {
		proposal.setStt(sttProposalService.findOne(1));
		confirmProposalService.save(new ConfirmProposal(GetUser(), proposal, Calendar.getInstance().getTime()));
		// notify to user, driver
		
	}

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
			Car carRegistered = carService.findOne(model.getCarID());
			long count = carService.findListCarAvailable().parallelStream().filter(x -> x.equals(carRegistered)).count();
			Proposal proposalnew = null;
			if(count != 0)
				proposalnew = new Proposal(typeProposalService.findOne(1),model.getName(),model.getDetail(),model.getUsefromdate(),model.getUsetodate(),sttProposalService.findOne(0),carRegistered);
			else{
				//hacker detected
				return;
			}
			proposalService.save(proposalnew);
			System.out.println(proposalnew.getProposalID());
			MultipartFile file = model.getFile();
			if(!file.isEmpty()){
				String location = request.getServletContext().getRealPath("static") + "/file/";
				String name = file.getOriginalFilename();
				String namefile = proposalnew.getProposalID() + name.substring(name.lastIndexOf("."),name.length());
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
			Proposal old = proposalService.findOne(proposalID);
			Proposal proposal = old;
			proposal.setName(model.getName());
			proposal.setDetail(model.getDetail());
			proposal.setType(typeProposalService.findOne(2));
			MultipartFile file = model.getFile();
			if(!file.isEmpty())
			{
				String location = request.getServletContext().getRealPath("static") + "/file/";
				String name = file.getOriginalFilename();
				String namefile = proposal.getProposalID() + name.substring(name.lastIndexOf("."),name.length());
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
			// only allow changing car when proposal not confirm yet
			if(proposal.getStt().getName().equals("Chưa Duyệt"))
				proposal.setCar(carService.findOne(model.getCarID()));
			// if proposal change is equal to old return
			if(proposal.equals(old))
				System.out.println("Proposal không có chỉnh sữa gì");
			if(proposal.getStt().getName().equals("Đã Duyệt")){
				// notify old proposal have been canceled to user,P.TBVT, driver 
				// and set new proposal to not confirm
				proposal.setStt(sttProposalService.findOne(0));// set not comfirm
				// set notify
			}
			proposalService.save(proposal);
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
		Proposal proposal = proposalService.findOne(proposalID);
		notifyEventService.save(new NotifyEvent(Calendar.getInstance(),proposal, userService.findOne("akiyamayami1@gmail.com")));
		notifyEventService.save(new NotifyEvent(Calendar.getInstance(),proposal, userService.findOne("lyphucloi.it@gmail.com")));
	}
	public void showCalendarAndNotify(Model model, String month, String year){
		model.addAttribute("listNotify", notifyEventService.getListNotifyNewest(GetUser()));
		model.addAttribute("calendar", createCalendar(month,year));
	}
	
	public void sendNotify(List<User> listuser){
		Thread nThread = new Thread(new SendEmail(listuser));
		nThread.start();
	}
	
	public void showCarWhenChangeProposal(ModelCreateorChangeProposal modelShow, Model model, Proposal proposal){
		List<Car> carShow = carService.findListCarAvailable();
		List<Car> listcars = new ArrayList<>();
		Car trash = carService.findOne(modelShow.getCarID());
		// if proposal has been confirmed show only car proposal registered
		// else show car available and car proposal registered
		if(proposal.getStt().getName().equals("Đã Duyệt")){
			listcars.add(trash);
			model.addAttribute("carsAvailble", listcars);
		}
		else{
			carShow.remove(trash);
			listcars.add(trash);
			listcars.addAll(carShow);			
			model.addAttribute("carsAvailble", listcars);
		}
		
		
	}
	
	public boolean isProposalExpired(int ProposalID){
		Proposal x = proposalService.findOne(ProposalID);
		Calendar now = Calendar.getInstance();
		if(x.getUsetodate().getTime() < now.getTime().getTime())
			return true;
		return false;
	}
}
