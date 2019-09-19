package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddHourlyEmployeeTest {

    @Test
    public void testAddHourlyEmployee() {
        int empId = 10;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 200.00);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.name);

        PaymentClassification pc = e.classification;
        assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        assertEquals(200.00, hc.hourlyRate);

        PaymentSchedule ps = e.schedule;
        assertTrue(ps instanceof WeeklySchedule);

        PaymentMethod pm = e.method;
        assertTrue(pm instanceof HoldMethod);
    }
}