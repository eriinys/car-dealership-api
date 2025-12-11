package com.pluralsight.dealership_api.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Contract {
    protected LocalDate date;
    protected String customerName;
    protected String email;
    protected String vin;
    protected double totalPrice;
    protected double monthlyPay;;

    LocalDate today = LocalDate.now();

    public Contract(LocalDate date, String customerName, String email, String vin) {
        this.date = today;
        this.customerName = customerName;
        this.email = email;
        this.vin = vin;
    }

    //region getters/setters
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = today;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    //endregion

    //abstract methods
    public abstract double getTotalPrice();
    public abstract double getMonthlyPay();

    public abstract String toFileString();

}

