package com.sky.employee.controller;

import com.sky.employee.model.Employee;
import com.sky.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee findEmployeeWithMaxSalaryByDepartment(
            @RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMaxSalaryByDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee findEmployeeWithMinSalaryByDepartment(
            @RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMinSalaryByDepartment(department);
    }

    @GetMapping(value = "/all", params = {"departmentId"})
    public List<Employee> getListOfEmployeesByDepartment(
            @RequestParam("departmentId") int department) {
        return departmentService.getListOfEmployeesByDepartment(department);
    }

    @GetMapping("/all")
    public List<Employee> getListOfAllEmployees() {
        return departmentService.getListOfAllEmployees();
    }
}
