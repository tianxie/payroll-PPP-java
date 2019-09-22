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
        return 0;
    }
}
