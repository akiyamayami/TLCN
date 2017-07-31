package com.tlcn.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.tlcn.dto.ModelCalendar;
import com.tlcn.dto.ModelCreateorChangeProposal;
import com.tlcn.dto.ModelFilterProposal;
import com.tlcn.dto.ModelShowNotify;
import com.tlcn.error.NotOwnerOfProposalException;
import com.tlcn.error.ProposalNotFoundException;
import com.tlcn.model.Car;
import com.tlcn.model.ConfirmProposal;
import com.tlcn.model.Driver;
import com.tlcn.model.Proposal;
import com.tlcn.model.RegisterProposal;
import com.tlcn.model.User;
import com.tlcn.runnable.SendEmail;
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
	public String filterproposal(Model model, @ModelAttribute("filter-model") ModelFilterProposal filterproposal) {
		if (checkUserhasAuthority("CHANGE_PROPOSAL")) {
			// access to mode find-my-proposal for normal user
			model.addAttribute("MODE", "MODE_FIND_MY_PROPOSAL");
			model.addAttribute("listProposal",
					proposalService.listProposalFind(filterproposal, GetUser()));
		} else {
			// mode find-all-proposal for P.TBVT and BGM
			System.out.println("BGM or PVT");
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
		model.addAttribute("carsAvailble", carService.getListCarAvailable());
		showCalendarAndNotify(model,month,year);
		return "Index";
	}

	@RequestMapping(value = "/create-proposal", method = RequestMethod.POST)
	public String createProposalPOST(Model model,
			@Valid @ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,BindingResult result, 
			HttpServletRequest request) throws MultipartException, IOException {
		proposaValidator.validate(proposal, result);
		if(checkExceptionCarAndDriver(carService.findOne(proposal.getCarID()),model,"change") || result.hasErrors()){
			model.addAttribute("MODE", "MODE_CREATE_PROPOSAL");
			model.addAttribute("typeForm", "/create-proposal");
			model.addAttribute("Proposal", proposal);
			model.addAttribute("carsAvailble", carService.findListCarAvailableInTime(proposalService.getDate(proposal.getUsefromdate(), proposal.getUsefromtime()),proposalService.getDate(proposal.getUsetodate(), proposal.getUsetotime())));
			showCalendarAndNotify(model,null,null);
			return "Index";
		}
		System.out.println("email user crea : " + GetUser().getEmail());
		saveProposal(proposal,GetUser(),request);
		return "redirect:/";
	}

	// page change-proposal
	@RequestMapping(value = "/change-proposal-{proposalID}", method = RequestMethod.GET)
	public String changeProposal(Model model, @PathVariable int proposalID, HttpSession session, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		model.addAttribute("typeForm", "/change-proposal");
		Proposal x = proposalService.findOne(proposalID);
		if(x == null)
			throw new ProposalNotFoundException();
		if (isProposalOfUser(proposalID,GetUser())) {
			session.setAttribute("proposalID", proposalID);
			showCalendarAndNotify(model,month,year);
			ModelCreateorChangeProposal modelShow = convertProposalToModelShow(x);
			model.addAttribute("Proposal", modelShow);
			// change position car use to top
			showCarWhenChangeProposal(modelShow,model,x);
			if(checkExceptionProposal(x,model))
				model.addAttribute("MODE", "MODE_PROPOSAL_EXPIRED");
			else{
				checkExceptionCarAndDriver(x.getCar(),model,"change");
				isCarAlreadyUsed(x,model);
				model.addAttribute("MODE", "MODE_CHANGE_PROPOSAL");
			}
			return "Index";
		}
		return "redirect:/accessDenied";
	}
	
	@RequestMapping(value = "/change-proposal", method = RequestMethod.POST)
	public String changeProposalPOST(Model model,@Valid @ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		model.addAttribute("MODE", "MODE_CHANGE_PROPOSAL");
		int proposalID = (int) session.getAttribute("proposalID");
		if (isProposalOfUser(proposalID, GetUser())) {
			Proposal x = proposalService.findOne(proposalID);
			proposal.setProposalID(proposalID);
			proposaValidator.validate(proposal, result);
			if (checkExceptionProposal(x, model)) {
				return "redirect:/hackerDetected";
			}
			if (checkExceptionCarAndDriver(carService.findOne(proposal.getCarID()),model,"change") || result.hasErrors()) {
				model.addAttribute("typeForm", "/change-proposal");
				session.setAttribute("proposalID", proposalID);
				showCalendarAndNotify(model, null, null);
				model.addAttribute("Proposal", proposal);
				// change position car use to top
				showCarWhenChangeProposal(proposal, model, x);
				return "Index";
			}
			showCalendarAndNotify(model,null,null);
				
			return (updateProposal(proposalID, proposal, request)) ? "redirect:/" : "Index";
		}
		else
			throw new NotOwnerOfProposalException();
	}

	// page confirm(see)-proposal
	@RequestMapping(value = "/confirm-proposal-{proposalID}", method = RequestMethod.GET)
	public String confirmProposal(Model model, @PathVariable int proposalID, HttpSession session, @RequestParam(value = "m", required=false) String month, @RequestParam(value = "y", required=false) String year) {
		model.addAttribute("typeForm", "/confirm-proposal");
		session.setAttribute("proposalID", proposalID);
		Proposal proposal = proposalService.findOne(proposalID);
		ModelCreateorChangeProposal modelShow = convertProposalToModelShow(proposal);
		model.addAttribute("Proposal", modelShow);
		showCalendarAndNotify(model,month,year);
		show1Car(proposal.getCar(),model);
		if(checkExceptionProposal(proposal,model) || checkExceptionCarAndDriver(proposal.getCar(),model,"confirm"))
		{
			model.addAttribute("MODE", "MODE_PROPOSAL_EXPIRED");
		}
		else if(!isCarAlreadyUsed(proposal, model)){
			model.addAttribute("MODE", "MODE_CONFIRM_PROPOSAL");
		}else model.addAttribute("MODE", "MODE_PROPOSAL_EXPIRED");
		
		return "Index";
	}
	@RequestMapping(value = "/confirm-proposal", method = RequestMethod.POST)
	public String confirmProposalPOST(Model model,@ModelAttribute("Proposal") ModelCreateorChangeProposal proposal,
			BindingResult result, HttpServletRequest request, HttpSession session) {
		model.addAttribute("MODE", "MODE_CONFIRM_PROPOSAL");
		model.addAttribute("typeForm", "/confirm-proposal");
		int proposalID = (int) session.getAttribute("proposalID");
		Proposal x = proposalService.findOne(proposalID);
		System.out.println("check 1");
		if(x.getStt().getSttproposalID() != 1 || !checkExceptionProposal(x,model)){
			System.out.println("check 1");
			if(!isCarAlreadyUsed(x, model))
			{
				checkExceptionCarAndDriver(x.getCar(),model,"confirm");
				approveProposal(x);
				for(Proposal p : proposalService.getListProposalHaveCarHasBeenUsed(x)){
					notifyEventService.addNotifyforUser(p, p.getUserregister().getUser(),"CarWasUsed");
				}
				return "redirect:/";
			}else{
				return "redirect:/confirm-proposal-" + proposalID;
			}
		}
		return "redirect:/hackerDetected";
	}
	
	@RequestMapping(value = "/cancel-proposal", method = RequestMethod.GET)
	public String cancelProposal(Model model, HttpSession session) {
		int proposalID = (int) session.getAttribute("proposalID");
		Proposal proposal = proposalService.findOne(proposalID);
		if(proposal == null)
			throw new ProposalNotFoundException();
		if(isConfirmProposal(proposal)){
			proposal.setType(typeProposalService.findOne(3));
			proposal.setStt(sttProposalService.findOne(1));
			proposalService.save(proposal);
			addNotify("cancel", proposal);
		}else{
			proposal.setType(typeProposalService.findOne(3));
			proposal.setStt(sttProposalService.findOne(1));
			proposalService.save(proposal);
		}
		return "redirect:/";
	}
	@RequestMapping(value = "/cancel-proposal-{proposalID}", method = RequestMethod.GET)
	public String cancelProposalID(Model model, HttpSession session, @PathVariable("proposalID") int proposalID) {
		session.setAttribute("proposalID", proposalID);
		return "redirect:/cancel-proposal";
	}
	
	private void approveProposal(Proposal proposal) {
		proposal.setStt(sttProposalService.findOne(1));
		ConfirmProposal confirmproposal = new ConfirmProposal(GetUser(), proposal, Calendar.getInstance().getTime());
		confirmProposalService.save(confirmproposal);
		proposal.setInfoconfirm(confirmproposal);
		proposalService.save(proposal);
		addNotify("confirm",proposal);
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
	
	//Save Proposal
	public void saveProposal(ModelCreateorChangeProposal model, User user, HttpServletRequest request) throws MultipartException, IOException{
			System.out.println("Save Proposal");
			Car carRegistered = carService.findOne(model.getCarID());
			long count = carService.findListCarAvailableInTime(proposalService.getDate(model.getUsefromdate(), model.getUsefromtime()),proposalService.getDate(model.getUsetodate(), model.getUsetotime()))
					.parallelStream().filter(x -> x.equals(carRegistered)).count();
			Proposal proposalnew = null;
			if(count != 0)
			{
				proposalnew = new Proposal(typeProposalService.findOne(1),model.getName(),model.getDetail(),
						model.getDestination(),model.getPickuplocation(),model.getPickuptime(),
						model.getUsefromdate(),model.getUsefromtime(),model.getUsetodate(),model.getUsetotime(),
						sttProposalService.findOne(0),carService.findOne(model.getCarID()));
			}
			else{
				//hacker detected
				return;
			}
			proposalService.save(proposalnew);
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
			addNotify("create",proposalnew);
		
	}
	
	public boolean updateProposal(int proposalID,ModelCreateorChangeProposal model, HttpServletRequest request){
		try{
			Proposal proposal = proposalService.findOne(proposalID);
			MultipartFile file = model.getFile();
			if(!file.isEmpty())
			{
				String location = request.getServletContext().getRealPath("static") + "/file/";
				String name = file.getOriginalFilename();
				String namefile = proposal.getProposalID() + name.substring(name.lastIndexOf("."),name.length());
				if(uploadfile(file,location,namefile))
					return false;
				if(proposal.getFile() != null){
					proposal.setFile(namefile);
				}
			}
			System.out.println("fiel is :" + file.isEmpty());
			System.out.println("check is " + proposal.checkEqual(model));
			// if proposal change is equal to old return
			if(proposal.checkEqual(model) && file.isEmpty()){
				System.out.println("Proposal không có chỉnh sữa gì");
				return true;
			}
			proposal.setName(model.getName());
			proposal.setDetail(model.getDetail());
			proposal.setType(typeProposalService.findOne(2));
			proposal.setDestination(model.getDestination());
			proposal.setPickuplocation(model.getPickuplocation());
			proposal.setPickuptime(model.getPickuptime());
			proposal.setUsefromdate(model.getUsefromdate());
			proposal.setUsefromtime(model.getUsefromtime());
			proposal.setUsetodate(model.getUsetodate());
			proposal.setUsetotime(model.getUsetotime());
			// allow changing car when proposal not confirm yet
			if(!isConfirmProposal(proposal))
				proposal.setCar(carService.findOne(model.getCarID()));
			if(isConfirmProposal(proposal)){
				// notify old proposal have been canceled to user,P.TBVT, driver
				// and set new proposal to not confirm
				proposal.setStt(sttProposalService.findOne(0));// set not comfirm
				confirmProposalService.delete(proposal.getInfoconfirm().getConfrimproposalID());
				addNotify("change",proposal);
				// set notify
			}
			proposalService.save(proposal);
			return true;
		}catch (Exception e) {
			e.getMessage();
			return false;
		}
		
	}
	
	public boolean isConfirmProposal(Proposal proposal){
		if(proposal.getStt().getSttproposalID() == 1)
			return true;
		return false;
	}
	
	public boolean uploadfile(MultipartFile file, String localtion, String namefile)
			throws IOException, MultipartException {
		byte[] bytes;
		bytes = file.getBytes();
		File serverFile = new File(localtion + namefile);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(bytes);
		stream.close();
		return true;
	}
	
	public boolean isProposalOfUser(int proposalID,User user){
		return proposalService.check_User_Owned_Proposal_Or_Not(proposalID,user);
	}
	
	public ModelCreateorChangeProposal convertProposalToModelShow( Proposal proposal){
		ModelCreateorChangeProposal modelShow;
		Calendar x = Calendar.getInstance();
		Calendar y = Calendar.getInstance();
		x.setTime(proposal.getUsefromdate());
		y.setTime(proposal.getUsetodate());
		boolean exitstfile = (proposal.getFile() != null) ? true : false;
		modelShow = new ModelCreateorChangeProposal(proposal.getProposalID(),
				proposal.getName(),proposal.getDetail(),proposal.getDestination(),proposal.getPickuplocation(),
				proposal.getPickuptime(),x.getTime(),proposal.getUsefromtime(),
				y.getTime(),proposal.getUsetotime(),proposal.getCar().getCarID(),exitstfile,proposal.getStt());
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
	
	public void addNotify(String type, Proposal proposal){
		switch(type){
			case "confirm":
				notifyEventService.addNotifyforUser(proposal,proposal.getUserregister().getUser(),"");
				break;
			default:
				notifyEventService.addNotifyforUser(proposal,userService.findOne("akiyamayami1@gmail.com"),"");
				notifyEventService.addNotifyforUser(proposal,userService.findOne("lyphucloi.it@gmail.com"),"");
				//notifyEventService.save(new NotifyEvent(Calendar.getInstance(),proposal, userService.findOne("akiyamayami1@gmail.com")));
				//notifyEventService.save(new NotifyEvent(Calendar.getInstance(),proposal, userService.findOne("lyphucloi.it@gmail.com")));
				break;
		}
	}
	
	public void showCalendarAndNotify(Model model, String month, String year){
		List<ModelShowNotify> listNotify = notifyEventService.getListNotifyNewest(GetUser());
		if(listNotify != null && listNotify.size() < 5)
			model.addAttribute("listNotify",listNotify);
		else
			model.addAttribute("listNotify", notifyEventService.getListNotifyNewest(GetUser()).subList(0, 5));
		model.addAttribute("calendar", createCalendar(month,year));
	}
	
	public void sendNotify(List<User> listuser){
		Thread nThread = new Thread(new SendEmail(listuser));
		nThread.start();
	}
	
	public void showCarWhenChangeProposal(ModelCreateorChangeProposal modelShow, Model model, Proposal proposal){
		System.out.println("Show car");
		List<Car> carShow = carService.findListCarAvailableInTime(proposalService.getDate(proposal.getUsefromdate(), proposal.getUsefromtime()),proposalService.getDate(proposal.getUsetodate(), proposal.getUsetotime()));
		System.out.println("list car show "+ carShow.size());
		List<Car> listcars = new ArrayList<>();
		Car trash = carService.findOne(modelShow.getCarID());
		// if proposal has been confirmed or expired show only car proposal registered
		// else show car available and car proposal registered
		System.out.println(proposal.getStt().getSttproposalID());
		if(checkExceptionProposal(proposal,model) || isConfirmProposal(proposal)){
			System.out.println("Exception");
			show1Car(trash,model);
		}
		else{
			checkExceptionCarAndDriver(carService.findOne(modelShow.getCarID()),model,"change");
			System.out.println("Normal");
			carShow.remove(trash);
			listcars.add(trash);
			listcars.addAll(carShow);			
			model.addAttribute("carsAvailble", listcars);
		}
		
		
	}
	
	public void show1Car(Car car, Model model){
		List<Car> listcars = new ArrayList<Car>();
		listcars.add(car);
		model.addAttribute("carsAvailble", listcars);
	}
	
	
	
	public boolean isProposalExpired(Proposal proposal){
		Calendar now = Calendar.getInstance();
		System.out.println(proposal.getUsetodate() +  "+ now = " + now.getTime());
		long timeFrom = proposalService.getDate(proposal.getUsefromdate(),proposal.getUsefromtime());
		long timeTo = proposalService.getDate(proposal.getUsetodate(),proposal.getUsetotime());
		long timeNow = now.getTime().getTime();
		if(proposal.getStt().getSttproposalID() == 1){
			if(timeTo < timeNow)
				return true;
			return false;
		}
		else{
			if(timeFrom < timeNow)
				return true;
			return false;
		}
	}
	public boolean isCarAlreadyUsed(Proposal proposal, Model model){
		Proposal x = proposalService.isProposalHaveCarWasUsed(proposal.getCar(), proposal);
		if(proposal.getStt().getSttproposalID() == 1)
			return false;
		if(x != null){
			System.out.println("Đề nghị :" + proposal.getProposalID() + "sử dụng xe đã được đăng ký");
			model.addAttribute("message", "Xe đăng ký đã được xử dụng");
			return true;
		}
		return false;
	}

	public boolean checkExceptionProposal(Proposal proposal, Model model){
		if(isProposalExpired(proposal)){
			System.out.println("Đề nghị :" + proposal.getProposalID() + "Hết hạn");
			model.addAttribute("message", "Đề nghị này đã hết hạn");
			return true;
		}
		if(proposalService.isInTimeUse(proposal)){
			model.addAttribute("message", "Đề nghị này đang trong thời gian thực hiện");
			System.out.println("Đề nghị :" + proposal.getProposalID() + "Trong thời gian thực hiện");
			return true;
		}
		
		return false;
	}
	public boolean checkExceptionCarAndDriver(Car car, Model model, String type){
		if(type.equals("confirm")){
			switch(car.getSttcar().getSttcarID()){
				case 2:
					model.addAttribute("message", "Xe đăng ký đang được bảo trì");
					return true;
				case 3:
					model.addAttribute("message", "Xe đăng ký đã bị xóa khỏi hệ thống");
					return true;
				case 4:
					model.addAttribute("message", "Xe đăng ký hiện không có tài xế");
					return true;
			}
			Driver driver = car.getDriver();
			if(driver == null){
				model.addAttribute("message", "Xe hiện không có tài xế");
				return true;
			}
			else{
				switch(driver.getSttdriver().getSttdriverID()){
					case 2:
						model.addAttribute("message", "Tài xế xe đăng ký đang nghỉ bệnh/phép");
						return true;
					case 3:
						model.addAttribute("message", "Tài xế xe đăng ký đã nghỉ việc");
						return true;
				}
			}
			return false;
		}
		else{
			switch(car.getSttcar().getSttcarID()){
				case 2:
					model.addAttribute("message", "Xe đăng ký đang được bảo trì, Vui lòng đổi xe khác.");
					return true;
				case 3:
					model.addAttribute("message", "Xe đăng ký đã bị xóa khỏi hệ thống, Vui lòng đổi xe khác.");
					return true;
				case 4:
					model.addAttribute("message", "Xe đăng ký hiện không có tài xế, Vui lòng đổi xe khác.");
					return true;
			}
			Driver driver = car.getDriver();
			if(driver == null){
				model.addAttribute("message", "Xe hiện không có tài xế, Vui lòng đổi xe khác.");
				return true;
			}
			else{
				switch(driver.getSttdriver().getSttdriverID()){
					case 2:
						model.addAttribute("message", "Tài xế xe đăng ký đang nghỉ bệnh/phép, Vui lòng đổi xe khác.");
						return true;
					case 3:
						model.addAttribute("message", "Tài xế xe đăng ký đã nghỉ việc, Vui lòng đổi xe khác.");
						return true;
				}
			}
			return false;
		}
	}
}
