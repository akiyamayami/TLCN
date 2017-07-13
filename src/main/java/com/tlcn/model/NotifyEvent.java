package com.tlcn.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="notifyevent")
public class NotifyEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notifyID;
	
	private Calendar dateUpEvent;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeID")
	private TypeProposal typeProposal;
	private int proposalID;
	private int stt;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="emailUser")
	private User notifyOfUser;


	public NotifyEvent(Calendar dateUpEvent, TypeProposal typeProposal, int proposalID, int stt, User notifyOfUser) {
		super();
		this.dateUpEvent = dateUpEvent;
		this.typeProposal = typeProposal;
		this.proposalID = proposalID;
		this.stt = stt;
		this.notifyOfUser = notifyOfUser;
	}

	public NotifyEvent(int notifyID, Calendar dateUpEvent, TypeProposal typeProposal, int proposalID, int stt,
			User notifyOfUser) {
		super();
		this.notifyID = notifyID;
		this.dateUpEvent = dateUpEvent;
		this.typeProposal = typeProposal;
		this.proposalID = proposalID;
		this.stt = stt;
		this.notifyOfUser = notifyOfUser;
	}

	public NotifyEvent() {
		super();
	}

	public int getNotifyID() {
		return notifyID;
	}

	public void setNotifyID(int notifyID) {
		this.notifyID = notifyID;
	}





	public Calendar getDateUpEvent() {
		return dateUpEvent;
	}

	public void setDateUpEvent(Calendar dateUpEvent) {
		this.dateUpEvent = dateUpEvent;
	}



	public TypeProposal getTypeProposal() {
		return typeProposal;
	}

	public void setTypeProposal(TypeProposal typeProposal) {
		this.typeProposal = typeProposal;
	}

	public int getProposalID() {
		return proposalID;
	}

	public void setProposalID(int proposalID) {
		this.proposalID = proposalID;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public User getNotifyOfUser() {
		return notifyOfUser;
	}

	public void setNotifyOfUser(User notifyOfUser) {
		this.notifyOfUser = notifyOfUser;
	}

	
	
}


