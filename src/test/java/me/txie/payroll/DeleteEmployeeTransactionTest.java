package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteEmployeeTransactionTest {

    @Test
    public void testDeleteEmployee() {
        int empId = 100;
        AddSalariedEmployee add = new AddSalariedEmployee(empId, "Bill", "Home", 1000.00);
        add.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);

        DeleteEmployeeTransaction delete = new DeleteEmployeeTransaction(empId);
        delete.execute();

        e = PayrollDatabase.getEmployee(empId);
        assertNull(e);
    }
}
