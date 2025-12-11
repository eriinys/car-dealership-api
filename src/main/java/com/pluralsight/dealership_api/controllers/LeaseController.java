package com.pluralsight.dealership_api.controllers;

import com.pluralsight.dealership_api.dao.LeaseContractsDao;
import com.pluralsight.dealership_api.model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeaseController {
    private LeaseContractsDao leaseContractsDao;

    @Autowired
    public LeaseController(LeaseContractsDao leaseContractsDao) {
        this.leaseContractsDao = leaseContractsDao;
    }

    @RequestMapping(path="/lease_contract", method= RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.CREATED)
    public LeaseContract addLeaseContract(@RequestBody LeaseContract leaseContract){
        return leaseContractsDao.addLeaseContract(leaseContract);
    }

    @RequestMapping(path="/lease_contract/{id}", method=RequestMethod.GET)
    public LeaseContract getLeaseContractById(@PathVariable int id){
        return leaseContractsDao.getLeaseContract(id);
    }
}
