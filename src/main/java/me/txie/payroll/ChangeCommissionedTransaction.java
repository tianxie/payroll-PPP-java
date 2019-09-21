package me.txie.payroll;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {

    private final double baseRate;
    private final double commissionRate;

    public ChangeCommissionedTransaction(int empId, double baseRate, double commissionRate) {
        super(empId);
        this.baseRate = baseRate;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new CommissionedClassification(baseRate, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
