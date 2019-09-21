package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeSalariedTransactionTest {

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 9527;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 3.2);
        t.execute();

        ChangeSalariedTransaction cst = new ChangeSalariedTransaction(empId, 2000.00);
        cst.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);

        PaymentClassification pc = e.classification;
        assertNotNull(pc);
        assertTrue(pc instanceof SalariedClassification);
        SalariedClassification sc = (SalariedClassification) pc;
        assertEquals(2000.00, sc.salary);
        PaymentSchedule ps = e.schedule;
        assertTrue(ps instanceof MonthlySchedule);
    }

}