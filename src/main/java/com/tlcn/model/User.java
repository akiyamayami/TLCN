package com.tlcn.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="user")
public class User {
	@Id
	private String email;
	private String password;
	private String name;
	private String phone;
	
	
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
	
	
	public User(String email, String password, String name, String phone, Role roleUser, List<Right> rightUser) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.roleUser = roleUser;
		this.rightUser = rightUser;
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
}
