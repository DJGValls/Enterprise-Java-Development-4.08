package EnterpriseJavaDevelopment42.Controller;

import EnterpriseJavaDevelopment42.Model.DTO.PatientNameDTO;
import EnterpriseJavaDevelopment42.Model.Patient;
import EnterpriseJavaDevelopment42.Model.Status;
import EnterpriseJavaDevelopment42.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    //ejercicio 5
    @GetMapping("/patients")
    public List<Patient> list(){
        return patientService.list();
    }

    //ejercicio 6
    @GetMapping ("/patients/patientsId/{patientId}")
    public Patient get(@PathVariable(value = "patientId") int patientId) {
        return patientService.get(patientId);
    }

    //ejercicio 7
    @GetMapping("/patients/byDate")
    public List<Patient> findAllByDateOfBirthIsBetween(@RequestParam(value = "dateOfBirth") @DateTimeFormat(pattern="yyyy/MM/dd") Date dateOfBirth, @RequestParam(value = "dateOfBirth2") @DateTimeFormat(pattern="yyyy/MM/dd") Date dateOfBirth2){
        return patientService.get(dateOfBirth, dateOfBirth2);
    }

    //ejercicio 8
    @GetMapping("patients/department/{department}")
    public List<Patient> findAllByAdmittedByDepartment(@PathVariable(value = "department") String department){
        return patientService.patientsListByDoctorDepartment(department);
    }

    @GetMapping("patients/doctorOFF")
    public List<Patient> patientsListByDoctorOff(){
        return patientService.patientsListByDoctorOff(Status.OFF);
    }

    @PostMapping("/newPatient")
    public Patient create(@RequestBody Patient patient){
        return patientService.save(patient);
    }

    @PatchMapping("/updatePatient/{id}")
    public Patient updatePatientInfo(@PathVariable int id, @RequestBody PatientNameDTO name){
        return patientService.updatePatientInfo(id, name);
    }




}
