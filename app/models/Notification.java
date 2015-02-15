package models;

import play.db.ebean.Model;

import java.util.Date;

import javax.persistence.*;


@Entity
public class Notification extends Model {
    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String message;
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    //Non-constructor variables
    private boolean read;

    public Notification() {
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public boolean isRead() {
        return read;
    }
}
