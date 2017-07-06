package com.tlcn.dao;

import org.springframework.data.repository.CrudRepository;

import com.tlcn.model.User;

public interface UserRespository extends CrudRepository<User, String> {
	
}
