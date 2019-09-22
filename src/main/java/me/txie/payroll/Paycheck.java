package me.txie.payroll;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Paycheck {
    private final LocalDate payDate;
    private double netPay;
    private double deductions;
    private double grossPay;
    private Map<String, String> fields = new HashMap<>();

    public Paycheck(LocalDate payDate) {
        this.payDate = payDate;
    }

    public LocalDate payDate() {
        return payDate;
    }

    public double grossPay() {
        return grossPay;
    }

    public double deductions() {
        return deductions;
    }

    public double netPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public String getField(String field) {
        return fields.get(field);
    }

    public void setField(String field, String value) {
        fields.put(field, value);
    }
}
