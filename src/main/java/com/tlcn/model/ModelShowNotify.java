package com.tlcn.model;

public class ModelShowNotify {
	
	private User user;
	private String time;
	private TypeProposal type;
	private Proposal proposal;
	private int stt;
	public ModelShowNotify(User user, String time, TypeProposal type, Proposal proposal, int stt) {
		super();
		this.user = user;
		this.time = time;
		this.type = type;
		this.proposal = proposal;
		this.stt = stt;
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
	public TypeProposal getType() {
		return type;
	}
	public void setType(TypeProposal type) {
		this.type = type;
	}
	public Proposal getProposal() {
		return proposal;
	}
	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
	}
	
	
}
