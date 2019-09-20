package me.txie.payroll;

import java.util.HashMap;
import java.util.Map;

public abstract class PayrollDatabase {

    private static Map<Integer, Employee> employees = new HashMap<>();

    private static Map<Integer, Employee> members = new HashMap<>();

    public static void addEmployee(int empId, Employee e) {
        employees.put(empId, e);
    }

    public static Employee getEmployee(int id) {
        return employees.get(id);
    }

    public static void deleteEmployee(int id) {
        employees.remove(id);
    }

    public static void addUnionMember(int memberId, Employee e) {
        members.put(memberId, e);
    }

    public static Employee getUnionMember(int memberId) {
        return members.get(memberId);
    }
}
