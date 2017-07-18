package com.tlcn.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelCarReady {
	
	private String licenseplate;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date usefromdate;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date usetodate;
	
	
	public ModelCarReady(String licenseplate, String name, Date usefromdate, Date usetodate) {
		super();
		this.licenseplate = licenseplate;
		this.name = name;
		this.usefromdate = usefromdate;
		this.usetodate = usetodate;
	}

	public ModelCarReady() {
		super();
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
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
	

	
	
}
