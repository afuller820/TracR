package com.trackr.trackr.employee;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/* This allows Spring to know this is a
Component to interact with

This is the business logic for TrackR
*/

@Component
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    // Retrieves all employees from database
    public List<Employee> getEmployees() {
        return employeeRepository.findAll(); // returns list of all employees
    }

    // Get data of all employees by job title
    public List<Employee> getEmployeesByJobTitle(String jobTitle) {
        return employeeRepository.findAll().stream()
                .filter(employee -> jobTitle.equals(employee.getJobTitle()))
                .collect(Collectors.toList()); // return employees specificaly from job title
    }

    // Get specific employees by email
    public List<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get specific employees by first and last name
    public List<Employee> getEmployeeByFirstAndLastName(String firstName, String lastName) {
        return employeeRepository.findAll().stream()
                .filter(employee -> firstName.equals(employee.getFirstName()) && lastName.equals(employee.getLastName()))
                .collect(Collectors.toList());
    }

    // Add an employee to the database
    public Employee addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    // Update an employee in the database
    public Employee updateEmployee(Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(updatedEmployee.getEmail());

        // Check to see if employee is present
        if (existingEmployee.isPresent()) {
            Employee employeeToUpdate = existingEmployee.get();
            employeeToUpdate.getFirstName();
            employeeToUpdate.getLastName();
            employeeToUpdate.getEmail();
            employeeToUpdate.getJobTitle();
            employeeToUpdate.getSalary();

            employeeRepository.save(employeeToUpdate);
            return employeeToUpdate;
        }
        return null;
    }

    // Delete an employee
    @Transactional //maintains data during the delete operation
    public void deleteEmployee(String employeeEmail) {
        employeeRepository.deleteByEmail(employeeEmail);
    }
}
