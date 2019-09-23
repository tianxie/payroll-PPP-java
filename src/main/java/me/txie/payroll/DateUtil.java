package me.txie.payroll;

import java.time.LocalDate;

public abstract class DateUtil {

    public static boolean isInPayPeriod(LocalDate date, LocalDate payPeriodStartDate, LocalDate payPeriodEndDate) {
        return (date.isEqual(payPeriodStartDate) || date.isAfter(payPeriodStartDate)) &&
                (date.isEqual(payPeriodEndDate) || date.isBefore(payPeriodEndDate));
    }
}
