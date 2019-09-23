package me.txie.payroll;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
    double hourlyRate;
    private Map<LocalDate, TimeCard> timeCards = new HashMap<>();

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public TimeCard getTimeCard(LocalDate date) {
        return timeCards.get(date);
    }

    public void addTimeCard(TimeCard timeCard) {
        timeCards.put(timeCard.date(), timeCard);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double totalPay = 0.0;
        for (TimeCard tc : timeCards.values()) {
            if (DateUtil.isInPayPeriod(tc.date(),
                    pc.payPeriodStartDate(),
                    pc.payPeriodEndDate())) {
                totalPay += calculatePayForTimeCard(tc);
            }
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard tc) {
        double overtimeHours = Math.max(0.0, tc.hours() - 8);
        double normalHours = tc.hours() - overtimeHours;
        return hourlyRate * normalHours +
                hourlyRate * 1.5 * overtimeHours;
    }

    @Override
    public String toString() {
        return String.format("${0}/hr", hourlyRate);
    }
}
