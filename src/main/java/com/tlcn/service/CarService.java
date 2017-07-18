package com.tlcn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.CarRepository;
import com.tlcn.dao.ProposalRepository;
import com.tlcn.model.Car;
import com.tlcn.model.ModelCarReady;
import com.tlcn.model.ModelCarRegistered;
import com.tlcn.model.Proposal;

@Service
public class CarService {
	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ProposalRepository proposalRepository;
	
	
	public CarService() {
		super();
	}
	
	public Car findOne(int carID){
		return carRepository.findOne(carID);
	}
	public List<Car> findAll(){
		List<Car> cars = new ArrayList<>();
		for(Car car :carRepository.findAll()){
			cars.add(car);
		}
		return cars;
	}
	public List<Car> findListCarAvailable(){
		List<Car> cars = new ArrayList<>();
		for(Car car : carRepository.getListCarAvaliable()){
			cars.add(car);
		}
		return cars;
	}
	
	public List<Car> findListFilter_Type(String type){
		List<Car> cars = new ArrayList<>();
		for(Car car : carRepository.getListFilter_Type(type)){
			cars.add(car);
		}
		return cars;
	}
	public List<Car> findListFilter_Seat(int seats){
		List<Car> cars = new ArrayList<>();
		for(Car car : carRepository.getListFilter_Seat(seats)){
			cars.add(car);
		}
		return cars;
	}
	public List<Car> findListFilter_Type_Seat(String type, int seats){
		List<Car> cars = new ArrayList<>();
		for(Car car : carRepository.getListFilter_Type_Seat(type, seats)){
			cars.add(car);
		}
		return cars;
	}
	
	public List<ModelCarReady> getListCarReady(){
		 List<Proposal> listProposal = proposalRepository.listProposalReady();
		 if(listProposal.isEmpty())
			 return null;
		 List<ModelCarReady> listCarReady = new ArrayList<>();
		 for(Proposal p : listProposal){
			 System.out.println(p.getCar().getLicenseplate());
			 listCarReady.add(new ModelCarReady(p.getCar().getLicenseplate(),p.getName(),p.getUsefromdate(),p.getUsetodate()));
		 }
		 return listCarReady;
	}
	
	public List<ModelCarRegistered> getListCarRegistered(){
		// get time now
		Calendar x = Calendar.getInstance();
		List<Car> listCar = carRepository.getListCarRegistered();
		if(listCar.isEmpty())
			 return null;
		List<ModelCarRegistered> listCarRegistered = new ArrayList<>();
		for(Car c : listCar){
			listCarRegistered.add(new ModelCarRegistered(c.getLicenseplate(),c.getListproposal()
					.parallelStream()
					.filter(p -> p.getUsefromdate().getTime() >= x.getTime().getTime())
					.collect(Collectors.toList()))); 
		}
		return listCarRegistered;
	}
	
	public List<Car> getListCarNotRegistered(){
		return carRepository.getListCarNotRegistered();
	}
}
