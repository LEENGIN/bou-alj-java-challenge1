package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing employees.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    // private static final Logger logger =
    // LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees with caching enabled.
     * 
     * @return the employee list, or null if not found
     */
    @Cacheable(value = "employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    /**
     * Retrieves an employee by their ID, with caching enabled.
     * 
     * @param employeeId the ID of the employee
     * @return the employee, or null if not found
     */
    @Cacheable(value = "employee", key = "#employeeId")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        if (!optEmp.isPresent()) {
            // if have no Employee, return empty
            return null;
        } else {
            return optEmp.get();
        }
    }

    /**
     * Saves an employee to the repository.
     * 
     * @param employee the employee to save
     * @return the employee
     */
    @CachePut(value = "employee", key = "#employee.id")
    @CacheEvict(value = "employees", allEntries = true)
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * delete an employee by their ID.
     * 
     * @param employeeId the ID of the employee
     * @return the employee, or null if not found
     */
    @CacheEvict(value = "employee", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    /**
     * update an employee.
     * 
     * @param employee the employee to update
     * @return the employee, or null if not found
     */
    @CachePut(value = "employee", key = "#employee.id")
    @CacheEvict(value = "employees", allEntries = true)
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}