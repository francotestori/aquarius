package models;

public class Notification {

    //Constructor variables
    private long id;
    private String message;
    private long date;
    private User user;

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

    public User getUser() {
        return user;
    }

    public boolean isRead() {
        return read;
    }
}
