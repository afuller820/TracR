package com.trackr.trackr.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
/* Extends the JPA Repository interface, providing CRUD
    Operations for the Employee entity
 */
    // Creating deleteByEmail method
    void deleteByEmail(String employeeEmail);
    Optional<Employee> findByEmail(String email); // For when employees aren't found in repository
}
