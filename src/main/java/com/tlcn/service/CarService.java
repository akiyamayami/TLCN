package com.tlcn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.CarRepository;
import com.tlcn.dao.ProposalRepository;
import com.tlcn.dto.ModelCarReady;
import com.tlcn.dto.ModelCarRegistered;
import com.tlcn.dto.ModelCreateorChangeCar;
import com.tlcn.error.CarNotFoundException;
import com.tlcn.error.HaveProposalInTimeUseException;
import com.tlcn.model.Car;
import com.tlcn.model.Proposal;

@Service
public class CarService {
	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ProposalService proposalService;
	@Autowired
	private NotifyEventService notifyEventService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private TypeProposalService typeProposalService;
	@Autowired 
	private SttCarService sttCarService;
	
	
	public CarService() {
		super();
	}
	
	public void save(Car car){
		carRepository.save(car);
	}
	public List<Car> getListCarFree(){
		return carRepository.getListCarFree();
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
	public List<Car> getListCarAvailable(){
		return carRepository.getListCarAvaliable();
	}
	public List<Car> findListCarAvailableInTime(long TimeFrom, long timeTo){
		List<Proposal> listProposal =  proposalService.findAll();
		System.out.println("start find car availble in time");
		
		Set<Car> x = new HashSet<>(listProposal.parallelStream()
				.filter(p -> p.getStt().getSttproposalID() == 1 && p.getType().getTypeID() != 3 && !isBetween(TimeFrom,timeTo,getDate(p.getUsefromdate(), p.getUsefromtime()),getDate(p.getUsetodate(), p.getUsetotime())))
				.map(Proposal::getCar)
				.collect(Collectors.toList()));
		List<Car> cars = new ArrayList<>(x);
		findAll().parallelStream().filter(c -> c.getListproposal() ==  null 
				|| !c.getListproposal().parallelStream().filter(p -> p.getType().getTypeID() != 3 && p.getStt().getSttproposalID() == 1).findFirst().isPresent())
				.forEach(c -> cars.add(c));
		for(Car c : findAll()){
			if(c.getListproposal() ==  null || !c.getListproposal().parallelStream().filter(p -> p.getType().getTypeID() != 3 && p.getStt().getSttproposalID() == 1).findFirst().isPresent()){
				cars.add(c);
			}
		}

		return cars;
 	}
	
	public List<Car> findListFilter_Type(String type){
		return carRepository.getListFilter_Type(type);
	}
	public List<Car> findListFilter_Seat(int seats){
		return carRepository.getListFilter_Seat(seats);
	}
	public List<Car> findListFilter_Type_Seat(String type, int seats){
		return carRepository.getListFilter_Type_Seat(type, seats);
	}
	
	public void remove(Car car){
		carRepository.delete(car);
	}
	public List<ModelCarReady> getListCarReady(){
		 List<Proposal> listProposal = proposalService.getListProposalReady();
		 if(listProposal.isEmpty())
			 return null;
		 List<ModelCarReady> listCarReady = new ArrayList<>();
		 for(Proposal p : listProposal){
			 System.out.println(p.getCar().getLicenseplate());
			 listCarReady.add(new ModelCarReady(p.getCar().getLicenseplate(),p.getName(),p.getUsefromdate(),p.getUsetodate()));
		 }
		 return listCarReady;
	}
	public void converAndSave(ModelCreateorChangeCar car){
		Car x = new Car(car.getLicenseplate(),car.getType(),car.getSeats(),
				sttCarService.findOne(car.getSttcarID()),driverService.findOne(car.getEmailDriver()));
		carRepository.save(x);
	}
	public ModelCreateorChangeCar convertCarToShow(Car car){
		return new ModelCreateorChangeCar(car.getLicenseplate(),car.getType(),car.getSeats(),car.getSttcar().getSttcarID(),car.getDriver().getEmail());
	}
	public void converAndChange(ModelCreateorChangeCar car, Car caradd){
		long timeNow = Calendar.getInstance().getTime().getTime();
		if(car.getSttcarID() != 1){
			boolean isCarinTimeUse = caradd.getListproposal().parallelStream()
					.filter(p -> p.getStt().getSttproposalID() == 1 && proposalService.isInTimeUse(p))
					.findFirst().isPresent();
			if(isCarinTimeUse){
				throw new HaveProposalInTimeUseException();
			}
		}
		caradd.setDriver(driverService.findOne(car.getEmailDriver()));
		caradd.setLicenseplate(car.getLicenseplate());
		caradd.setSeats(car.getSeats());
		caradd.setSttcar(sttCarService.findOne(car.getSttcarID()));
		caradd.setType(car.getType());
		if(caradd.getSttcar().getSttcarID() == 2){
			caradd.getListproposal().parallelStream()
				.filter(p -> p.getType().getTypeID() != 3 && proposalService.getDate(p.getUsefromdate(),p.getUsefromtime()) > timeNow)
				.forEach(p -> notifyEventService.addNotifyforUser(p,p.getUserregister().getUser(),"CarIsRepair"));
		}
		carRepository.save(caradd);
	}
	
	public List<ModelCarRegistered> getListCarRegistered(){
		long timeNow = Calendar.getInstance().getTime().getTime();
		List<Car> allCar = findAll();
		List<ModelCarRegistered> listcarRegister = new ArrayList<>();
		allCar.parallelStream()
			  .filter(c -> c.getListproposal().parallelStream()
												.filter(p -> p.getStt().getSttproposalID() == 0 && getDate(p.getUsefromdate(),p.getUsefromtime()) > timeNow)
												.findFirst().isPresent())
			  .forEach(c -> listcarRegister.add(new ModelCarRegistered(c.getLicenseplate(), c.getListproposal()
					  .parallelStream().filter(p -> getDate(p.getUsefromdate(), p.getUsefromtime()) >= timeNow)
					  .collect(Collectors.toList()))));
		return listcarRegister;
	}

	public List<Car> getListCarNotRegistered(){
		return carRepository.getListCarNotRegistered();
	}
	
	public Long getDate(Date date, Date time){
		Calendar Cdate = Calendar.getInstance(),Ctime = Calendar.getInstance(),dateTime = Calendar.getInstance();
		Cdate.setTime(date);
		Ctime.setTime(time);
		dateTime.set(Cdate.get(Calendar.YEAR), Cdate.get(Calendar.MONTH), Cdate.get(Calendar.DATE), 
				Ctime.get(Calendar.HOUR_OF_DAY), Ctime.get(Calendar.MINUTE));
		return dateTime.getTime().getTime();
		
	}
	
	public boolean isBetween(long timeCheckFrom,long timeCheckTo, long timeFrom, long timeTo){
		System.out.println("time check from" + timeCheckFrom);
		System.out.println("time check to " + timeCheckTo);
		System.out.println("time timeFrom" + timeFrom);
		System.out.println("time timeTo" + timeTo);
		System.out.println(timeCheckFrom >= timeFrom && timeCheckFrom <= timeTo);
		System.out.println(timeCheckTo >= timeFrom && timeCheckTo <= timeTo);
		if((timeCheckFrom >= timeFrom && timeCheckFrom <= timeTo) || (timeCheckTo >= timeFrom && timeCheckTo <= timeTo))
			return true;
		return false;
	}
}
