package com.tlcn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlcn.model.Car;
import com.tlcn.model.ModelCarReady;
import com.tlcn.model.ModelFilterCar;
import com.tlcn.service.CarService;

@Controller
@Component
public class CarController {
	@Autowired
	private CarService carService;

	public CarController() {
		super();
	}
	
	
	// page find-car
	@RequestMapping(value="/find-cars", method = RequestMethod.GET)
	public String findCar(Model model){
		model.addAttribute("MODE", "MODE_FIND_CARS");
		model.addAttribute("listcars", carService.findAll());
		model.addAttribute("listtype_seats", carService.findAll());
		model.addAttribute("filter-car", new ModelFilterCar());
		return "Index";
	}
	
	// filter car
	@RequestMapping(value="/find-cars", method = RequestMethod.POST)
	public String filterCar(Model model, @ModelAttribute("filter-car") ModelFilterCar filtercar,
			BindingResult result){
		model.addAttribute("MODE", "MODE_FIND_CARS");
		model.addAttribute("listtype_seats", carService.findAll());
		model.addAttribute("listcars", getListCarFilter(filtercar));
		model.addAttribute("filter-car", filtercar);
		
		return "Index";
	}
	// page check-stt-car
	@RequestMapping(value="/check-stt-cars", method = RequestMethod.GET)
	public String checkSttCar(Model model){
		model.addAttribute("MODE", "MODE_CHECK_STT_CARS");
		model.addAttribute("listCarReady", carService.getListCarReady()); 
		model.addAttribute("listCarRegistered", carService.getListCarRegistered()); 
		model.addAttribute("listCarNotRegistered", carService.getListCarNotRegistered()); 
		return "Index";
	}
	public List<Car> getListCarFilter(ModelFilterCar filter){
		int seats = filter.getSeat();
		String type = filter.getType();
		if(seats == -1 && type.equals("all"))
			return carService.findAll();
		if(seats != -1 && !type.equals("all"))
			return carService.findListFilter_Type_Seat(type, seats);
		if(seats != -1)
			return carService.findListFilter_Seat(seats);
		else
			return carService.findListFilter_Type(type);
	}
}
