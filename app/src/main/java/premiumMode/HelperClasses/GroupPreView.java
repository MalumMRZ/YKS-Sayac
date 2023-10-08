package premiumMode.HelperClasses;

import java.io.Serializable;

public class GroupPreView implements Serializable {

    String title;
    String key;
    String role;

    public GroupPreView(String title, String key, String role) {
        this.title = title;
        this.key = key;
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
