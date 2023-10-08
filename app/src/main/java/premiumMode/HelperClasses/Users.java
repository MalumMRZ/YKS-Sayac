package premiumMode.HelperClasses;

import java.io.Serializable;

public class Users implements Serializable {

    String name;
    String role;
    String uID;

    public Users(String name, String role, String uID) {
        this.name = name;
        this.role = role;
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
