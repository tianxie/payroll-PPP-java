package me.txie.payroll;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TimeCardTransactionTest {

    @Test
    public void testTimeCardTransaction() {
        int empId = 18;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Tom", "Home", 15.25);
        t.execute();

        TimeCardTransaction tct = new TimeCardTransaction(empId, LocalDate.of(2019, 9, 19), 8.0);
        tct.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);


        PaymentClassification pc = e.classification;
        assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;

        TimeCard tc = hc.getTimeCard(LocalDate.of(2019, 9, 19));
        assertNotNull(tc);
        assertEquals(8.0, tc.hours());
    }
}
