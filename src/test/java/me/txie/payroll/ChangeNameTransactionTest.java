package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChangeNameTransactionTest {

    @Test
    public void testChangeNameTransaction() {
        int empId = 8;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bob");
        cnt.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals("Bob", e.name);
    }
}