package me.txie.payroll;

import java.time.LocalDate;

public class Employee {

    final int empId;
    String name;
    String address;
    PaymentClassification classification;
    PaymentSchedule schedule;
    PaymentMethod method;
    Affiliation affiliation = new NoAffiliation();

    public Employee(int empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    public boolean isPayDate(LocalDate payDate) {
        return schedule.isPayDate(payDate);
    }

    public void payday(Paycheck pc) {
        double grossPay = classification.calculatePay(pc);
        double deductions = affiliation.calculateDeductions(pc);
        double netPay = grossPay - deductions;
        pc.setGrossPay(grossPay);
        pc.setDeductions(deductions);
        pc.setNetPay(netPay);
        method.pay(pc);
    }
}
