package com.apap.tutorial5.service;

import java.util.Optional;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.CarDb;

public interface CarService {
	
	Optional<CarModel> getCarDetailById(Long id);
	
	void addCar(CarModel car);
	void deleteCar(CarModel car);
	void updateCar(CarModel car);
	
	CarDb getCarDb();
}