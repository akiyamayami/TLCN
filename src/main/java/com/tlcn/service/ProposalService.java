package com.tlcn.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.ProposalRepository;
import com.tlcn.dao.UserRespository;
import com.tlcn.model.ModelCreateorChangeProposal;
import com.tlcn.model.ModelFilterProposal;
import com.tlcn.model.Proposal;
import com.tlcn.model.User;

@Service
public class ProposalService {
	@Autowired
	private ProposalRepository proposalRepository;
	
	
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
	public void deleteProposal(int proposalID){
		// code here
	}
	private List<Proposal> getListFilter(ModelFilterProposal filter, User user){
		Date datecreate = filter.getDatecreate();
		String type = filter.getType();
		int stt = filter.getStt();
		if(user != null){
			if(datecreate != null && stt != -1 && type != null)
				return proposalRepository.LPF_all(datecreate, type, stt);
			if(datecreate != null && stt == -1 && type != null)
				return proposalRepository.LPF_datecreate_and_type(datecreate, type);
			if(datecreate != null && stt != -1 && type == null)
				return proposalRepository.LPF_datecreate_and_stt(datecreate, stt);
			if(datecreate == null && stt != -1 && type != null)
				return proposalRepository.LPF_type_stt(type, stt);
			if(datecreate != null)
				return proposalRepository.LPF_datecreate(datecreate);
			if(type != null)
				return proposalRepository.LPF_type(type);
			else
				return proposalRepository.LPF_stt(stt);
		}
		else{
			if(datecreate != null && stt != -1 && type != null)
				return proposalRepository.LPF_all_of_user(datecreate, type, stt, user);
			if(datecreate != null && stt == -1 && type != null)
				return proposalRepository.LPF_datecreate_and_type_of_user(datecreate, type, user);
			if(datecreate != null && stt != -1 && type == null)
				return proposalRepository.LPF_datecreate_and_stt_of_user(datecreate, stt, user);
			if(datecreate == null && stt != -1 && type != null)
				return proposalRepository.LPF_type_stt_of_user(type, stt, user);
			if(datecreate != null)
				return proposalRepository.LPF_datecreate_of_user(datecreate, user);
			if(type != null)
				return proposalRepository.LPF_type_of_user(type, user);
			else
				return proposalRepository.LPF_stt_of_user(stt, user);
		}
		
	}
	
	public boolean check_User_Owned_Proposal_Or_Not(int proposalID, User user){
		if(proposalRepository.checkProposalOwnedByUserOrNot(proposalID, user) == 0){
			return false;
		}
		return true;
	}
	
}