package EnterpriseJavaDevelopment42.Controller;


import EnterpriseJavaDevelopment42.Model.Employee;
import EnterpriseJavaDevelopment42.Model.Status;
import EnterpriseJavaDevelopment42.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //Ejercicio 1
    @GetMapping("/doctors")
    public List<Employee> list(){
        return employeeService.list();
    }

    //Ejercicio 2
    @GetMapping ("/doctors/doctorsId/{employeeId}")
    public Employee get(@PathVariable(value = "employeeId") int employeeId){
        return employeeService.get(employeeId);
    }

    //Ejercicio 3
    @GetMapping("/doctors/OFF")
    public List<Employee> listStatusOff(){
        return employeeService.getStatus(Status.OFF);
    }

    //Ejercicio 4
    @GetMapping("/doctors/department/{department}")
    public List<Employee> getDepartment(@PathVariable(value = "department") String department){
        return employeeService.getDepartment(department);
    }

    @PostMapping("/newDoctor")
    public Employee create(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @PutMapping("/updateDoctorStatus/{employeeId}")
    public Employee updateStatus(@PathVariable int employeeId, @RequestBody Employee employee){
        return employeeService.update(employeeId, employee);
    }

    @PutMapping("/updateDoctorDepartment/{employeeId}")
    public Employee updateDepartment(@PathVariable int employeeId, @RequestBody Employee employee){
        return employeeService.update(employeeId, employee);
    }

}
