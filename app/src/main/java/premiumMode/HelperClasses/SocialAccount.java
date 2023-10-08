package premiumMode.HelperClasses;

import java.io.Serializable;




public class SocialAccount implements Serializable {

    int imageID;
    String link;
    String name;

    public SocialAccount(int imageID, String link, String name) {
        this.imageID = imageID;
        this.link = link;
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
