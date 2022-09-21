package EnterpriseJavaDevelopment42.Service;

import EnterpriseJavaDevelopment42.Model.DTO.PatientNameDTO;
import EnterpriseJavaDevelopment42.Model.Patient;
import EnterpriseJavaDevelopment42.Model.Status;

import java.util.Date;
import java.util.List;

public interface PatientService {

    List<Patient> list(); //metodo para devolver una lista de todos los patients, ejercicio 5 de 4.02

    Patient get(int patientId); //metodo para devolver patients por su patient_id, ejercicio 6 de 4.02

    List<Patient> get(Date dateOfBirth, Date dateOfBirth2); //metodo para devolver patients por su date_of_birth, ejercicio 7 de 4.02

    List<Patient> patientsListByDoctorDepartment(String department); //metodo para devolver una lista de patients por el department de employee, ejercicio 8 de 4.02

    List<Patient> patientsListByDoctorOff(Status admittedBy_status); //metodo para devolver una lista de patients por el status OFF de employee, ejercicio 9 de 4.02

    Patient save(Patient patient); //metodo para añadir un patient ejercicio 1 de 4.0.4

    Patient updatePatientInfo(int id, PatientNameDTO name); //metodo para updatear un patient ejercicio 5 de 4.04





}
