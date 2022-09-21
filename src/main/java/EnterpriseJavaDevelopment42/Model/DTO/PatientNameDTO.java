package EnterpriseJavaDevelopment42.Model.DTO;

import com.sun.istack.NotNull;

import java.util.Date;

public class PatientNameDTO {

    @NotNull
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
