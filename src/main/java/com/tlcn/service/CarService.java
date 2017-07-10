package com.tlcn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.CarRepository;
import com.tlcn.model.Car;

@Service
public class CarService {
	@Autowired
	private CarRepository carRepository;

	public CarService() {
		super();
	}
	
	public Car findOne(int carID){
		return carRepository.findOne(carID);
	}
	
	public List<Car> findListCarAvailable(){
		List<Car> cars = new ArrayList<>();
		for(Car car : carRepository.getListCarAvaliable()){
			cars.add(car);
		}
		return cars;
	}
}
