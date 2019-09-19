package me.txie.payroll;

public class Employee {

    final int empId;
    final String name;
    final String address;
    PaymentClassification classification;
    PaymentSchedule schedule;
    PaymentMethod method;

    public Employee(int empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }
}
