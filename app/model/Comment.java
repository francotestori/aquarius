package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class Comment {

    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String comment;
    long date;

    @ManyToOne
    User user;

    @ManyToOne
    Project project;

    //Non-constructor variables
    @OneToMany
    @JoinTable(name = "ANSWER", inverseJoinColumns = {@JoinColumn(name = "ANSWER_ID")})
    Collection<Comment> answers;

    public Comment() {
    }

    public Comment(Project project, User user, String comment, long date) {
        this.project = project;
        this.user = user;
        this.comment = comment;
        this.date = date;
        //Initialize
        answers = new ArrayList<Comment>();
    }

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
