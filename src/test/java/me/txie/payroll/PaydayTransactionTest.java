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

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 29);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeNoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 20);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 0.0);
    }

    private void validateHourlyPaycheck(PaydayTransaction pt, int empId, LocalDate payDate, double pay) {
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);

        assertEquals(payDate, pc.payDate());
        assertEquals(pay, pc.grossPay());
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(0.0, pc.deductions());
        assertEquals(pay, pc.netPay());
    }

    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 20); // Friday
        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 2.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 30.5);
    }

    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 20); // Friday
        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 9.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, (8 + 1.5) * 15.25);
    }

    @Test
    public void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 19); // Thursday
        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 9.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 20); // Friday
        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 2.0);
        tct.execute();
        TimeCardTransaction tct2 = new TimeCardTransaction(empId, payDate.minusDays(1), 5.0);
        tct2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 7 * 15.25);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2019, 9, 20); // Friday
        LocalDate dateInPreviousPayPeriod = LocalDate.of(2019, 9, 13);

        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 2.0);
        tct.execute();
        TimeCardTransaction tct2 = new TimeCardTransaction(empId, dateInPreviousPayPeriod, 5.0);
        tct2.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 2 * 15.25);
    }
}