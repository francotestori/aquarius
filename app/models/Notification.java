package models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import play.db.ebean.Model;

import java.util.Date;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Notification extends Model {
    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String message;
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private boolean read;

    static final Finder<Long, Notification> finder = new Finder<>(Long.class, Notification.class);

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

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public static Finder<Long, Notification> find(){
        return finder;
    }

}
