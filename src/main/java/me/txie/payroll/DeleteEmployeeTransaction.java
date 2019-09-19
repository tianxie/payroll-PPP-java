package me.txie.payroll;

public class DeleteEmployeeTransaction implements Transaction {
    int id;

    public DeleteEmployeeTransaction(int empId) {
        this.id = empId;
    }

    @Override
    public void execute() {
        PayrollDatabase.deleteEmployee(id);
    }
}
