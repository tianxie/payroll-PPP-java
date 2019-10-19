package me.txie.payroll;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CommissionedClassification implements PaymentClassification {
    private final double baseRate;
    private final double commissionRate;
    private Map<LocalDate, SalesReceipt> salesReceipts = new HashMap<>();

    public CommissionedClassification(double baseRate, double commissionRate) {
        this.baseRate = baseRate;
        this.commissionRate = commissionRate;
    }

    public SalesReceipt getSalesReceipt(LocalDate date) {
        return salesReceipts.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceipts.put(salesReceipt.date(), salesReceipt);
    }

    public double baseRate() {
        return baseRate;
    }

    public double commissionRate() {
        return commissionRate;
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double salesTotal = 0;
        for (SalesReceipt receipt : salesReceipts.values()) {
            if (DateUtil.isInPayPeriod(receipt.date(),
                    pc.payPeriodStartDate(), pc.payPeriodEndDate())) {
                salesTotal += receipt.saleAmount();
            }
        }
        return baseRate + (salesTotal * commissionRate * 0.01);
    }

    @Override
    public String toString() {
        return String.format("%.2f + %.2f%% sales commission", baseRate, commissionRate);
    }
}
