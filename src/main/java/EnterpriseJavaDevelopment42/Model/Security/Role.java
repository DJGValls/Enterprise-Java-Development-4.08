package EnterpriseJavaDevelopment42.Model.Security;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "FK_USER", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "FK_ROL")
    )

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<User> users;


    //metodo para añadir usuarios a cada rol
    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }

        this.users.add(user);
    }



    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
