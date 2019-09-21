package me.txie.payroll;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
    public ChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    @Override
    protected void change(Employee e) {
        recordMembership(e);
        e.affiliation = getAffiliation();
    }

    protected abstract Affiliation getAffiliation();
    protected abstract void recordMembership(Employee e);
}
