package com.sky.employee.service;

import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.exception.EmployeeStorageIsFullException;
import com.sky.employee.exception.EmployeeAlreadyAddedException;
import com.sky.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final List<Employee> employees;
    private static final byte MAX_AMOUNT_EMPLOYEES = 10;
    private Employee employee;

    public EmployeeServiceImp() {
        this.employees = new ArrayList<>();
    }

    @Override
    public Employee addEmployee(String firstName, String lastName) {
        if (employees.size() >= MAX_AMOUNT_EMPLOYEES) {

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
    public Employee removeEmployee(String firstName, String lastName) {
        employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            employees.remove(employee);
            return employee;
        }
        throw new EmployeeNotFoundException("Сотрудник не найден!");
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            return employee;
        }
        throw new EmployeeNotFoundException("Сотрудник не найден!");
    }

    @Override
    public List<Employee> getListOfEmployees() {
        return Collections.unmodifiableList(employees);
    }
}
