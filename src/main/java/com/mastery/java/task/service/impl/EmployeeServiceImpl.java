package com.mastery.java.task.service.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LogManager.getLogger();
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee getById(Long id) {
        log.info("IN EmployeeServiceImpl getById {}", id);
        return employeeDao.getById(id);
    }

    @Override
    public void save(Employee employee) {
        log.info("IN EmployeeServiceImpl save {}", employee);
        employeeDao.save(employee);
    }

    @Override
    public void delete(Long id) {
        log.info("IN EmployeeServiceImpl delete {}", id);
        employeeDao.delete(id);
    }

    @Override
    public List<Employee> getAll() {
        log.info("IN EmployeeServiceImpl getAll");
        return employeeDao.findAll();
    }

    @Override
    public void update(Long id, Employee employee) {
        log.info("IN EmployeeServiceImpl update {}, {}", id, employee);
        employeeDao.update(id, employee);
    }
}
