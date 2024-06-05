package com.sky.employee.controller;

import com.sky.employee.exception.EmployeeAlreadyAddedException;
import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.exception.EmployeeStorageIsFullException;
import com.sky.employee.service.Employee;
import com.sky.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee addEmployee(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName) {
        try {
            return employeeService.addEmployee(firstName, lastName);

        } catch (EmployeeStorageIsFullException | EmployeeAlreadyAddedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/remove")
    public Employee deleteEmployee(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName) {
        try {
            return employeeService.deleteEmployee(firstName, lastName);

        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/find")
    public Employee getEmployee(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName) {
        try {
            return employeeService.getEmployee(firstName, lastName);

        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/list")
    public List<Employee> getListOfEmployees() {
        return employeeService.getListOfEmployees();
    }
}
