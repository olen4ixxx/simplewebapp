package com.mastery.java.task.service;

import com.mastery.java.task.dto.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getById(Long id);

    void save(Employee employee);

    void delete(Long id);

    List<Employee> getAll();

    void update(Long id, Employee employee);
}
