package com.tlcn.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="registerproposal")
public class RegisterProposal implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumn(name = "emailuser")
	private User user;
	
	@Id
	@OneToOne
	@JoinColumn(name="proposalID")
	private Proposal proposal;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateregiter;

	public RegisterProposal() {
		super();
	}

	public RegisterProposal(User user, Proposal proposal, Date dateregiter) {
		super();
		this.user = user;
		this.proposal = proposal;
		this.dateregiter = dateregiter;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public Date getDateregiter() {
		return dateregiter;
	}

	public void setDateregiter(Date dateregiter) {
		this.dateregiter = dateregiter;
	}
	
	
}
