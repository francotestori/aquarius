package models;

public class Fund {
    private long id;
    private int amount;
    private User user;
    private Project project;

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }
}
