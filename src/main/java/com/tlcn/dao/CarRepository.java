package com.tlcn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tlcn.model.Car;


public interface CarRepository extends CrudRepository<Car, Integer>{
	@Query("select c "
			+ "from car c "
			+ "where c not in "
			+ "(select c "
			+ "from car c, com.tlcn.model.Proposal p "
			+ "where p.stt = 1 and p.type != 3 and CURDATE() between p.usefromdate and p.usetodate "
			+ "and p.car = c)")
	public List<Car> getListCarAvaliable();
}


