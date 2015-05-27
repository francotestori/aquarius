package models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Fund extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public Fund() {

    }

    public Fund(int amount, User user, Project project) {
        this.amount = amount;
        this.user = user;
        this.project = project;
    }
}
