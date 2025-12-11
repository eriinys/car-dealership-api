package com.pluralsight.dealership_api.model;

import java.sql.Date;
import java.time.LocalDate;

public class LeaseContract extends Contract {
    private Vehicle vehicle;
    private int leaseId;
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(LocalDate date, String customerName, String email, String vin, int leaseId) {
        super(date, customerName, email, vin);
        this.leaseId = leaseId;
        this.expectedEndingValue = vehicle.getPrice() * 0.5;
        this.leaseFee = vehicle.getPrice() * 0.07;
    }

    public int getLeaseId() {
        return leaseId;
    }
    public void setLeaseId(int leaseId) {
        this.leaseId = leaseId;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }
    public double getLeaseFee() {
        return leaseFee;
    }

    @Override
    public double getMonthlyPay() {
        double loanAnnual = 0.04;
        double monthlyRate = loanAnnual / 12;
        monthlyPay = ((vehicle.getPrice() - expectedEndingValue ) + leaseFee) / 36;
        return monthlyPay;
    }

    @Override
    public double getTotalPrice() {
        totalPrice = getMonthlyPay() * 36;

        return totalPrice;
    }

    @Override
    public String toFileString() {
        return String.format("%.2f|%.2f|%.2f|%.2f", getExpectedEndingValue(), getLeaseFee(), getTotalPrice(), getMonthlyPay());
    }
}
