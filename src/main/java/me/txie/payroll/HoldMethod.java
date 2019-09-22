package me.txie.payroll;

public class HoldMethod implements PaymentMethod {
    @Override
    public void pay(Paycheck pc) {
        pc.setField("Disposition", "Hold");
    }
}
