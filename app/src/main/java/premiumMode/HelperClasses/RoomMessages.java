package premiumMode.HelperClasses;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class RoomMessages implements Serializable {
    String message;
    String sender;
    Timestamp timestamp;

    public RoomMessages(String message, String sender, Timestamp timestamp) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
