package com.tlcn.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class ModelCreateorChangeProposal {
	private int proposalID;
	private String name;
	private String detail;
	private String typedateuse;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date useindate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date usefromdate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date usetodate;
	private MultipartFile file;
	private int carID;
	private boolean isFileExist;
	
	public ModelCreateorChangeProposal() {
		super();
	}
	
	

	public ModelCreateorChangeProposal(int proposalID, String name, String detail) {
		super();
		this.proposalID = proposalID;
		this.name = name;
		this.detail = detail;
	}

	
	//use car many day
	public ModelCreateorChangeProposal(int proposalID, String name, String detail, String typedateuse
			, Date usefromdate, Date usetodate, MultipartFile file, int carID, boolean isFileExitst) {
		super();
		this.proposalID = proposalID;
		this.name = name;
		this.detail = detail;
		this.typedateuse = typedateuse;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
		this.file = file;
		this.carID = carID;
		this.isFileExist = isFileExitst;
	}


	//use car 1 day
	public ModelCreateorChangeProposal(int proposalID, String name, String detail, String typedateuse, Date useindate,
			MultipartFile file, int carID, boolean isFileExitst) {
		super();
		this.proposalID = proposalID;
		this.name = name;
		this.detail = detail;
		this.typedateuse = typedateuse;
		this.useindate = useindate;
		this.file = file;
		this.carID = carID;
		this.isFileExist = isFileExitst;
	}



	public ModelCreateorChangeProposal(int proposalID, String name, String detail, String typedateuse, Date useindate,
			Date usefromdate, Date usetodate, MultipartFile file, int carID) {
		super();
		this.proposalID = proposalID;
		this.name = name;
		this.detail = detail;
		this.typedateuse = typedateuse;
		this.useindate = useindate;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
		this.file = file;
		this.carID = carID;
	}



	public int getProposalID() {
		return proposalID;
	}
	public void setProposalID(int proposalID) {
		this.proposalID = proposalID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public int getCarID() {
		return carID;
	}
	public void setCarID(int carID) {
		this.carID = carID;
	}
	
	public String getTypedateuse() {
		return typedateuse;
	}
	public void setTypedateuse(String typedateuse) {
		this.typedateuse = typedateuse;
	}

	public Date getUseindate() {
		return useindate;
	}

	public void setUseindate(Date useindate) {
		this.useindate = useindate;
	}


	public boolean isFileExist() {
		return isFileExist;
	}


	public void setFileExist(boolean isFileExist) {
		this.isFileExist = isFileExist;
	}
	
}
