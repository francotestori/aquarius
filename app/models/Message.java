package models;

public class Message {

    //Constructor variables
    private long id;
    private String message;
    private long date;
    private User sender;
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

}
