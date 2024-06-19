package com.sky.employee.service;

import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DepartmentServiceImp implements DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentServiceImp(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Employee findEmployeeWithMaxSalaryByDepartment(int department) {
        return findEmployeesByDepartment(department).stream()
                .max(Comparator.comparingInt(e -> e.getSalary()))
                .get();
    }

    @Override
    public Employee findEmployeeWithMinSalaryByDepartment(int department) {
        return findEmployeesByDepartment(department).stream()
                .min(Comparator.comparingInt(e -> e.getSalary()))
                .get();
    }

    @Override
    public List<Employee> getListOfEmployeesByDepartment(int department) {
        return findEmployeesByDepartment(department);
    }

    @Override
    public List<Employee> getListOfAllEmployees() {
        return employeeService.getListOfEmployees().stream()
                .sorted(Comparator.comparingInt(e -> e.getDepartment()))
                .toList();
    }

    private List<Employee> findEmployeesByDepartment(int departmentNumber) {
        List<Employee> employees = employeeService.getListOfEmployees().stream()
                .filter(e -> e.getDepartment() == departmentNumber)
                .toList();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("В этом отделе нет сотрудников!");
        } else {
            return employees;
        }
    }
}


