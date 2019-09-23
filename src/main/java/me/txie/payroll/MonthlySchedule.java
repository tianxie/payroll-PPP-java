package me.txie.payroll;

import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return isLastDayOfMonth(payDate);
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate date) {
        int days = 0;
        while (date.plusDays(days - 1).getMonth() == date.getMonth()) {
            days--;
        }

        return date.plusDays(days);
    }

    private boolean isLastDayOfMonth(LocalDate payDate) {
        int m1 = payDate.getMonthValue();
        int m2 = payDate.plusDays(1).getMonthValue();
        return (m1 != m2);
    }

    @Override
    public String toString() {
        return "monthly";
    }
}
