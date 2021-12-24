package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class EmployeeDao {
    private static final Logger log = LogManager.getLogger();
    private static final String INIT_QUERY = """
            CREATE TABLE IF NOT EXISTS employee (
                id BIGSERIAL PRIMARY KEY,
                firstName varchar(30),
                lastName varchar(30),
                departmentId bigint,
                jobTitle varchar(30),
                gender varchar(6),
                dateOfBirth date );""";
    private static final String INSERT_QUERY = """
            insert into employee (firstname, lastname, departmentid, jobtitle, gender, DateOfBirth) 
            values  ('Thomas', 'Anderson', 13, 'DevOps', 'MALE', '1982-11-11');""";
    private Connection connection;

    {
        Properties properties = new Properties();
        ClassLoader classLoader = EmployeeDao.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Driver registration failed", e);
        }
        String url = (String) properties.get("db.url");
        String username = (String) properties.get("db.username");
        String password = (String) properties.get("db.password");
        String driver = (String) properties.get("db.driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("Driver registration failed", e);
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
            log.info("connection OK");
            Statement statement = connection.createStatement();
            statement.executeQuery(INIT_QUERY);
            statement.execute(INSERT_QUERY);

        } catch (SQLException throwables) {
            log.error("connection ERROR", throwables);
        }
    }

    public EmployeeDao() {
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        log.info("index start");
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM employee";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Employee employee = new Employee();
                EmployeeSetter.setEmployee(employee, resultSet);
                employees.add(employee);
            }
            log.info("index OK");
        } catch (SQLException throwables) {
            log.error("index ERROR", throwables);
        }
        return employees;
    }

    public Employee getById(Long id) {
        log.info("show start");
        Employee employee = null;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id=?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            employee = new Employee();
            EmployeeSetter.setEmployee(employee, resultSet);
            log.info("show OK");
        } catch (SQLException throwables) {
            log.error("show ERROR", throwables);
        }
        return employee;
    }

    public void save(Employee employee) {
        log.info("save start");
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO employee(firstname, lastname, departmentid, jobtitle, gender, DateOfBirth)" +
                        "VALUES(?, ?, ?, ?, ?, ?)")) {
            EmployeeSetter.setStatement(preparedStatement, employee);
            preparedStatement.executeUpdate();
            log.info("save OK");
        } catch (SQLException throwables) {
            log.error("save ERROR", throwables);
        }
    }

    public void update(Long id, Employee employee) {
        log.info("update start");
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                """ 
                        UPDATE employee SET FirstName=?, LastName=?, DepartmentId=?,
                        JobTitle=?, Gender=?, DateOfBirth=? WHERE id=?""")) {
            EmployeeSetter.setStatement(preparedStatement, employee);
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
            log.info("update OK");
        } catch (SQLException throwables) {
            log.error("update ERROR", throwables);
        }
    }

    public void delete(Long id) {
        log.info("delete start");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM employee WHERE id=?")) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            log.info("delete OK");
        } catch (SQLException throwables) {
            log.error("delete ERROR", throwables);
        }

    }

}
