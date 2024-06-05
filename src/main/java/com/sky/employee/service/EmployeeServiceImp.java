package com.sky.employee.service;

import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.exception.EmployeeStorageIsFullException;
import com.sky.employee.exception.EmployeeAlreadyAddedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private static final byte MAX_AMOUNT_EMPLOYEES = 10;
    private Employee employee;

    @Override
    public Employee addEmployee(String firstName, String lastName) {
        if (employees.size() > MAX_AMOUNT_EMPLOYEES) {

            throw new EmployeeStorageIsFullException("Превышен лимит количества сотрудников в фирме!");
        }
        employee = new Employee(firstName, lastName);
        if (!employees.contains(employee)) {
            employees.add(employee);
            return employee;
        }
        throw new EmployeeAlreadyAddedException("Такой сотрудник уже существует!");
    }

    @Override
    public Employee deleteEmployee(String firstName, String lastName) {
        employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            employees.remove(employee);
            return employee;
        }
        throw new EmployeeNotFoundException("Сотрудник не найден!");
    }

    @Override
    public Employee getEmployee(String firstName, String lastName) {
        employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException("Сотрудник не найден!");
    }

    @Override
    public List<Employee> getListOfEmployees() {
        return employees;
    }
}
