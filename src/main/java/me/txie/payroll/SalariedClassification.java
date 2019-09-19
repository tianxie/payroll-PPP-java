package me.txie.payroll;

public class SalariedClassification implements PaymentClassification {

    public double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }
}
