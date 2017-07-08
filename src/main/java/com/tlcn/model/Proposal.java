package com.tlcn.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="proposal")
public class Proposal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int proposalID;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeID")
	private TypeProposal type;
	private String name;
	private String detail;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date usefromdate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date usetodate;
	private String file;
	private int stt;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinTable
	(
			name = "registercar",
			joinColumns={ @JoinColumn(name="proposalID", unique=true) },
			inverseJoinColumns={ @JoinColumn(name="carID", unique=true)}
	)
	private Car car;
	
	@OneToOne(mappedBy = "proposal")
	private RegisterProposal userregister;

	public Proposal() {
		super();
	}

	public Proposal(int proposalID, TypeProposal type, String name, String detail, Date usefromdate, Date usetodate,
			String file, int stt, Car car, RegisterProposal userregister) {
		super();
		this.proposalID = proposalID;
		this.type = type;
		this.name = name;
		this.detail = detail;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
		this.file = file;
		this.stt = stt;
		this.car = car;
		this.userregister = userregister;
	}

	public Proposal(int proposalID, TypeProposal type, String name, Date usefromdate, Date usetodate, String file,
			int stt, Car car, RegisterProposal userregister) {
		super();
		this.proposalID = proposalID;
		this.type = type;
		this.name = name;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
		this.file = file;
		this.stt = stt;
		this.car = car;
		this.userregister = userregister;
	}

	public int getProposalID() {
		return proposalID;
	}

	public void setProposalID(int proposalID) {
		this.proposalID = proposalID;
	}

	public TypeProposal getType() {
		return type;
	}

	public void setType(TypeProposal type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUsefromdate() {
		return usefromdate;
	}

	public void setUsefromdate(Date usefromdate) {
		this.usefromdate = usefromdate;
	}

	public Date getUsetodate() {
		return usetodate;
	}

	public void setUsetodate(Date usetodate) {
		this.usetodate = usetodate;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public RegisterProposal getUserregister() {
		return userregister;
	}

	public void setUserregister(RegisterProposal userregister) {
		this.userregister = userregister;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
	
	
}
