package me.txie.payroll;

import java.time.LocalDate;

public class BiweeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(LocalDate payDate) {
        return false;
    }
}
