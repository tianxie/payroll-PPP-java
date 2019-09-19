package me.txie.payroll;

public abstract class AddEmployeeTransaction implements Transaction {

    private int empId;
    private String name;
    private String address;

    public AddEmployeeTransaction(int empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    protected abstract PaymentClassification makeClassification();

    protected abstract PaymentSchedule makeSchedule();

    public void execute() {
        PaymentClassification pc = makeClassification();
        PaymentSchedule ps = makeSchedule();
        PaymentMethod pm = new HoldMethod();

        Employee e = new Employee(empId, name, address);
        e.classification = pc;
        e.schedule = ps;
        e.method = pm;
        PayrollDatabase.addEmployee(empId, e);
    }
}
