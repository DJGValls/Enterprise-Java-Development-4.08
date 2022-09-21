package EnterpriseJavaDevelopment42.Service;

import EnterpriseJavaDevelopment42.Model.DTO.PatientNameDTO;
import EnterpriseJavaDevelopment42.Model.Patient;
import EnterpriseJavaDevelopment42.Model.Status;
import EnterpriseJavaDevelopment42.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    //Implementación en servicio de ejercicio 5 (mirar servicio)
    @Override
    public List<Patient> list() {
        return patientRepository.findAll();
    }

    //Implementación en servicio de ejercicio 6 (mirar servicio)
    @Override
    public Patient get(int patientId) {
        return patientRepository.findById(patientId).get();
    }

    //Implementación en servicio de ejercicio 7 (mirar servicio)
    @Override
    public List<Patient> get(Date dateOfBirth, Date dateOfBirth2) {
        return patientRepository.findAllByDateOfBirthIsBetween(dateOfBirth, dateOfBirth2);
    }

    //Implementación en servicio de ejercicio 8 (mirar servicio)
    @Override
    public List<Patient> patientsListByDoctorDepartment(String department) {
        return patientRepository.findAllByAdmittedByDepartment(department);
    }

    //Implementación en servicio de ejercicio 9 (mirar servicio)
    @Override
    public List<Patient> patientsListByDoctorOff(Status admittedBy_status) {
        return patientRepository.findByAdmittedByStatus(Status.OFF);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatientInfo(int id, PatientNameDTO name) {
        Patient storedPatient = get(id);
        storedPatient.setName(name.getName());
        return patientRepository.save(storedPatient);
    }


}
