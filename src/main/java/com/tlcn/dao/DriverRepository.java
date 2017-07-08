package com.tlcn.dao;

import org.springframework.data.repository.CrudRepository;

import com.tlcn.model.Driver;
import com.tlcn.model.Right;

public interface DriverRepository extends CrudRepository<Driver, String>{

}
