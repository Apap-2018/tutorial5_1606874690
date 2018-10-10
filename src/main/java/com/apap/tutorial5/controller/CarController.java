package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

@Controller
public class CarController {
	private long idSementara;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value= "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealerArchive = dealerService.getDealerDetailById(dealerId).get();
		
		
		ArrayList<CarModel> listCar = new ArrayList<CarModel>();
		//car.setDealer(dealerArchive);
		listCar.add(car);
		dealerArchive.setListCar(listCar);
		
		model.addAttribute("dealer", dealerArchive);
		model.addAttribute("title", "Add Car");
		//model.addAttribute("car", car);
		return "addCar";
	}
	
	@RequestMapping(value= "/car/add/{dealerId}", method = RequestMethod.POST, params= {"simpan"})
	private String addCarSubmit(@ModelAttribute DealerModel dealer, Model model) {
		
		//Long dealerId = dealer.getId();
		DealerModel dealerArchive = dealerService.getDealerDetailById(dealer.getId()).get();
		
		for (int i = 0; i < dealer.getListCar().size(); i++) {
			dealer.getListCar().get(i).setDealer(dealerArchive);
			carService.addCar(dealer.getListCar().get(i));
		}
		
		model.addAttribute("title", "Add");
		return "add";
	}
	
	

//	@RequestMapping(value= "/car/delete/{carId}", method = RequestMethod.GET)
//	private String delete(@PathVariable(value = "carId") Long carId, Model model) {
//		
//		CarModel archive = carService.getCarDetailById(carId).get();
//		carService.deleteCar(archive);
//		
//		return "delete-dealer";			
//	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer,Model model) {
		
		for(CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
		}
		
		model.addAttribute("title", "Delete Car");
		return "delete-dealer";
	}
	
	@RequestMapping(value= "/car/update/{carId}", method = RequestMethod.GET)
	private String updateCar(@PathVariable(value = "carId") String carId, Model model) {

		CarModel archive = carService.getCarDetailById(Long.parseLong(carId)).get();
		this.idSementara = Long.parseLong(carId);
		model.addAttribute("car", archive);
		
		model.addAttribute("title", "Update Car");
		return "updateCar";			
	}
	
	@RequestMapping(value= "/car/update/", method = RequestMethod.POST)
	private String updateCarSubmit(@ModelAttribute CarModel car, Model model) {

		car.setId(idSementara);
		carService.updateCar(car);
		
		model.addAttribute("title", "Update");
		return "update";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"tambahbaris"})
	public String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
		
		if (dealer.getListCar() == null) {
            dealer.setListCar(new ArrayList<CarModel>());
        }
		
		CarModel car = new CarModel();
		//car.setDealer(dealer);
		dealer.getListCar().add(car);
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"hapusbaris"})
	public String removeRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	  
		final Integer rowId = Integer.valueOf(req.getParameter("hapusbaris"));
	    dealer.getListCar().remove(rowId.intValue());
	    
	    model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		
	    return "addCar";
	}
	
}
