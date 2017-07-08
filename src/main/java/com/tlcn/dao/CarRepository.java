package com.tlcn.dao;

import org.springframework.data.repository.CrudRepository;

import com.tlcn.model.Car;

public interface CarRepository extends CrudRepository<Car, Integer>{

}
