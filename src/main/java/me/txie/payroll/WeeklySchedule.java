package me.txie.payroll;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return payDate.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate date) {
        return date.minusDays(6);
    }

    @Override
    public String toString() {
        return "weekly";
    }
}
