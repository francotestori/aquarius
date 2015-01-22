package models;

public class Update {
    private long id;
    private Project project;
    private long date;
    private String message;

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
