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

    @Test
    public void testSalariedUnionMemberDues() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.payDate());
        assertEquals(1000.00, pc.grossPay());
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(47.1, pc.deductions());
        assertEquals(1000.0 - 47.1, pc.netPay());
    }

    @Test
    public void testHourlyUnionMembersServiceCharge() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();

        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.payDate());
        assertEquals(8 * 15.24, pc.grossPay());
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(9.42 + 19.42, pc.deductions());
        assertEquals((8 * 15.24 - (9.42 + 19.42)), pc.netPay());
    }

    @Test
    public void testServiceChargeSpanningMultiplePayPeriods() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();

        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        LocalDate earlyDate = LocalDate.of(2001, 11, 2); // 不到星期五
        LocalDate lateDate = LocalDate.of(2001, 11, 16); // 下星期五

        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();

        ServiceChargeTransaction sctEarly = new ServiceChargeTransaction(memberId, earlyDate, 100.00);
        sctEarly.execute();

        ServiceChargeTransaction sctLate = new ServiceChargeTransaction(memberId, lateDate, 200.00);
        sctLate.execute();

        TimeCardTransaction tct = new TimeCardTransaction(empId, payDate, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.payDate());
        assertEquals(8 * 15.24, pc.grossPay());
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(9.42 + 19.42, pc.deductions());
        assertEquals((8 * 15.24 - (9.42 + 19.42)), pc.netPay());
    }
}