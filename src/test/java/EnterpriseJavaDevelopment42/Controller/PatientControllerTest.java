package EnterpriseJavaDevelopment42.Controller;

import EnterpriseJavaDevelopment42.Model.Employee;
import EnterpriseJavaDevelopment42.Model.Patient;
import EnterpriseJavaDevelopment42.Repository.EmployeeRepository;
import EnterpriseJavaDevelopment42.Repository.PatientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static EnterpriseJavaDevelopment42.Model.Status.*;
import static EnterpriseJavaDevelopment42.Model.Status.OFF;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PatientRepository patientRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Employee employee1 = new Employee(356712, "cardiology","Alonso Flores", ON_CALL);
        Employee employee2 = new Employee(564134, "immunology", "Sam Ortega", ON);
        Employee employee3 = new Employee(172456, "psychiatric", "John Paul Armes", OFF);
        Employee employee4 = new Employee(761527, "cardiology", "German Ruiz", OFF);
        employeeRepository.saveAll(List.of(employee1,employee2,employee3,employee4));

        Patient patient1 = new Patient("Jaime Jordan", new Date(1984,03,02), employee2);
        Patient patient2 = new Patient("Marian Garcia", new Date(1972,01,12), employee3);
        Patient patient3 = new Patient("Julia Dusterdieck", new Date(1954,06,11), employee1);
        Patient patient4 = new Patient("Steve McDuck", new Date(1931,11,10), employee4);
        patientRepository.saveAll(List.of(patient1, patient2, patient3, patient4));

    }

    @AfterEach
    void tearDown() {

        patientRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Query(value = "ALTER TABLE 'patient' AUTO_INCREMENT = 1", nativeQuery = true)
    @DisplayName("List Test")
    @Test
    void list() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Jaime Jordan"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Steve McDuck"));

    }


    @DisplayName("Test Patient Id")
    @Test
    void get_id() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/patients/patientsId/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Patient patient = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Patient.class);
        assertEquals(patient.getPatientId(), 3);
        assertEquals(patient.getName(), "Julia Dusterdieck");

    }

    @DisplayName("Test date of birth is between")
    @Test
    void findAllByDateOfBirthIsBetween() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/patients/byDate?dateOfBirth=1940/01/01&dateOfBirth2=2020/05/03"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Patient> patients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Patient>>() {});
        assertEquals(3, patients.size());

    }

    @DisplayName("Test list of patients by doctor departement")
    @Test
    void findAllByAdmittedByDepartment() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/patients/department/cardiology"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Patient> patients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Patient>>() {});
        assertEquals(2, patients.size());

    }

    @DisplayName("Test list of patients with doctor status in OFF")
    @Test
    void patientsListByDoctorOff() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/patients/doctorOFF"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        List<Patient> patients = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Patient>>() {});
        assertEquals(2, patients.size());

    }

    @Test
    void create() throws Exception{

        Patient patientNew = new Patient("Test Arudo", new Date(1980,06,14), new Employee(156545, "orthopaedic", "Paolo Rodriguez", ON_CALL));
        String payload = objectMapper.writeValueAsString(patientNew);
        MvcResult mvcResult = mockMvc.perform(post("/newPatient")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Patient patient = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Patient.class);
        assertEquals(patientNew.getName(),patient.getName());
        assertEquals(patientNew.getAdmittedBy(), patient.getAdmittedBy());
        assertEquals(patientNew.getDateOfBirth(), patient.getDateOfBirth());

    }

    @Test
    void updatePatientInfo() throws Exception {

        String newName = "Robert Lewandosky";
        MvcResult mvcResult = mockMvc.perform(patch("/updatePatient/3")
                        .content(newName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Patient updatedPatient = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Patient.class);
        assertEquals(newName, updatedPatient.getName());

    }
}