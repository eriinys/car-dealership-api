package com.pluralsight.dealership_api.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Contract {
    protected String date;
    protected String customerName;
    protected String email;
    protected Vehicle vehicle;
    protected double totalPrice;
    protected double monthlyPay;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    LocalDate today = LocalDate.now();

    public Contract(String date, String customerName, String email, Vehicle vehicle) {
        this.date = today.format(formatter);
        this.customerName = customerName;
        this.email = email;
        this.vehicle = vehicle;
    }

    //region getters/setters
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    //endregion

    //abstract methods
    public abstract double getTotalPrice();
    public abstract double getMonthlyPay();

    public abstract String toFileString();

    public String toString(){
        return String.format("%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|", date, customerName, email, vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());

        //DATE|CUSTOMER_NAME|CUSTOMER_EMAIL|VIN|Y
        //EAR|MAKE|MODEL|VEHICLE_TYPE|COLOR|ODOMETER|VEHICLE_PRI
        //CE|[contract-specific-fields]|TOTAL_PRICE|MONTHLY_PAYMENT
    }
}

