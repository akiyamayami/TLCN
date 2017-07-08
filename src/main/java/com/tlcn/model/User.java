package com.tlcn.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="user")
public class User {
	@Id
	private String email;
	private String password;
	
	
	private String name;
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleID")
	private Role roleUser;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable
	(
			name = "user_right",
			joinColumns={ @JoinColumn(name="email") },
			inverseJoinColumns={ @JoinColumn(name="rightID") }
	)
	private List<Right> rightUser;
	
	@OneToMany(mappedBy = "user")
	private List<RegisterProposal> listproposalregister;
	
	
	
	public User(String email, String password, Date birthday, String name, String phone, Role roleUser,
			List<Right> rightUser, List<RegisterProposal> listproposalregister) {
		super();
		this.email = email;
		this.password = password;
		this.birthday = birthday;
		this.name = name;
		this.phone = phone;
		this.roleUser = roleUser;
		this.rightUser = rightUser;
		this.listproposalregister = listproposalregister;
	}
	public User() {
		super();
	}
	public User(String email, String password, String name, String phone, Role roleUser) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.roleUser = roleUser;
		this.rightUser = null;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Role getRoleUser() {
		return roleUser;
	}
	public void setRoleUser(Role roleUser) {
		this.roleUser = roleUser;
	}
	public List<Right> getRightUser() {
		return rightUser;
	}
	public void setRightUser(List<Right> rightUser) {
		this.rightUser = rightUser;
	}
	public List<RegisterProposal> getListproposalregister() {
		return listproposalregister;
	}
	public void setListproposalregister(List<RegisterProposal> listproposalregister) {
		this.listproposalregister = listproposalregister;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
