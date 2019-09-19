package me.txie.payroll;

import java.time.LocalDate;

public class TimeCard {
    private LocalDate date;
    private double hours;

    public TimeCard(LocalDate date, double hours) {
        this.date = date;
        this.hours = hours;
    }

    public LocalDate date() {
        return date;
    }

    public double hours() {
        return hours;
    }
}
