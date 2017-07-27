package com.tlcn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.DriverRepository;
import com.tlcn.dto.ModelCreateorChangeDriver;
import com.tlcn.dto.ModelCreateorChangeProposal;
import com.tlcn.model.Car;
import com.tlcn.model.Driver;
import com.tlcn.model.Proposal;

@Service
public class DriverService {
	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private SttDriverService sttDriverService;

	@Autowired
	private ProposalService proposalService;

	@Autowired
	private NotifyEventService notifyEventService;

	@Autowired
	private CarService carService;

	public DriverService() {
		super();
	}

	public void save(Driver driver) {
		driverRepository.save(driver);
	}

	public List<Driver> findAll() {
		List<Driver> lists = new ArrayList<>();
		for (Driver d : driverRepository.findAll()) {
			lists.add(d);
		}
		return lists;
	}

	public Driver findOne(String email) {
		return driverRepository.findOne(email);
	}

	public void convertAndSave(ModelCreateorChangeDriver d) {
		List<Car> cars = d.getListcar();
		List<Car> listcaradd = new ArrayList<>();

		Driver driverNew = null;
		if (cars != null) {
			for (Car c : cars) {
				Car newcar = carService.findOne(c.getCarID());
				System.out.println(newcar.getCarID());
				listcaradd.add(newcar);
			}
			System.out.println("list car notempty");
			List<Car> listcarAdd = getListCarToAdd(cars);
			driverNew = new Driver(d.getEmail(), d.getName(), d.getBirthday(), d.getPhone(), d.getExperience(),
					d.getLicense(), d.getAddress(), sttDriverService.findOne(1), listcarAdd);
			save(driverNew);
			for (Car c : listcarAdd) {
				c.setDriver(driverNew);
				carService.save(c);
			}
		} else {
			driverNew = new Driver(d.getEmail(), d.getName(), d.getBirthday(), d.getPhone(), d.getExperience(),
					d.getLicense(), d.getAddress(), sttDriverService.findOne(1), null);
			save(driverNew);
		}

	}

	public void convertAndChange(ModelCreateorChangeDriver d, Driver driver){
		long timeNow = Calendar.getInstance().getTime().getTime();
		List<Car> cars = d.getListcar();
		driver.setName(d.getName());
		driver.setBirthday(d.getBirthday());
		driver.setExperience(d.getExperience());
		driver.setLicense(d.getLicense());
		driver.setAddress(d.getAddress());
		driver.setSttdriver(sttDriverService.findOne(d.getSttdriverID()));
		List<Proposal> listProposalChange = new ArrayList<>();
		if (cars != null) {
			System.out.println("cars not null");
			// khi tài xế bị bệnh hoặc nghỉ hưu sẻ hủy các xe được lái bởi tài xế và thông báo cho cho các đề nghị đã đăng ký xe này
			if (d.getSttdriverID() == 3) {
				cars = cars.parallelStream().filter(c -> c.getDriver() != null).collect(Collectors.toList());
				for(Car c : cars){
					c.getListproposal().parallelStream()
						.filter(p -> p.getType().getTypeID() != 3 
						&& proposalService.getDate(p.getUsefromdate(), p.getUsefromtime()) > timeNow)
						.forEach(p -> listProposalChange.add(p));
					c.setDriver(null);
					carService.save(c);
				}
				listProposalChange.forEach(p -> notifyEventService.addNotifyforUser(p, p.getUserregister().getUser(),
										"DriverQuitJob"));
				driver.setListcar(null);
			} else {
				// khi có thêm xe mới/xóa xe cũ ...vv..v.
				
				
				List<Car> listCarRemove = new ArrayList<>();
				for(Car c : d.getListcar()){
					System.out.println(c.getCarID());
				}
				System.out.println("size :" + driver.getListcar().size());
				for(Car c : driver.getListcar()){
					System.out.println(c.getCarID());
					System.out.println(isCarInList(cars,c));
					if(!isCarInList(cars,c)){
						System.out.println("add car " + c.getCarID());
						listCarRemove.add(c);
					}
				}
				System.out.println("list car remove " + listCarRemove.size());
				//set driver = null cho các xe bị xóa không có tài xế
				for(Car c : listCarRemove){
					if(c.getCarID() == 0)
						continue;
					System.out.println(c.getCarID());
					c.setDriver(null);
					carService.save(c);
					if(c.getListproposal() != null){
						c.getListproposal().parallelStream()
				   		  .filter(p -> p.getType().getTypeID() != 3 
									&& proposalService.getDate(p.getUsefromdate(), p.getUsefromtime()) > timeNow)
				   		  .forEach(p -> notifyEventService.addNotifyforUser(p, p.getUserregister().getUser(),
						"NoDriver"));
					}
				}
				// kiểm tra đề nghị của các xe không có tài xế, đề nghị nào đang trong thời gian đăng ký hoặc sử dụng thì thông báo đã có tài xế trở lại
				List<Car> listcar = d.getListcar();
				List<Car> listCarAdd = getListCarToAdd(cars);
				
				System.out.println(listCarAdd.size());
				if(listCarAdd != null){
					listCarAdd.parallelStream()
						.filter(c -> c.getListproposal() != null )
						.forEach(c -> c.getListproposal().parallelStream()
										.filter(p -> p.getType().getTypeID() != 3 
									&& proposalService.getDate(p.getUsefromdate(), p.getUsefromtime()) > timeNow)
										.forEach(p -> notifyEventService.addNotifyforUser(p, p.getUserregister().getUser(),
												"DriverBack")));
				}
				driver.setListcar(listcar);
				for(Car c : listcar){
					if(c.getCarID() == 0)
						continue;
					c.setDriver(driver);
					carService.save(c);
				}
			}
		}
		else{
			System.out.println("cars null");
			// nếu xóa toàn bộ xe kiểm tra xe nào có đề nghị đang sử dụng thì thông báo
			driver.getListcar().parallelStream()
							   .filter(c -> c.getDriver() != null)
							   .forEach(c -> c.getListproposal().parallelStream()
									   		  .filter(p -> p.getType().getTypeID() != 3 
														&& proposalService.getDate(p.getUsefromdate(), p.getUsefromtime()) > timeNow)
									   		  .forEach(p -> listProposalChange.add(p)));
			listProposalChange.forEach(p -> notifyEventService.addNotifyforUser(p, p.getUserregister().getUser(),
					"NoDriver"));
			for(Car c : driver.getListcar()){
				c.setDriver(null);
				carService.save(c);
			}
			driver.setListcar(null);
		}
		
		save(driver);
	}

	public void remove(Driver driver) {
		driverRepository.delete(driver);
	}
	// lấy danh sách xe không có tài xế
	public List<Car> getListCarToAdd(List<Car> listCar) {
		List<Car> cars = new ArrayList<>();
		listCar.parallelStream().filter(c -> c.getCarID() != 0 && c.getDriver() == null).forEach(c -> cars.add(carService.findOne(c.getCarID())));
		System.out.println("size car after filter :" + cars.size());
		return cars;
	}
	public boolean isCarInList(List<Car> list, Car car){
		for(Car c : list){
			if(c.getCarID() == car.getCarID())
				return true;
		}
		return false;
	}
	public ModelCreateorChangeDriver converDriverToDisplay(Driver d) {
		ModelCreateorChangeDriver driverShow = new ModelCreateorChangeDriver(d.getEmail(), d.getName(), d.getBirthday(),
				d.getPhone(), d.getExperience(), d.getLicense(), d.getAddress(), d.getSttdriver().getSttdriverID(),
				d.getListcar());
		return driverShow;
	}
}
