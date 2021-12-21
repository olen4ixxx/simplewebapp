package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource({"classpath:application.properties"})
public class EmployeeDao {

    private static final Logger logger = LogManager.getLogger();
    @Value("${db.url}")
    private static String url;
    @Value("${db.username}")
    private static String username;
    @Value("${db.password}")
    private static String password;

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("connection OK");
        } catch (SQLException throwables) {
            logger.error("connection ERROR", throwables);
        }
    }

    public List<Employee> index() {
        List<Employee> employees = new ArrayList<>();
        logger.info("index start");
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM employee";
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                Employee employee = new Employee();
                EmployeeService.setEmployee(employee, resultSet);
                employees.add(employee);
            }
            logger.info("index OK");
        } catch (SQLException throwables) {
            logger.error("index ERROR", throwables);
        }
        return employees;
    }

    public Employee show(Long id) {
        logger.info("show start");
        Employee employee = null;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id=?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            employee = new Employee();
            EmployeeService.setEmployee(employee, resultSet);
            logger.info("show OK");
        } catch (SQLException throwables) {
            logger.error("show ERROR", throwables);
        }
        return employee;
    }

    public void save(Employee employee) {
        logger.info("save start");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "INSERT INTO employee(firstname, lastname, departmentid, jobtitle)" +
                                     "VALUES(?, ?, ?, ?, ?, ?)")) {
            EmployeeService.setStatement(preparedStatement, employee);
            preparedStatement.executeUpdate();
            logger.info("save OK");
        } catch (SQLException throwables) {
            logger.error("save ERROR", throwables);
        }
    }

    public void update(Long id, Employee employee) {
        logger.info("update start");
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                """ 
                        UPDATE employee SET FirstName=?, LastName=?, DepartmentId=?,
                        JobTitle=?, Gender=?, DateOfBirth=? WHERE id=?""")) {
            EmployeeService.setStatement(preparedStatement, employee);
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
            logger.info("update OK");
        } catch (SQLException throwables) {
            logger.error("update ERROR", throwables);
        }
    }

    public void delete(Long id) {
        logger.info("delete start");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM employee WHERE id=?")) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            logger.info("delete OK");
        } catch (SQLException throwables) {
            logger.error("delete ERROR", throwables);
        }

    }
}
