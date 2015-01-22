package model;

import javax.persistence.*;

@Entity
public class Update {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Project project;

    private long date;

    private String message;

    public Update(Project project, long date, String message) {
        this.project = project;
        this.date = date;
        this.message = message;
    }

    public Update() {
    }

    public long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public long getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
