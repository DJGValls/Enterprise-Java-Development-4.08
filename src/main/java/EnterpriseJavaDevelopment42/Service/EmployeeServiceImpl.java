package EnterpriseJavaDevelopment42.Service;

import EnterpriseJavaDevelopment42.Model.Employee;
import EnterpriseJavaDevelopment42.Model.Status;
import EnterpriseJavaDevelopment42.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Implementación en servicio de ejercicio 1 (mirar servicio)
    @Override
    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    //Implementación en servicio de ejercicio 2 (mirar servicio)
    @Override
    public Employee get(int employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    //Implementación en servicio de ejercicio 3 (mirar servicio)

    @Override
    public List<Employee> getStatus(Status status) {
        return employeeRepository.findAllByStatus(status);
    }

    //Implementación en servicio de ejercicio 4 (mirar servicio)
    @Override
    public List<Employee> getDepartment(String department) {
        return employeeRepository.findAllByDepartment(department);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(int employeeId, Employee employee) {
        Employee storedEmployee = get(employeeId);
        storedEmployee.setEmployeeId(employee.getEmployeeId());
        storedEmployee.setDepartment(employee.getDepartment());
        storedEmployee.setName(employee.getName());
        storedEmployee.setStatus(employee.getStatus());
        return employeeRepository.save(employee);
    }


}
