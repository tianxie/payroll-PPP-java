package me.txie.payroll;

import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return isLastDayOfMonth(payDate);
    }

    private boolean isLastDayOfMonth(LocalDate payDate) {
        int m1 = payDate.getMonthValue();
        int m2 = payDate.plusDays(1).getMonthValue();
        return (m1 != m2);
    }
}
