package me.txie.payroll;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UnionAffiliation implements Affiliation {
    private final int memberId;
    private final double dues;
    private Map<LocalDate, ServiceCharge> charges = new HashMap<>();

    public UnionAffiliation(int memberId, double dues) {
        this.memberId = memberId;
        this.dues = dues;
    }

    public UnionAffiliation() {
        this(-1, 0.0);
    }

    public ServiceCharge getServiceCharge(LocalDate date) {
        return charges.get(date);
    }

    public void addServiceCharge(ServiceCharge sc) {
        charges.put(sc.date(), sc);
    }

    public int memberId() {
        return memberId;
    }

    public double dues() {
        return dues;
    }

    @Override
    public double calculateDeductions(Paycheck pc) {
        double totalDues = 0;
        // 会费每周五累加一次
        int fridays = numberOfFridaysInPayPeriod(
                pc.payPeriodStartDate(),
                pc.payPeriodEndDate()
        );
        totalDues = dues * fridays;

        for (ServiceCharge charge : charges.values()) {
            if (DateUtil.isInPayPeriod(charge.date(),
                    pc.payPeriodStartDate(), pc.payPeriodEndDate())) {
                totalDues += charge.amount();
            }
        }
        return totalDues;
    }

    private int numberOfFridaysInPayPeriod(LocalDate payPeriodStartDate, LocalDate payPeriodEndDate) {
        int fridays = 0;
        for (LocalDate day = payPeriodStartDate;
             (day.isEqual(payPeriodEndDate) || day.isBefore(payPeriodEndDate));
             day = day.plusDays(1)) {
            if (day.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays++;
            }
        }
        return fridays;
    }
}
