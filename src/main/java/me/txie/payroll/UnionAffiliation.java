package me.txie.payroll;

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
        return 0;
    }
}
