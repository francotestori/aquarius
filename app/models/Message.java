package models;

import play.db.ebean.Model;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.*;


@Entity
public class Message extends Model {
    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String subject;
    private String message;
    private Date date;
    @ManyToOne
    private User sender;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MESSAGE_USER", inverseJoinColumns={@JoinColumn(name = "RECIPIENT_ID")})
    private List<User> recipients;
    private boolean read;
    private static Finder<Long, Message> find = new Finder<>(Long.class, Message.class);

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

    public List<User> getRecipient() {
        return recipients;
    }

    public boolean isRead() {
        return read;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setRecipient(List<User> recipients) {
        this.recipients = recipients;
    }

    public void addRecipient(User recipient){recipients.add(recipient);}

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public String validate() {
        for(User recipient : recipients) {
            if (recipient == null) {
                return "user does not exist";
            }
        }
        return null;
    }

    @Nullable
    public static Message find(Long id){
        return find.byId(id);
    }

    @Nullable
    public static List<Message> findUserInbox(Long userId){
        return find.where().like("recipients.id","" + userId).findList();
    }
}
