package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCommissionedEmployeeTest {

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 2500, 9.5);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.name);

        PaymentClassification pc = e.classification;
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertEquals(2500, cc.baseRate);
        assertEquals(9.5, cc.commissionRate);

        PaymentSchedule ps = e.schedule;
        assertTrue(ps instanceof BiweeklySchedule);

        PaymentMethod pm = e.method;
        assertTrue(pm instanceof HoldMethod);
    }
}
