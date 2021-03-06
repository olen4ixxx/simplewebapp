package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.testng.Assert.assertEquals;

public class EmployeeSetterTest {
    Employee employee;

    @BeforeMethod
    public void setUp() {
        employee = new Employee(1L, "Test", "Testovich", 7L,
                "Developer", Gender.MALE, Timestamp.valueOf("1992-02-07 12:12:12"));
    }

    @Test(timeOut = 10000)
    public void testSetEmployee() throws SQLException {
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.getLong("id")).thenReturn(employee.getEmployeeId());
        Mockito.when(resultSetMock.getString("FirstName")).thenReturn(employee.getFirstName());
        Mockito.when(resultSetMock.getString("LastName")).thenReturn(employee.getLastName());
        Mockito.when(resultSetMock.getLong("DepartmentId")).thenReturn(employee.getDepartmentId());
        Mockito.when(resultSetMock.getString("JobTitle")).thenReturn(employee.getJobTitle());
        Mockito.when(resultSetMock.getString("Gender")).thenReturn(employee.getGender().toString());
        Mockito.when(resultSetMock.getTimestamp("DateOfBirth")).thenReturn(employee.getDateOfBirth());

        Employee expected = employee;
        Employee actual = new Employee();
        EmployeeSetter.setEmployee(actual, resultSetMock);
        assertEquals(expected, actual);
    }
}
