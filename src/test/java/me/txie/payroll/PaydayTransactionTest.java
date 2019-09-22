package me.txie.payroll;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaydayTransactionTest {

    @Test
    public void testPaySingleSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.payDate());
        assertEquals(1000.00, pc.grossPay());
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(0.0, pc.deductions());
        assertEquals(1000.00, pc.netPay());
    }
}