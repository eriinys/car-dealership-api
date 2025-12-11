package com.pluralsight.dealership_api.controllers;

import com.pluralsight.dealership_api.dao.SalesContractsDao;
import com.pluralsight.dealership_api.model.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalesController {
    private SalesContractsDao salesContractsDao;

    @Autowired
    public SalesController(SalesContractsDao salesContractsDao) {
        this.salesContractsDao = salesContractsDao;
    }

    @RequestMapping(path="/sales_contract", method=RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.CREATED)
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract){
        return salesContractsDao.addSalesContract(salesContract);
    }

    @RequestMapping(path="/sales_contract/{id}", method=RequestMethod.GET)
    public SalesContract getSalesContractById(@PathVariable int id){
        return salesContractsDao.getSalesContract(id);
    }
}
