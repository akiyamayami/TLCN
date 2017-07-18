package com.tlcn.model;

public class ModelShowNotify {
	
	private User user;
	private String time;
	private Proposal proposal;
	public ModelShowNotify(User user, String time, Proposal proposal) {
		super();
		this.user = user;
		this.time = time;
		this.proposal = proposal;
	}
	public ModelShowNotify() {
		super();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Proposal getProposal() {
		return proposal;
	}
	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	
}
