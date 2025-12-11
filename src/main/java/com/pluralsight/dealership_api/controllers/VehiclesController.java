package com.pluralsight.dealership_api.controllers;

import com.pluralsight.dealership_api.dao.VehiclesDao;
import com.pluralsight.dealership_api.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class VehiclesController {
    private VehiclesDao vehiclesDao;

    @Autowired
    public VehiclesController(VehiclesDao vehiclesDao) {
        this.vehiclesDao = vehiclesDao;
    }

    @RequestMapping(path="/vehicles", method=RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.CREATED)
    public Vehicle addVehicle(@RequestBody Vehicle vehicle){
        return vehiclesDao.insertVehicle(vehicle);
    }

    @RequestMapping(path="/vehicles/{vin}", method=RequestMethod.PUT)
    public void updateVehicle(
            @PathVariable String vin,
            @RequestBody Vehicle vehicle){
        vehiclesDao.updateVehicle(vin, vehicle);
    }

    @RequestMapping(path="/vehicles/{vin}", method=RequestMethod.DELETE)
    public void deleteVehicle(@PathVariable String vin){
        vehiclesDao.deleteVehicle(vin);
    }

    @RequestMapping(path="/vehicles", method=RequestMethod.GET)
    public List<Vehicle> getVehicles(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minMiles,
            @RequestParam(required = false) Integer maxMiles,
            @RequestParam(required = false) String type
    ){
        return vehiclesDao.filterVehicle(minPrice,maxPrice,make,model,minYear, maxYear,color, minMiles, maxMiles, type);
    }




}
