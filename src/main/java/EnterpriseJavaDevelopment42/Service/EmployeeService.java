package EnterpriseJavaDevelopment42.Service;

import EnterpriseJavaDevelopment42.Model.Employee;
import EnterpriseJavaDevelopment42.Model.Status;

import java.util.List;

public interface EmployeeService {

    List<Employee> list(); //metodo para devolver una lista de todos los doctores, ejercicio 1 de 4.02

    Employee get(int employeeId); //metodo para devolver employees por su employee_id, ejercicio 2 de 4.02

    List<Employee> getStatus(Status status); //metodo para devolver employees por su estado, ejercicio 3 de 4.02

    List<Employee> getDepartment(String department); //metodo para devolver employees por su departamento, ejercicio 4 de 4.02

    Employee save(Employee employee); //metodo para añadir un employee, ejercicio 2 de 4.04

    Employee update(int employeeId, Employee employee); //metodo para updatear un employee, ejercicios 3 y 4 de 4.04





}
