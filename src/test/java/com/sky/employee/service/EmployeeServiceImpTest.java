package com.sky.employee.service;

import com.sky.employee.exception.EmployeeAlreadyAddedException;
import com.sky.employee.exception.EmployeeIsNotValidException;
import com.sky.employee.exception.EmployeeNotFoundException;
import com.sky.employee.exception.EmployeeStorageIsFullException;
import com.sky.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImpTest {

    private EmployeeService out;

    private Employee employee;

    @BeforeEach
    public void setUp() {

        out = new EmployeeServiceImp();

        employee = out.addEmployee("Ivanova", "Elena", 50000, 1);

    }

    @Test
    public void addEmployeeTesting() {

        Employee expected = new Employee("Ivanova", "Elena", 50000, 1);

        assertEquals(expected, employee);
        assertEquals(1, out.getListOfEmployees().size());
    }

    @Test
    public void exceptionAddOneEmployeeTwiceTesting() {

        Exception exception = assertThrows(EmployeeAlreadyAddedException.class,
                () -> out.addEmployee("Ivanova", "Elena", 50000, 1));

        assertEquals("Такой сотрудник уже существует!", exception.getMessage());

    }

    @Test
    public void exceptionAddMoreThenMaxAmountEmployeesTesting() {

        out.addEmployee("Sidorov", "Igor", 100000, 3);
        out.addEmployee("Smirnova", "Anna", 30000, 1);
        out.addEmployee("Novikov", "Nikita", 20000, 2);
        out.addEmployee("Kotikova", "Alla", 40000, 1);
        out.addEmployee("Dorohov", "Nikolay", 90000, 3);

        Exception exception = assertThrows(EmployeeStorageIsFullException.class,
                () -> out.addEmployee("Govorov", "Ivan", 10000, 2));

        assertEquals("Превышен лимит количества сотрудников в фирме!", exception.getMessage());
        assertEquals(6, out.getListOfEmployees().size());
    }

    @ParameterizedTest
    @MethodSource("invalidFirstAndLastNamesForTests")
    public void exceptionInvalidFirstOrLastNamesWhenAddingEmployeeTesting(String firstName, String lastName,
                                                                          int salary, int department) {
        Exception exception = assertThrows(EmployeeIsNotValidException.class,
                () -> out.addEmployee(firstName, lastName, salary, department));

        assertEquals("Не верно введены имя или фамилия!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidCaseFirstAndLastNamesForTests")
    public void conversionFirstAndLastNamesToRequiredCaseWhenAddingEmployeeTesting(String firstname,
                                                                                   String lastname,
                                                                                   int salary,
                                                                                   int department) {
        String expectedFirstname = firstname.toLowerCase().substring(0, 1).toUpperCase()
                + firstname.toLowerCase().substring(1);
        String expectedLastname = lastname.toLowerCase().substring(0, 1).toUpperCase()
                + lastname.toLowerCase().substring(1);
        String expectedFullName = expectedFirstname + " " + expectedLastname;

        Employee actual = out.addEmployee(firstname, lastname, salary, department);

        assertEquals(expectedFullName, actual.getFullName());
    }

    @Test
    public void removeEmployeeTesting() {

        assertEquals(employee, out.removeEmployee("Ivanova", "Elena"));
        assertEquals(0, out.getListOfEmployees().size());
    }

    @Test
    public void removeNonExistingEmployeeTesting() {

        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> out.removeEmployee("Govorov", "Ivan"));

        assertEquals("Сотрудник не найден!", exception.getMessage());
        assertEquals(1, out.getListOfEmployees().size());
    }

    @ParameterizedTest
    @MethodSource("invalidFirstAndLastNamesForTests")
    public void exceptionInvalidFirstOrLastNamesWhenDeletingEmployeeTesting(String firstName, String lastName) {

        Exception exception = assertThrows(EmployeeIsNotValidException.class,
                () -> out.removeEmployee(firstName, lastName));

        assertEquals("Не верно введены имя или фамилия!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidCaseFirstAndLastNamesForTests")
    public void conversionFirstAndLastNamesToRequiredCaseWhenDeletingEmployeeTesting(String firstname,
                                                                                     String lastname,
                                                                                     int salary,
                                                                                     int department) {

        String expectedFirstname = firstname.toLowerCase().substring(0, 1).toUpperCase()
                + firstname.toLowerCase().substring(1);
        String expectedLastname = lastname.toLowerCase().substring(0, 1).toUpperCase()
                + lastname.toLowerCase().substring(1);
        String expectedFullName = expectedFirstname + " " + expectedLastname;

        out.addEmployee(firstname, lastname, salary, department);

        assertEquals(expectedFullName, out.removeEmployee(firstname, lastname).getFullName());
    }

    @Test
    public void findEmployeeTesting() {

        assertEquals(employee, out.findEmployee("Ivanova", "Elena"));
    }

    @Test
    public void findNonExistingEmployeeTesting() {

        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> out.findEmployee("Govorov", "Ivan"));

        assertEquals("Сотрудник не найден!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidFirstAndLastNamesForTests")
    public void exceptionInvalidFirstOrLastNamesWhenFindingEmployeeTesting(String firstName, String lastName) {

        Exception exception = assertThrows(EmployeeIsNotValidException.class,
                () -> out.findEmployee(firstName, lastName));

        assertEquals("Не верно введены имя или фамилия!", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidCaseFirstAndLastNamesForTests")
    public void conversionFirstAndLastNamesToRequiredCaseWhenFindingEmployeeTesting(String firstname,
                                                                                    String lastname,
                                                                                    int salary,
                                                                                    int department) {

        String expectedFirstname = firstname.toLowerCase().substring(0, 1).toUpperCase()
                + firstname.toLowerCase().substring(1);
        String expectedLastname = lastname.toLowerCase().substring(0, 1).toUpperCase()
                + lastname.toLowerCase().substring(1);
        String expectedFullName = expectedFirstname + " " + expectedLastname;

        out.addEmployee(firstname, lastname, salary, department);

        assertEquals(expectedFullName, out.findEmployee(firstname, lastname).getFullName());
    }

    @Test
    public void getListOfEmployeesTesting() {

        List<Employee> actual = List.copyOf(out.getListOfEmployees());
        List<Employee> expected = new ArrayList<>();

        expected.add(employee);

        assertEquals(expected, actual);
    }

    public static Stream<Arguments> invalidCaseFirstAndLastNamesForTests() {
        return Stream.of(
                Arguments.of("sidorov", "igor", 100000, 3),
                Arguments.of("SmIrnOvA", "AnnA", 30000, 1),
                Arguments.of("NOVIKOV", "NIKITA", 20000, 2)
        );
    }

    public static Stream<Arguments> invalidFirstAndLastNamesForTests() {
        return Stream.of(
                Arguments.of("Ivanova1", "Elena", 50000, 1),
                Arguments.of("Sidorov", "Igor2", 100000, 3),
                Arguments.of("Smirnova", "", 30000, 1)
        );
    }

}
