package com.pluralsight.dealership_api.model;

public class SalesContract extends Contract {
    private final double salesTaxAmt;
    private final double recordingFee;
    private double processingFee;
    private boolean financed;

    public SalesContract(String date, String customerName, String email, Vehicle vehicle, boolean financed) {
        super(date, customerName, email, vehicle);
        this.salesTaxAmt = 0.05;
        this.recordingFee = 100;
        this.financed = financed;
    }

    public double getSalesTaxAmt() {
        return salesTaxAmt;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    public double getProcessingFee(){
        double vehiclePrice = vehicle.getPrice();
        if (vehiclePrice < 10000) {
            processingFee = 295;
        } else {
            processingFee = 495;
        }
        return  processingFee;
    }

    @Override
    public double getTotalPrice() {
        double vehiclePrice = vehicle.getPrice();
        totalPrice = (vehiclePrice * salesTaxAmt) + vehiclePrice + recordingFee + processingFee;
        return totalPrice;
    }

    @Override
    public double getMonthlyPay() {
        double loanAnnual;
        double monthlyRate;
        double vehiclePrice = vehicle.getPrice();
        double p = vehiclePrice + processingFee + recordingFee + (vehiclePrice * salesTaxAmt);
        if (financed) {
            if (vehiclePrice >= 10000) {
                loanAnnual = 0.0425;
                monthlyRate = loanAnnual / 12;
                monthlyPay = (monthlyRate * p) / (1 - Math.pow(1 + monthlyRate, -48));
                //monthly Payment = (r × P) ÷ (1 − (1 + r)^−n)
            } else {
                loanAnnual = 0.0525;
                monthlyRate = loanAnnual / 12;
                monthlyPay = (monthlyRate * p) / (1 - Math.pow(1 + monthlyRate, -12));
            }
        } else {
            monthlyPay = 0;
        }
        return monthlyPay;
    }

    @Override
    public String toFileString() {
        String yesNo;
        if (isFinanced() == true){
            yesNo = "YES";
        } else {
            yesNo = "NO";
        }
        return String.format("%.2f|%.2f|%.2f|%s|%.2f", getRecordingFee(), getProcessingFee(), getTotalPrice(), yesNo, getMonthlyPay());
    }
}