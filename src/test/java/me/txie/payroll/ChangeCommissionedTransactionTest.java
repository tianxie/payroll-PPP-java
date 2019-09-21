package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeCommissionedTransactionTest {

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 9527;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        ChangeCommissionedTransaction cct = new ChangeCommissionedTransaction(empId, 2500.00, 3.2);
        cct.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);

        PaymentClassification pc = e.classification;
        assertNotNull(pc);
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertEquals(2500.00, cc.baseRate());
        assertEquals(3.2, cc.commissionRate());
        PaymentSchedule ps = e.schedule;
        assertTrue(ps instanceof BiweeklySchedule);
    }
}