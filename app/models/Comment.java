package models;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Comment {

    //Constructor variables
    long id;
    String comment;
    long date;
    User user;
    Project project;

    //Non-constructor variables
    Collection<Comment> answers;

    public long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public long getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public Collection<Comment> getAnswers() {
        return answers;
    }
}
