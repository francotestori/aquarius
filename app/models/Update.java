package models;

import play.db.ebean.Model;

import java.util.Date;

import javax.persistence.*;


@Entity
public class Update extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Project project;
    private Date date;
    private String message;

    public Update() {
    }

    public long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getMessage() {
        return message;
    }
}
