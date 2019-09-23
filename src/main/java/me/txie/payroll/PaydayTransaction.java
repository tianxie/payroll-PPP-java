package me.txie.payroll;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaydayTransaction implements Transaction {
    private final LocalDate payDate;
    private Map<Integer, Paycheck> paychecks = new HashMap<>();

    public PaydayTransaction(LocalDate payDate) {
        this.payDate = payDate;
    }

    @Override
    public void execute() {
        List<Integer> empIds = PayrollDatabase.getAllEmployeeIds();

        for (int empId : empIds) {
            Employee e = PayrollDatabase.getEmployee(empId);
            if (e.isPayDate(payDate)) {
                LocalDate startDate = e.getPayPeriodStartDate(payDate);
                Paycheck pc = new Paycheck(startDate, payDate);
                paychecks.put(empId, pc);
                e.payday(pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return paychecks.get(empId);
    }
}
