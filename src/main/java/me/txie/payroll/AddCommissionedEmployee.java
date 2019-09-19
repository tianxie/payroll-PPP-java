package me.txie.payroll;

public class AddCommissionedEmployee extends AddEmployeeTransaction {

    double baseRate;
    double commissionRate;

    public AddCommissionedEmployee(int empId, String name, String address, double baseRate, double commissionRate) {
        super(empId, name, address);
        this.baseRate = baseRate;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return new CommissionedClassification(baseRate, commissionRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return new BiweeklySchedule();
    }
}
