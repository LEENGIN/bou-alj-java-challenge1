package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

/**
 * Controller class for managing Employee related operations.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Endpoint to retrieve all employees.
     * 
     * @return employees
     */
    @GetMapping("/employees")
    public Map<String, Object> getEmployees() {
        // call sevice to retrieve all employees
        List<Employee> employees = employeeService.retrieveEmployees();

        Map<String, Object> response = new HashMap<>();
        response.put("size", employees.size());
        response.put("employees", employees);

        return response;
    }

    /**
     * Endpoint to retrieve an employee by their ID.
     * 
     * @param employeeId the ID of the employee to retrieve
     * @return employee
     */
    @GetMapping("/employees/{employeeId}")
    public Map<String, Object> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        // call sevice to retrieve employee
        Employee employee = employeeService.getEmployee(employeeId);

        Map<String, Object> response = new HashMap<>();
        response.put("employee", employee);

        return response;
    }

    /**
     * Endpoint to save an employee.
     * 
     * @param employee employee
     */
    @PostMapping("/employees")
    public Map<String, Object> saveEmployee(@Valid Employee employee) {

        // call sevice to save employee
        Employee employeeRes = employeeService.saveEmployee(employee);
        logger.info("Employee Saved Successfully");
        Map<String, Object> response = new HashMap<>();
        response.put("result", employeeRes != null ? "success" : "failure");

        return response;
    }

    /**
     * Endpoint to delete an employee by their ID.
     * 
     * @param employeeId the ID of the employee to delete
     */
    @DeleteMapping("/employees/{employeeId}")
    public Map<String, Object> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        // call sevice to delete employee

        Employee emp = employeeService.getEmployee(employeeId);
        Map<String, Object> response = new HashMap<>();
        if (emp != null) {
            // call sevice to update employee
            employeeService.deleteEmployee(employeeId);
            logger.info("Employee Deleted Successfully");

            response.put("result", "success");
        } else {
            // nothing to delete
            response.put("result", "Employee with ID " + employeeId + " not found");
        }
        return response;
    }

    /**
     * Endpoint to update an employee by their ID.
     * 
     * @param employee   the employee to update
     * @param employeeId the ID of the employee to update
     */
    @PutMapping("/employees/{employeeId}")
    public Map<String, Object> updateEmployee(@Valid @RequestBody Employee employee,
            @PathVariable(name = "employeeId") Long employeeId) {
        // employee validation check
        Employee emp = employeeService.getEmployee(employeeId);
        Map<String, Object> response = new HashMap<>();
        if (emp != null) {
            // call sevice to update employee
            employeeService.updateEmployee(employee);
            logger.info("Employee update Successfully");
            response.put("result", "success");
        } else {
            // nothing to delete
            response.put("result", "Employee with ID " + employeeId + " not found");
        }
        return response;
    }

}
