package me.txie.payroll;

public class HourlyClassification implements PaymentClassification {
    double hourlyRate;

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
