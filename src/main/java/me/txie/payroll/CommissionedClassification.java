package me.txie.payroll;

public class CommissionedClassification implements PaymentClassification {
    double baseRate;
    double commissionRate;

    public CommissionedClassification(double baseRate, double commissionRate) {
        this.baseRate = baseRate;
        this.commissionRate = commissionRate;
    }
}
