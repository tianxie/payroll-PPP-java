package me.txie.payroll;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {

    private final String newAddress;

    public ChangeAddressTransaction(int empId, String newAddress) {
        super(empId);
        this.newAddress = newAddress;
    }

    @Override
    protected void change(Employee e) {
        e.address = newAddress;
    }
}
