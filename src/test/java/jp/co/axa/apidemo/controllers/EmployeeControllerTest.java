package jp.co.axa.apidemo.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

import java.util.Arrays;

public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;
	private Employee employee1;
	private Employee employee2;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

		// prepare dummy data
		employee1 = new Employee();
		employee1.setId(1L);
		employee1.setName("John Doe");
		employee1.setDepartment("DEP 1");
		employee1.setSalary(4000);

		employee2 = new Employee();
		employee2.setId(2L);
		employee2.setName("Miki Ler");
		employee2.setDepartment("DEP 2");
		employee2.setSalary(7800);

	}

	@Test
	public void testGetEmployees() throws Exception {
		// mock service
		when(employeeService.retrieveEmployees()).thenReturn(Arrays.asList(employee1, employee2));

		// test getEmployees method
		this.mockMvc.perform(get("/api/v1/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size", is(2)))
				.andExpect(jsonPath("$.employees[0].name", is("John Doe")))
				.andExpect(jsonPath("$.employees[0].department", is("DEP 1")))
				.andExpect(jsonPath("$.employees[0].salary", is(4000)))
				.andExpect(jsonPath("$.employees[1].name", is("Miki Ler")))
				.andExpect(jsonPath("$.employees[1].department", is("DEP 2")))
				.andExpect(jsonPath("$.employees[1].salary", is(7800)));
	}

	@Test
	public void testGetEmployee() throws Exception {
		// test get employee method
		// mock service
		when(employeeService.getEmployee(any())).thenReturn(employee1);

		// test getEmployees method
		this.mockMvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee.name", is("John Doe")))
				.andExpect(jsonPath("$.employee.department", is("DEP 1")))
				.andExpect(jsonPath("$.employee.salary", is(4000)));

		when(employeeService.getEmployee(any())).thenReturn(null);

		// test getEmployees method for null result
		this.mockMvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testSaveEmployee() throws Exception {
		// test save employee method
		// mock service
		employeeService.saveEmployee(any(Employee.class));

		// test getEmployees method
		String employeeJson = "{\"name\":\"John Doe\",\"department\":\"Developer\"}";
		mockMvc.perform(post("/employees")
				.content(employeeJson))
				.andExpect(status().isNotFound());

		when(employeeService.getEmployee(any())).thenReturn(null);

		// test getEmployees method for null result
		this.mockMvc.perform(get("/api/v1/employees/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		// test delete employee method
		// mock service
		employeeService.deleteEmployee(anyLong());

		this.mockMvc.perform(delete("/employees/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		// test update employee method
		// mock service
		employeeService.deleteEmployee(anyLong());
		String updatedEmployeeJson = "{\"name\":\"John Doe\",\"department\":\"Developer\"}";

		this.mockMvc.perform(put("/employees/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedEmployeeJson))
				.andExpect(status().isNotFound());
	}

}
