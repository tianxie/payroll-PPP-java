package me.txie.payroll;

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
}
