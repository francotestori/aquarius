package model;

import javax.persistence.*;


@Entity
public class Notification {

    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String message;

    private long date;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    //Non-constructor variables
    private boolean read;

    public Notification() {
    }

    public Notification(String message, long date, User user) {
        this.message = message;
        this.date = date;
        this.user = user;

        //Initialize
        read = false;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public boolean isRead() {
        return read;
    }
}
