package EnterpriseJavaDevelopment42.Utils;

import EnterpriseJavaDevelopment42.Model.Security.Role;
import EnterpriseJavaDevelopment42.Model.Security.User;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;


public class AddUsersUtil {

    public static void main(String[] args) {

        //users

        User userNurse = new User();
        userNurse.setUsername("nurse");

        User userDoctor = new User();
        userDoctor.setUsername("doctor");

        User userAdmin = new User();
        userAdmin.setUsername("admin");

        //roles

        Role roleDoctor = new Role();
        roleDoctor.setName("DOCTOR");
        roleDoctor.addUser(userNurse);
        roleDoctor.addUser(userDoctor);

        Role roleNurse = new Role();
        roleNurse.setName("NURSE");
        roleNurse.addUser(userNurse);

        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleAdmin.addUser(userAdmin);


        System.out.println("FIN");
    }


}
