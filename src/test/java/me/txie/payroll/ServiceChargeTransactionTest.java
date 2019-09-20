package me.txie.payroll;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServiceChargeTransactionTest {

    @Test
    public void testAddServiceCharge() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);

        UnionAffiliation af = new UnionAffiliation();
        e.affiliation = af;
        int memberId = 86;
        PayrollDatabase.addUnionMember(memberId, e);

        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, LocalDate.of(2019, 9, 20), 12.95);
        sct.execute();
        ServiceCharge sc = af.getServiceCharge(LocalDate.of(2019, 9, 20));
        assertNotNull(sc);
        assertEquals(12.95, sc.amount());
    }
}
