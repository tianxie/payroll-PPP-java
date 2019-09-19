package me.txie.payroll;

import java.time.LocalDate;

public class TimeCardTransaction implements Transaction {
    int empId;
    LocalDate date;
    double hours;

    public TimeCardTransaction(int empId, LocalDate date, double hours) {
        this.empId = empId;
        this.date = date;
        this.hours = hours;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);

        if (e != null) {
            if (e.classification instanceof HourlyClassification) {
                HourlyClassification hc = (HourlyClassification) e.classification;
                hc.addTimeCard(new TimeCard(date, hours));
            } else {
                throw new RuntimeException("Tried to add time card to non-hourly employee.");
            }

        } else {
            throw new RuntimeException("No such employee.");
        }
    }
}
