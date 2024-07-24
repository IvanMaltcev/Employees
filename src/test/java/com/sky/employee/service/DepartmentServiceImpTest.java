package com.sky.employee.service;

import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImpTest {

    @Mock
    EmployeeService employeeServiceMock;

    private DepartmentService departmentService;
    private final List<Employee> employees = List.of(
            new Employee("Ivanova", "Elena", 50000, 1),
            new Employee("Sidorov", "Igor", 100000, 3),
            new Employee("Smirnova", "Anna", 30000, 1),
            new Employee("Novikov", "Nikita", 20000, 2),
            new Employee("Dorohov", "Nikolay", 90000, 3)
    );

    @BeforeEach
    public void setUp() {

        departmentService = new DepartmentServiceImp(employeeServiceMock);

        when(employeeServiceMock.getListOfEmployees()).thenReturn(List.of(
                employees.get(0),
                employees.get(1),
                employees.get(2),
                employees.get(3),
                employees.get(4)
        ));
    }

    @Test
    public void getListOfAllEmployeesTesting() {

        Map<Integer, List<Employee>> actual = departmentService.getListOfAllEmployees();

        Map<Integer, List<Employee>> expected = new HashMap<>();

        expected.put(1, List.of(
                employees.get(0),
                employees.get(2)
        ));
        expected.put(2, List.of(
                employees.get(3)
        ));
        expected.put(3, List.of(
                employees.get(1),
                employees.get(4)
        ));

        assertEquals(expected, actual);
        verify(employeeServiceMock, times(1)).getListOfEmployees();
    }

    @Test
    public void getListOfAllEmployeesWhenEmployeesDoNotExistTesting() {
        when(employeeServiceMock.getListOfEmployees()).thenReturn(emptyList());

        assertTrue(departmentService.getListOfAllEmployees().isEmpty());
    }

    @Test
    public void getListOfEmployeesByDepartmentTesting() {

        List<Employee> actual = departmentService.getListOfEmployeesByDepartment(1);

        List<Employee> expected = new ArrayList<>();

        expected.add(employees.get(0));
        expected.add(employees.get(2));

        assertEquals(expected, actual);
        verify(employeeServiceMock, times(1)).getListOfEmployees();

    }

    @Test
    public void exceptionWhenEmployeesDoNotExistInDepartmentTesting() {

        assertThrows(EmployeeNotFoundException.class,
                () -> departmentService.getListOfEmployeesByDepartment(4));
    }

    @ParameterizedTest
    @MethodSource("ParamForSumTest")
    public void sumSalaryByDepartmentTesting(int departmentNumber, int sumSalary) {

        int actualSum = departmentService.sumSalaryByDepartment(departmentNumber);

        assertEquals(sumSalary, actualSum);
        verify(employeeServiceMock, times(1)).getListOfEmployees();

    }

    @ParameterizedTest
    @MethodSource("ParamForFindMaxTest")
    public void findEmployeeWithMaxSalaryByDepartmentTesting(int departmentNumber, Employee employee) {

        Employee actual = departmentService.findEmployeeWithMaxSalaryByDepartment(departmentNumber);

        assertEquals(employee, actual);
        verify(employeeServiceMock, times(1)).getListOfEmployees();

    }

    @ParameterizedTest
    @MethodSource("ParamForFindMinTest")
    public void findEmployeeWithMinSalaryByDepartmentTesting(int departmentNumber, Employee employee) {

        Employee actual = departmentService.findEmployeeWithMinSalaryByDepartment(departmentNumber);

        assertEquals(employee, actual);
        verify(employeeServiceMock, times(1)).getListOfEmployees();

    }

    public static Stream<Arguments> ParamForSumTest() {
        return Stream.of(
                Arguments.of(1, 80000),
                Arguments.of(2, 20000),
                Arguments.of(3, 190000)
        );
    }

    public static Stream<Arguments> ParamForFindMaxTest() {
        return Stream.of(
                Arguments.of(1,
                        new Employee("Ivanova", "Elena", 50000, 1)),
                Arguments.of(2,
                        new Employee("Novikov", "Nikita", 20000, 2)),
                Arguments.of(3,
                        new Employee("Sidorov", "Igor", 100000, 3))
        );
    }

    public static Stream<Arguments> ParamForFindMinTest() {
        return Stream.of(
                Arguments.of(1,
                        new Employee("Smirnova", "Anna", 30000, 1)),
                Arguments.of(2,
                        new Employee("Novikov", "Nikita", 20000, 2)),
                Arguments.of(3,
                        new Employee("Dorohov", "Nikolay", 90000, 3))
        );
    }

}
