package model;

import javax.persistence.*;


@Entity
public class Message {

    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String message;
    private long date;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    //Non-constructor variables
    private boolean read;

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
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

    public Message(User recipient, User sender, long date, String message) {
        this.recipient = recipient;
        this.sender = sender;
        this.date = date;
        this.message = message;

        //Initialize
        read = false;
    }

    public Message() {
    }
}
