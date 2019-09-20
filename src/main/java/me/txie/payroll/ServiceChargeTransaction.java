package me.txie.payroll;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class ServiceChargeTransaction implements Transaction {
    private final int memberId;
    private final LocalDate date;
    private final double dues;

    public ServiceChargeTransaction(int memberId, LocalDate date, double dues) {
        this.memberId = memberId;
        this.date = date;
        this.dues = dues;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getUnionMember(memberId);

        if (e != null) {
            if (e.affiliation instanceof UnionAffiliation) {
                UnionAffiliation ua = (UnionAffiliation) e.affiliation;
                ua.addServiceCharge(new ServiceCharge(date, dues));
            } else {
                throw new RuntimeException("Tries to add service charge to union member without a union affiliation.");
            }
        } else {
            throw new NoSuchElementException("No such union member.");
        }
    }
}
