package com.sky.employee.controller;

import com.sky.employee.model.Employee;
import com.sky.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/salary/sum")
    public int sumSalaryByDepartment(@PathVariable("id") int id) {
        return departmentService.sumSalaryByDepartment(id);
    }

    @GetMapping("/{id}/salary/max")
    public Employee findEmployeeWithMaxSalaryByDepartment(
            @PathVariable("id") int id) {
        return departmentService.findEmployeeWithMaxSalaryByDepartment(id);
    }

    @GetMapping("/{id}/salary/min")
    public Employee findEmployeeWithMinSalaryByDepartment(
            @PathVariable("id") int id) {
        return departmentService.findEmployeeWithMinSalaryByDepartment(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getListOfEmployeesByDepartment(
            @PathVariable("id") int id) {
        return departmentService.getListOfEmployeesByDepartment(id);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> getListOfAllEmployees() {
        return departmentService.getListOfAllEmployees();
    }
}
