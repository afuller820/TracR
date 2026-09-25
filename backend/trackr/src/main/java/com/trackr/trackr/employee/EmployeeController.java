package com.trackr.trackr.employee;

/*
* Handles incoming http requests, delegates to service layer,
* and returns the appropriate response
*
* */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*marks the class as a Spring MVC Controller where every
method returns a domain object instead of a view*/
@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired //allows the controller to delegate the business logic back to service layer
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Use this to handle all GET requests
    @GetMapping
    public List<Employee> getEmployees(
            // All the params to use to get employees from database
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String jobTitle){
                if (firstName != null && lastName != null) {
                    return employeeService.getEmployeeByFirstAndLastName(firstName, lastName);
                }
                else if (email != null) {
                    return employeeService.getEmployeeByEmail(email);
                }
                else if (jobTitle != null) {
                    return employeeService.getEmployeesByJobTitle(jobTitle);
                }
                else {
                    return employeeService.getEmployees();
                }
    }

    @PostMapping //handles POST request to add new employees to database\
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
                Employee createdEmployee = employeeService.addEmployee(employee);
                return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED); //return a created player with an http status of 201 created if successful
    }

    @PutMapping //handles PUT requests to update an existing player in the database
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
                Employee resultPlayer = employeeService.updateEmployee(employee);
                if (resultPlayer != null) {
                    return new ResponseEntity<>(resultPlayer, HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
    }

    @DeleteMapping("/{employeeEmail}") // handles DELETE requests to delete an employee by email
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeEmail) {
                employeeService.deleteEmployee(employeeEmail);
                return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
