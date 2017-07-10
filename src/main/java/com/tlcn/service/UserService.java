package com.tlcn.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tlcn.dao.RoleRepository;
import com.tlcn.dao.UserRespository;
import com.tlcn.model.Right;
import com.tlcn.model.Role;
import com.tlcn.model.User;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRespository userRespository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		User userInfo = userRespository.findOne(username.toLowerCase());
		System.out.println(username);
        System.out.println("UserInfo= " + userInfo);
        
        if (userInfo == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }    
        List<Role> x = new ArrayList<Role>();
        x.add(userInfo.getRoleUser());
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(userInfo.getEmail(),
                userInfo.getPassword(),getAuthorities(x));
		return userDetails;
	}
	
	private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
	
	private final List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Right> collection = new ArrayList<Right>();
        for (final Role role : roles) {
            collection.addAll(role.getListRight());
        }
        for (final Right item : collection) {
            privileges.add(item.getName());
        }
        
        return privileges;
    }
	
	
	private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
	
	public User findOne(String email){
		return userRespository.findOne(email);
	}
	
	
}
