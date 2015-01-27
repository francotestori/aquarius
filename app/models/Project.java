package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project extends Model {

    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    String description;
    String faq;
    long start;
    long end;
    int objective;
    String html;

    @ManyToOne
    Country country;

    @ManyToOne
    Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToMany
    List<Tag> tags;

    //Non-constructor variables
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    List<Update> updates;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFaq() {
        return faq;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public int getObjective() {
        return objective;
    }

    public String getHtml() {
        return html;
    }

    public Country getCountry() {
        return country;
    }

    public Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "PROJECT_FOLLOWERS", inverseJoinColumns = {@JoinColumn(name = "FOLLOWER_ID")})
    List<User> followers;

//    @OneToMany(cascade = CascadeType.ALL)(cascade = CascadeType.ALL)(cascade = CascadeType.ALL)(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    List<Image> images;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
    List<Fund> funds;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setObjective(int objective) {
        this.objective = objective;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFundsRaised() {
        int total = 0;
        for (Fund fund : funds) {
            total = total + fund.getAmount();
        }
        return total;
    }

    public int getObjectiveCompletion() {
        return 100 * getFundsRaised() / objective;
    }

    //TODO replantear los metodos de getDaysRemaining y getTimeCompletion
    public int getDaysRemaining() {
        if (end > System.currentTimeMillis()) {
            return (int) (end - System.currentTimeMillis()) / (24 * 3600000);
        } else {
            return 0;
        }

    }

    public int getTimeCompletion() {
        if (end > System.currentTimeMillis()) {
            final long dummy = 100 * (end - System.currentTimeMillis()) / (end - start);
            return (int) (100 - dummy);
        } else {
            return 100;
        }
    }

    public int getFollowersQty() {
        return followers.size();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void deleteAllTags() {
        tags = new ArrayList<>();
    }

    public void addFollower(User user) {
        followers.add(user);
    }

    public boolean isFollowedBy(User user) {
        return followers.contains(user);
    }

    public boolean isFollowedBy(String user) {
        for (User follower : followers) {
            if (follower.getUserName().equals(user)) return true;
        }
        return false;
    }

    public void addFund(Fund fund) {
        funds.add(fund);
    }

}