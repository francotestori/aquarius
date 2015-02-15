package models;

import play.db.ebean.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Comment extends Model {
    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String comment;
    Date date;
    @ManyToOne
    User user;
    @ManyToOne
    Project project;

    //Non-constructor variables
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ANSWER", inverseJoinColumns =  {
        @JoinColumn(name = "ANSWER_ID")
    }
    )
    List<Comment> answers;

    public long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public List<Comment> getAnswers() {
        return answers;
    }
}
