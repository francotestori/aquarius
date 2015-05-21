package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User extends Model implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String firstName;
    String lastName;
    Date birthday;
    @Constraints.Required
    @Column(unique = true)
    String email;
    @Constraints.Required
    String password;
    long reputation;
    @OneToOne(cascade = CascadeType.ALL)
    Image profilePicture;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    List<Project> projects;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_FOLLOWERS", inverseJoinColumns = {@JoinColumn(name = "FOLLOWER_ID")})
    List<User> followers;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Notification> notifications;
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    List<Message> inbox;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Fund> funds;
    boolean confirmedEmail;
    @ManyToOne
    Country country;

    public boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    private static Finder<Long, User> find = new Finder<>(Long.class, User.class);

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public List<Project> getProjects() {
        return projects;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<Message> getInbox() {
        return inbox;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public int getUnreadNotificationsQty() {
        return getUnreadNotifications().size();
    }

    public List<Notification> getUnreadNotifications() {
        ArrayList<Notification> unread = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.isRead()) unread.add(notification);
        }
        return unread;
    }

    public int getUnreadMessagesQty() {
        return getUnreadMessages().size();
    }

    public List<Message> getUnreadMessages() {
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

    public void addFollower(User user) {
        followers.add(user);
    }

    public static User authenticate(String email, String password) {
        User user = find.where().eq("email", email).findUnique();
        if (user == null) return null;
        else if (user.getPassword().equals(password)) return user;
        else return null;
    }

    /**
     * If has first name & last name returns "FirstName LastName" else returns email
     */
    public String getUsername(){
        if(firstName != null && lastName != null) return firstName + " " + lastName;
        else return email;
    }

    public static List<User> list() {
        return find.all();
    }

    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static User find(long id) {
        return find.byId(id);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Message getMessage(Long messageId){
        Message message = null;
        for (Message auxMessage : inbox) {
            if (messageId.equals(auxMessage.getId())) message = auxMessage;
        }
        return message;
    }

    /**
     * Finder for advanced queries in controllers
     */
    public static Finder<Long, User> find() {
        return find;
    }
}