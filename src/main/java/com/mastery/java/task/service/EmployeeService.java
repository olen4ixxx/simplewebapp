package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeService {

    public static void setEmployee(Employee employee, ResultSet resultSet) throws SQLException {
        employee.setEmployeeId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("FirstName"));
        employee.setLastName(resultSet.getString("LastName"));
        employee.setDepartmentId(resultSet.getLong("DepartmentId"));
        employee.setJobTitle(resultSet.getString("JobTitle"));
        employee.setGender(Gender.valueOf(resultSet.getString("Gender")));
        employee.setDateOfBirth(resultSet.getTimestamp("DateOfBirth"));
    }

    public static void setStatement(PreparedStatement preparedStatement, Employee employee) throws SQLException {
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setLong(3, employee.getDepartmentId());
        preparedStatement.setString(4, employee.getJobTitle());
        preparedStatement.setString(5, employee.getGender().toString());
        preparedStatement.setTimestamp(6, employee.getDateOfBirth());
    }
}
