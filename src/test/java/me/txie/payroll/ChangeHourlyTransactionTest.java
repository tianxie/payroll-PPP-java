package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeHourlyTransactionTest {

    @Test
    public void testChangeHourlyTransaction() {
        int empId = 9527;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 3.2);
        t.execute();

        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.52);
        cht.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);

        PaymentClassification pc = e.classification;
        assertNotNull(pc);
        assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        assertEquals(27.52, hc.hourlyRate);
        PaymentSchedule ps = e.schedule;
        assertTrue(ps instanceof WeeklySchedule);
    }

}