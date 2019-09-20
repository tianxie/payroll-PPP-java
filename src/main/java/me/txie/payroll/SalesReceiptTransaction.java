package me.txie.payroll;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class SalesReceiptTransaction implements Transaction {
    private final int empId;
    private final LocalDate date;
    private final double saleAmount;

    public SalesReceiptTransaction(int empId, LocalDate date, double saleAmount) {
        this.empId = empId;
        this.date = date;
        this.saleAmount = saleAmount;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e != null) {
            PaymentClassification pc = e.classification;
            if (pc instanceof CommissionedClassification) {
                CommissionedClassification cc = (CommissionedClassification) pc;
                cc.addSalesReceipt(new SalesReceipt(date, saleAmount));
            } else {
                throw new RuntimeException("Tried to add sales receipt to non-commissioned employee.");
            }
        } else {
            throw new NoSuchElementException("No such employee.");
        }
    }
}
