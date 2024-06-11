package com.sky.employee.service;

import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.exception.EmployeeStorageIsFullException;
import com.sky.employee.exception.EmployeeAlreadyAddedException;
import com.sky.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final Map<String, Employee> employees;
    private static final byte MAX_AMOUNT_EMPLOYEES = 10;
    private Employee employee;

    public EmployeeServiceImp() {
        this.employees = new HashMap<>();
    }

    @Override
    public Employee addEmployee(String firstName, String lastName) {
        if (employees.size() >= MAX_AMOUNT_EMPLOYEES) {

            throw new EmployeeStorageIsFullException("Превышен лимит количества сотрудников в фирме!");
        }
        employee = new Employee(firstName, lastName);
        if (!employees.containsKey(employee.getFullName())) {
            employees.put(employee.getFullName(), employee);
            return employees.get(employee.getFullName());
        }
        throw new EmployeeAlreadyAddedException("Такой сотрудник уже существует!");
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        employee = new Employee(firstName, lastName);
        if (employees.containsKey(employee.getFullName())) {
            return employees.remove(employee.getFullName());
        }
        throw new EmployeeNotFoundException("Сотрудник не найден!");
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        employee = new Employee(firstName, lastName);
        if (employees.containsKey(employee.getFullName())) {
            return employees.get(employee.getFullName());
        }
        throw new EmployeeNotFoundException("Сотрудник не найден!");
    }

    @Override
    public Collection<Employee> getListOfEmployees() {
        return Collections.unmodifiableCollection(employees.values());
    }
}
