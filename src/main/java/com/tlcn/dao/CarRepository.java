package com.tlcn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tlcn.model.Car;
import com.tlcn.model.ModelCarReady;


public interface CarRepository extends CrudRepository<Car, Integer>{
	@Query("select c "
			+ "from car c "
			+ "where c not in "
			+ "(select c "
			+ "from car c, com.tlcn.model.Proposal p "
			+ "where p.stt = 1 and p.type != 3 and CURDATE() between p.usefromdate and p.usetodate "
			+ "and p.car = c)")
	public List<Car> getListCarAvaliable();
	
	@Query("select c from car c where c.type = ?1")
	public List<Car> getListFilter_Type(String type);
	
	@Query("select c from car c where c.seats = ?1")
	public List<Car> getListFilter_Seat(int seats);
	
	@Query("select c from car c where c.type = ?1 and c.seats = ?2")
	public List<Car> getListFilter_Type_Seat(String type, int seats);
	
	@Query("select c from car c where c not in (select p.car from com.tlcn.model.Proposal p where p.usefromdate > CURDATE() or CURDATE() between p.usefromdate and p.usetodate)")
	public List<Car> getListCarNotRegistered();
	
	@Query("select p.car from com.tlcn.model.Proposal p where p.stt = 0 and p.usefromdate > CURDATE()")
	public List<Car> getListCarRegistered();
}


