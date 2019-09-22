package me.txie.payroll;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaydayTransaction implements Transaction {
    private final LocalDate payDate;
    private Map<Integer, Paycheck> payckecks = new HashMap<>();

    public PaydayTransaction(LocalDate payDate) {
        this.payDate = payDate;
    }

    @Override
    public void execute() {
        List<Integer> empIds = PayrollDatabase.getAllEmployeeIds();

        for (int empId : empIds) {
            Employee e = PayrollDatabase.getEmployee(empId);
            if (e.isPayDate(payDate)) {
                Paycheck pc = new Paycheck(payDate);
                payckecks.put(empId, pc);
                e.payday(pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return payckecks.get(empId);
    }
}
