package com.mastery.java.task.service.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.service.EmployeeService;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class EmployeeServiceImplTest {

    Employee employee1;
    Employee employee2;
    Map<String, Employee> employees;
    EmployeeDao employeeDaoMock;
    EmployeeService employeeService;

    @BeforeMethod
    public void setUp() {
        employee1 = new Employee(1L, "Test", "Testovich", 7L,
                "Developer", Gender.MALE, Timestamp.valueOf("1992-02-07 12:12:12"));
        employee2 = new Employee(2L, "Tom", "Tomovich", 13L,
                "DevOps", Gender.FEMALE, Timestamp.valueOf("1982-02-07 12:12:12"));
        employees = Map.of("1", employee1, "2", employee2);
        employeeDaoMock = Mockito.mock(EmployeeDao.class);
        Mockito.when(employeeDaoMock.getById(1L)).thenReturn(employees.get("1"));
        Mockito.when(employeeDaoMock.getById(2L)).thenReturn(employees.get("2"));
        Mockito.when(employeeDaoMock.findAll()).thenReturn(new ArrayList<>(employees.values()));
        employeeService = new EmployeeServiceImpl(employeeDaoMock);
    }

    @Test
    public void testGetById() {
        Employee expected = employee1;
        Employee actual = employeeService.getById(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void testSave() {

    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testGetAll() {
        Map<String, Employee> expected = employees;
        Map<String, Employee> actual =
                employeeService.getAll().stream()
                        .collect(Collectors.toMap(e -> String.valueOf(e.getEmployeeId()), e -> e));
        assertEqualsDeep(expected, actual);
    }

    @Test
    public void testUpdate() {
    }
}