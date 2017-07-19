package com.tlcn.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date usefromdate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date usetodate;
	private String file;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sttproposalID")
	private SttProposal stt;
	
	private boolean expired = false;
	
	@OneToMany(mappedBy = "notifyOfProposal")
	private List<NotifyEvent> listnotifyofproposal;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinTable
	(
			name = "registercar",
			joinColumns={ @JoinColumn(name="proposalID", unique=true) },
			inverseJoinColumns={ @JoinColumn(name="carID")}
	)
	private Car car;
	
	@OneToOne(mappedBy = "proposal")
	private RegisterProposal userregister;

	@OneToOne(mappedBy = "proposalapproved")
	private ConfirmProposal infoconfirm;
	
	
	public Proposal() {
		super();
	}
	
	
	public Proposal(TypeProposal type, String name, String detail, Date usefromdate, Date usetodate, String file,
			SttProposal stt, List<NotifyEvent> listnotifyofproposal, Car car, RegisterProposal userregister,
			ConfirmProposal infoconfirm) {
		super();
		this.type = type;
		this.name = name;
		this.detail = detail;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
		this.file = file;
		this.stt = stt;
		this.listnotifyofproposal = listnotifyofproposal;
		this.car = car;
		this.userregister = userregister;
		this.infoconfirm = infoconfirm;
	}


	public Proposal(TypeProposal type, String name, String detail, Date usefromdate, Date usetodate, SttProposal stt, Car car) {
		super();
		this.type = type;
		this.name = name;
		this.detail = detail;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
		this.stt = stt;
		this.car = car;
	}

	public Proposal(TypeProposal type, String name, String detail, Date usefromdate, Date usetodate, String file,
			SttProposal stt, Car car, RegisterProposal userregister) {
		super();
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

	public Proposal(int proposalID, TypeProposal type, String name, String detail, Date usefromdate, Date usetodate,
			String file, SttProposal stt, Car car, RegisterProposal userregister) {
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
			SttProposal stt, Car car, RegisterProposal userregister) {
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

	public SttProposal getStt() {
		return stt;
	}

	public void setStt(SttProposal stt) {
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


	public List<NotifyEvent> getListnotifyofproposal() {
		return listnotifyofproposal;
	}


	public void setListnotifyofproposal(List<NotifyEvent> listnotifyofproposal) {
		this.listnotifyofproposal = listnotifyofproposal;
	}


	public ConfirmProposal getInfoconfirm() {
		return infoconfirm;
	}


	public void setInfoconfirm(ConfirmProposal infoconfirm) {
		this.infoconfirm = infoconfirm;
	}


	public boolean isExpired() {
		return expired;
	}


	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	
	
}
