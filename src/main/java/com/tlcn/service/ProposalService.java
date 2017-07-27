package com.tlcn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.ProposalRepository;
import com.tlcn.dto.ModelFilterProposal;
import com.tlcn.dto.ModelFormProposal;
import com.tlcn.model.Car;
import com.tlcn.model.Proposal;
import com.tlcn.model.SttProposal;
import com.tlcn.model.TypeProposal;
import com.tlcn.model.User;
import com.tlcn.validator.ProposalValidator;


@Service
public class ProposalService {
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private TypeProposalService typeProposalService;
	@Autowired
	private SttProposalService sttProposalService;
	
	public List<Proposal> getListProposalReady(){
		return proposalRepository.listProposalReady();
	}
	
	
	public Proposal findOne(int proposalID){
		return proposalRepository.findOne(proposalID);
	}
	public void save(Proposal proposal){
		proposalRepository.save(proposal);
	}
	public List<Proposal> findAll(){
		List<Proposal> proposals = new ArrayList<>();
		for(Proposal proposal : proposalRepository.findAll()){
			proposals.add(proposal);
		}
		return proposals;
	}
	public List<Proposal> findProposalofuser(User user){
		List<Proposal> proposals = new ArrayList<>();
		for(Proposal proposal : proposalRepository.listProposal_User(user)){
			proposals.add(proposal);
		}
		return proposals;
	}
	public List<Proposal> listProposalFind(ModelFilterProposal filter, User user){
		List<Proposal> proposals = new ArrayList<>();
		for(Proposal proposal : getListFilter(filter,user)){
			proposals.add(proposal);
		}
		return proposals;
	}
	public void confirmProposal(int proprosalID){
		Proposal x = proposalRepository.findOne(proprosalID);
		x.setStt(sttProposalService.findOne(1));
		proposalRepository.save(x);
	}
	public void deleteProposal(int proposalID){
		// code here
	}
	
	public List<Proposal> getListProposalExpired(){
		return proposalRepository.listProposalExpired();
	}
	
	private List<Proposal> getListFilter(ModelFilterProposal filter, User user){
		Date datecreate = filter.getDatecreate();
		int typeNumber = Integer.parseInt(filter.getType());
		TypeProposal type = null;
		if(typeNumber != 0)
			type = typeProposalService.findOne(typeNumber);
		SttProposal stt = sttProposalService.findOne(filter.getStt());
		if(user != null){
			if(datecreate == null && stt == null && type == null)
				return findProposalofuser(user);
			if(datecreate != null && stt != null && type != null)
				return proposalRepository.LPF_all(datecreate, type, stt);
			if(datecreate != null && stt == null && type != null)
				return proposalRepository.LPF_datecreate_and_type(datecreate, type);
			if(datecreate != null && stt != null && type == null)
				return proposalRepository.LPF_datecreate_and_stt(datecreate, stt);
			if(datecreate == null && stt != null && type != null)
				return proposalRepository.LPF_type_stt(type, stt);
			if(datecreate != null)
				return proposalRepository.LPF_datecreate(datecreate);
			if(type != null)
				return proposalRepository.LPF_type(type);
			else
				return proposalRepository.LPF_stt(stt);
		}
		else{
			if(datecreate == null && stt == null && type == null)
				return findAll();
			if(datecreate != null && stt != null && type != null)
				return proposalRepository.LPF_all_of_user(datecreate, type, stt, user);
			if(datecreate != null && stt == null && type != null)
				return proposalRepository.LPF_datecreate_and_type_of_user(datecreate, type, user);
			if(datecreate != null && stt != null && type == null)
				return proposalRepository.LPF_datecreate_and_stt_of_user(datecreate, stt, user);
			if(datecreate == null && stt != null && type != null)
				return proposalRepository.LPF_type_stt_of_user(type, stt, user);
			if(datecreate != null)
				return proposalRepository.LPF_datecreate_of_user(datecreate, user);
			if(type != null)
				return proposalRepository.LPF_type_of_user(type, user);
			else
				return proposalRepository.LPF_stt_of_user(stt, user);
		}
		
	}
	public List<Proposal> getListProposalHaveCarHasBeenUsed(Proposal proposal){
		List<Proposal> listProposal = proposalRepository.getListProposalNotCofirmOfCar(proposal.getCar());
		long timeFrom = getDate(proposal.getUsefromdate(), proposal.getUsefromtime());
		long timeTo = getDate(proposal.getUsetodate(), proposal.getUsetotime());
		List<Proposal> x = listProposal.parallelStream()
				.filter(p -> isBetween(getDate(p.getUsefromdate(), p.getUsefromtime()),getDate(p.getUsetodate(), p.getUsetotime()),timeFrom,timeTo))
				.collect(Collectors.toList());
		return x;
	}
	
	public Proposal isProposalHaveCarWasUsed(Car car, Proposal proposal){
		List<Proposal> listProposal = proposalRepository.getListProposalConfirmOfCar(car);
		long timeFrom =  getDate(proposal.getUsefromdate(), proposal.getUsefromtime());
		long timeTo = getDate(proposal.getUsetodate(), proposal.getUsetotime());
		Proposal x = null;
		// time check is X
		// time Already used is Y 
		// first check X is Between Yfrom and YTo
		// second check Y is Between Xfrom and XTo
		x = listProposal.parallelStream()
				.filter(p -> isBetween(timeFrom,timeTo,getDate(p.getUsefromdate(), p.getUsefromtime()),getDate(p.getUsetodate(), p.getUsetotime()))
						|| isBetween(getDate(p.getUsefromdate(), p.getUsefromtime()),getDate(p.getUsetodate(), p.getUsetotime()),timeFrom,timeTo))
				.findFirst().orElse(null);
		if(x != null){
			System.out.println(" Proposal register car is : " + x.getProposalID());
		}
		return x;
	}
	public boolean check_User_Owned_Proposal_Or_Not(int proposalID, User user){
		if(proposalRepository.checkProposalOwnedByUserOrNot(proposalID, user) == 0){
			return false;
		}
		return true;
	}
	public boolean isBetween(long timeCheckFrom,long timeCheckTo, long timeFrom, long timeTo){
		if((timeCheckFrom >= timeFrom && timeCheckFrom <= timeTo) || (timeCheckTo >= timeFrom && timeCheckTo <= timeTo))
			return true;
		return false;
	}
	public Long getDate(Date date, Date time){
		Calendar Cdate = Calendar.getInstance(),Ctime = Calendar.getInstance(),dateTime = Calendar.getInstance();
		Cdate.setTime(date);
		Ctime.setTime(time);
		dateTime.set(Cdate.get(Calendar.YEAR), Cdate.get(Calendar.MONTH), Cdate.get(Calendar.DATE), 
				Ctime.get(Calendar.HOUR_OF_DAY), Ctime.get(Calendar.MINUTE));
		return dateTime.getTime().getTime();
		
	}
	
	public ModelFormProposal genarateFormProposal(Proposal proposal){
		ModelFormProposal x = new ModelFormProposal(1,"1231231","12312321","!23123213","123123123","12312321");
		return x;
	}
	
	public boolean isInTimeUse(Proposal proposal){
		Calendar now = Calendar.getInstance();
		if(proposal.getUsetodate().getTime() >= now.getTime().getTime() && proposal.getUsefromdate().getTime() <= now.getTime().getTime() && proposal.getStt().getSttproposalID() == 1)
			return true;
		return false;
	}
}
