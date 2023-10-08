package premiumMode.HelperClasses;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

public class Post {
    String name;
    String id;
    String email;
    Timestamp timestamp;
    String URL;
    String comment;

    String title;
    @Nullable String postLesson;
    @Nullable String postClass;
    @Nullable String postExam;

    public Post(String name, String id, String email, Timestamp timestamp, String URL, String title,String comment, @Nullable String postLesson, @Nullable String postClass, @Nullable String postExam) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.timestamp = timestamp;
        this.URL = URL;
        this.comment = comment;
        this.title = title;
        this.postLesson = postLesson;
        this.postClass = postClass;
        this.postExam = postExam;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Nullable
    public String getPostLesson() {
        return postLesson;
    }

    public void setPostLesson(@Nullable String postLesson) {
        this.postLesson = postLesson;
    }

    @Nullable
    public String getPostClass() {
        return postClass;
    }

    public void setPostClass(@Nullable String postClass) {
        this.postClass = postClass;
    }

    @Nullable
    public String getPostExam() {
        return postExam;
    }

    public void setPostExam(@Nullable String postExam) {
        this.postExam = postExam;
    }
}
