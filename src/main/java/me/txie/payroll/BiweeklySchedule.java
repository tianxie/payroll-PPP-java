package me.txie.payroll;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BiweeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return payDate.getDayOfWeek() == DayOfWeek.FRIDAY &&
                payDate.getDayOfMonth() % 2 == 0;
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate date) {
        return date.minusDays(13);
    }

    @Override
    public String toString() {
        return "bi-weekly";
    }
}
