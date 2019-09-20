package me.txie.payroll;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SalesReceiptTransactionTest {

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 5;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bill", "Home", 2000, 15.25);
        t.execute();

        SalesReceiptTransaction srt = new SalesReceiptTransaction(empId, LocalDate.of(2019, 9, 20), 250.00);
        srt.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);

        PaymentClassification pc = e.classification;
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;

        SalesReceipt sr = cc.getSalesReceipt(LocalDate.of(2019, 9, 20));
        assertNotNull(sr);
        assertEquals(250.00, sr.saleAmount());
    }
}
