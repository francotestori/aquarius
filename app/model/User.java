package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User implements Serializable {

    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String userName;
    String firstName;
    String lastName;
    long birthday;
    String email;
    String password;

    @OneToOne(cascade = CascadeType.ALL)
    Image profilePicture;

    public void setUserName(String userName) {this.userName = userName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setBirthday(long birthday){this.birthday = birthday;}

    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {this.password = password;}

    public void setProfilePicture(Image profilePicture) {this.profilePicture = profilePicture;}

    //Non-constructor variables
    long reputation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    Collection<Project> projects;

    @OneToMany
    @JoinTable(name = "USER_FOLLOWERS", inverseJoinColumns = {@JoinColumn(name = "FOLLOWER_ID")})
    Collection<User> followers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Collection<Notification> notifications;

    @OneToMany(mappedBy = "recipient")
    Collection<Message> inbox;

    @OneToMany(mappedBy = "user")
    Collection<Fund> funds;


    public User() {
        //Initialize
        notifications = new ArrayList<>();
        followers = new ArrayList<>();
        projects = new ArrayList<>();
        inbox = new ArrayList<>();
        funds = new ArrayList<>();
        reputation = 0;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String firstName, String lastName, long birthday, String userName, String email, String password, Image profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;

        //Initialize
        notifications = new ArrayList<>();
        followers = new ArrayList<>();
        projects = new ArrayList<>();
        inbox = new ArrayList<>();
        funds = new ArrayList<>();
        reputation = 0;

    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public long getReputation() {
        return reputation;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public Collection<User> getFollowers() {
        return followers;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public Collection<Message> getInbox() {
        return inbox;
    }

    public Collection<Fund> getFunds() {
        return funds;
    }

    public int getUnreadNotificationsQty() {
        return getUnreadNotifications().size();
    }

    public Collection<Notification> getUnreadNotifications() {
        ArrayList<Notification> unread = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.isRead()) unread.add(notification);
        }
        return unread;
    }

    public int getUnreadMessagesQty() {
        return getUnreadMessages().size();
    }

    public Collection<Message> getUnreadMessages() {
        ArrayList<Message> unread = new ArrayList<>();
        for (Message message : inbox) {
            if (!message.isRead()) unread.add(message);
        }
        return unread;
    }

    public void addFund(Fund fund) {
        funds.add(fund);
    }

    public int getFundsRaised() {
        int total = 0;
        for (Fund fund : funds) {
            total = total + fund.getAmount();
        }
        return total;
    }


}
