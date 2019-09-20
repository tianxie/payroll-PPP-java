package me.txie.payroll;

import java.time.LocalDate;

public class SalesReceipt {
    private LocalDate date;
    private double saleAmount;

    public SalesReceipt(LocalDate date, double amount) {
        this.date = date;
        this.saleAmount = amount;
    }

    public LocalDate date() {
        return date;
    }

    public double saleAmount() {
        return saleAmount;
    }
}
