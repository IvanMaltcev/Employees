package com.sky.employee.service;

import com.sky.employee.exception.EmployeeIsNotValidException;
import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.exception.EmployeeStorageIsFullException;
import com.sky.employee.exception.EmployeeAlreadyAddedException;
import com.sky.employee.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final Map<String, Employee> employees;
    private static final byte MAX_AMOUNT_EMPLOYEES = 6;

    public EmployeeServiceImp() {
        this.employees = new HashMap<>();
    }

    @Override
    public Employee addEmployee(String firstName, String lastName, int salary, int department) {

        String validFirstName = validationStringData(firstName);
        String validLastName = validationStringData(lastName);

            if (employees.size() >= MAX_AMOUNT_EMPLOYEES) {

                throw new EmployeeStorageIsFullException("Превышен лимит количества сотрудников в фирме!");
            }

            final Employee employee = new Employee(validFirstName, validLastName, salary, department);

            if (employees.containsKey(employee.getFullName())) {
                throw new EmployeeAlreadyAddedException("Такой сотрудник уже существует!");
            } else {
                employees.put(employee.getFullName(), employee);
            }
            return employees.get(employee.getFullName());


    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {

        String validFirstName = validationStringData(firstName);
        String validLastName = validationStringData(lastName);

        return employees.remove(employees.values().stream()
                .filter(e -> e.getFullName().equals(validFirstName + " " + validLastName))
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден!"))
                .getFullName());

    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {

        String validFirstName = validationStringData(firstName);
        String validLastName = validationStringData(lastName);

        return employees.values().stream()
                .filter(e -> e.getFullName().equals(validFirstName + " " + validLastName))
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден!"));
    }

    @Override
    public Collection<Employee> getListOfEmployees() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private String validationStringData (String string) {
        if (StringUtils.isAlpha(string)) {
            return StringUtils.capitalize(string.toLowerCase());
        } else {
            throw new EmployeeIsNotValidException("Не верно введены имя или фамилия!");
        }
    }
}
