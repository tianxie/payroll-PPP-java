package me.txie.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeUnaffiliatedTransactionTest {

    @Test
    public void testChangeUnaffiliatedTransaction() {
        int empId = 8;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        int memberId = 7743;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();

        ChangeUnaffiliatedTransaction cut = new ChangeUnaffiliatedTransaction(empId);
        cut.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        Affiliation affiliation = e.affiliation;
        assertNotNull(affiliation);
        assertTrue(affiliation instanceof NoAffiliation);

        Employee member = PayrollDatabase.getUnionMember(memberId);
        assertNull(member);
    }

}