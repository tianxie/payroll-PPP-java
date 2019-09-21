package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChangeAddressTransactionTest {

    @Test
    public void testChangeAddressTransaction() {
        int empId = 80;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        ChangeAddressTransaction cnt = new ChangeAddressTransaction(empId, "Office");
        cnt.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals("Office", e.address);
    }

}