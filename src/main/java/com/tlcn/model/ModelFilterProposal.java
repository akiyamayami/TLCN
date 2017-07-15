package com.tlcn.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelFilterProposal {
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date datecreate;
	private String type;
	private int stt;
	public ModelFilterProposal(Date datecreate, String type, int stt) {
		super();
		this.datecreate = datecreate;
		this.type = type;
		this.stt = stt;
	}
	public ModelFilterProposal() {
		super();
	}
	public Date getDatecreate() {
		return datecreate;
	}
	public void setDatecreate(Date datecreate) {
		this.datecreate = datecreate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
	}
	
	
	
}
