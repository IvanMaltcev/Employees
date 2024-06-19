package com.sky.employee.service;

import com.sky.employee.model.Employee;

import java.util.List;

public interface DepartmentService {

    Employee findEmployeeWithMaxSalaryByDepartment(int department);

    Employee findEmployeeWithMinSalaryByDepartment(int department);

    List<Employee> getListOfEmployeesByDepartment(int department);

    List<Employee> getListOfAllEmployees();

}
