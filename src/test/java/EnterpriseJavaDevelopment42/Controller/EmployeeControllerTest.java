package EnterpriseJavaDevelopment42.Controller;

import EnterpriseJavaDevelopment42.Model.Employee;
import EnterpriseJavaDevelopment42.Repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static EnterpriseJavaDevelopment42.Model.Status.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Employee employee1 = new Employee(356712, "cardiology","Alonso Flores", ON_CALL);
        Employee employee2 = new Employee(564134, "immunology", "Sam Ortega", ON);
        Employee employee3 = new Employee(172456, "psychiatric", "John Paul Armes", OFF);
        Employee employee4 = new Employee(761527, "cardiology", "German Ruiz", OFF);
        employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4));
    }

    @AfterEach
    void tearDown() {

        employeeRepository.deleteAll();

    }

    @DisplayName("List Test")
    @Test
    void list() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("cardiology"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("immunology"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("psychiatric"));

    }

    @DisplayName("Test Employee Id")
    @Test
    void get_id() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/doctors/doctorsId/356712"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        assertEquals(employee.getEmployeeId(), 356712);


    }
    @DisplayName("Test list of status OFF")
    @Test
    void listStatusOff() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/doctors/OFF"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Employee> employees = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {});
        assertEquals(2, employees.size());
    }

    @DisplayName("Test List by departments")
    @Test
    void getDepartment() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/doctors/department/cardiology"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Employee> employees = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {});
        assertEquals(2, employees.size());
    }

    @DisplayName("Test to create new employee")
    @Test
    void create() throws Exception{

        Employee employee5 = new Employee(123456, "Test", "Test Arudo", ON);
        String payload = objectMapper.writeValueAsString(employee5);
        MvcResult mvcResult = mockMvc.perform(post("/newDoctor")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        assertEquals(employee5.getEmployeeId(), employee.getEmployeeId());
        assertEquals(employee5.getName(), employee.getName());
        assertEquals(employee5.getDepartment(), employee.getDepartment());
        assertTrue(employeeRepository.findById(123456).isPresent());

    }

    @DisplayName("Test Update with PUT Status value")
    @Test
    void updateStatus() throws Exception {

        Employee updatedEmployee = new Employee(564134, "immunology", "Sam Ortega", OFF);
        String payload = objectMapper.writeValueAsString(updatedEmployee);

        MvcResult mvcResult = mockMvc.perform(put("/updateDoctorStatus/564134")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        assertEquals(updatedEmployee.getEmployeeId(), employee.getEmployeeId());
        assertEquals(updatedEmployee.getName(), employee.getName());
        assertEquals(updatedEmployee.getStatus(), employee.getStatus());

    }

    @DisplayName("Test update with PUT department value")
    @Test
    void updateDepartment() throws Exception {

        Employee updatedEmployee = new Employee(564134, "cardiology", "Sam Ortega", ON);
        String payload = objectMapper.writeValueAsString(updatedEmployee);

        MvcResult mvcResult = mockMvc.perform(put("/updateDoctorDepartment/564134")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Employee employee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        assertEquals(updatedEmployee.getEmployeeId(), employee.getEmployeeId());
        assertEquals(updatedEmployee.getName(), employee.getName());
        assertEquals(updatedEmployee.getStatus(), employee.getStatus());
        assertEquals(updatedEmployee.getDepartment(), employee.getDepartment());

    }
}