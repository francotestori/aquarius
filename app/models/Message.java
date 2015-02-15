package models;

import play.db.ebean.Model;

import java.util.Date;

import javax.persistence.*;


@Entity
public class Message extends Model {
    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String message;
    private Date date;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;
    private boolean read;

    public Message() {
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public boolean isRead() {
        return read;
    }
}
