package me.txie.payroll;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    protected Affiliation getAffiliation() {
        return new NoAffiliation();
    }

    @Override
    protected void recordMembership(Employee e) {
        UnionAffiliation ua = (UnionAffiliation) e.affiliation;
        int memberId = ua.memberId();
        PayrollDatabase.removeUnionMember(memberId);
    }
}
