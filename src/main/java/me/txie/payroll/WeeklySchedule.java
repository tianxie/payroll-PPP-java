package me.txie.payroll;

import java.time.LocalDate;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return false;
    }
}
