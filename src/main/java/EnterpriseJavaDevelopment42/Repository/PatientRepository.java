package EnterpriseJavaDevelopment42.Repository;

import EnterpriseJavaDevelopment42.Model.Patient;
import EnterpriseJavaDevelopment42.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAllByDateOfBirthIsBetween(Date dateOfBirth, Date dateOfBirth2);

    List<Patient> findAllByAdmittedByDepartment(String department);

    List<Patient> findByAdmittedByStatus(Status admittedBy_status);

}
