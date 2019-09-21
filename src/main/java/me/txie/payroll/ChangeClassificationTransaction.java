package me.txie.payroll;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeClassificationTransaction(int empId) {
        super(empId);
    }

    @Override
    protected void change(Employee e) {
        e.classification = getClassification();
        e.schedule = getSchedule();
    }

    protected abstract PaymentClassification getClassification();

    protected abstract PaymentSchedule getSchedule();
}
