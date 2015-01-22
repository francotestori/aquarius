package model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Project {

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
    Collection<Tag> tags;

    //Non-constructor variables
    @OneToMany(mappedBy = "project")
    Collection<Update> updates;

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

    public Collection<Tag> getTags() {
        return tags;
    }

    public Collection<Update> getUpdates() {
        return updates;
    }

    public Collection<User> getFollowers() {
        return followers;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public Collection<Fund> getFunds() {
        return funds;
    }

    @ManyToMany
    @JoinTable(name = "PROJECT_FOLLOWERS", inverseJoinColumns = {@JoinColumn(name = "FOLLOWER_ID")})
    Collection<User> followers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    Collection<Comment> comments;

    @OneToMany
    Collection<Image> images;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    Collection<Fund> funds;

    public Project() {
        //Initialize
        updates = new ArrayList<>();
        followers = new ArrayList<>();
        comments = new ArrayList<>();
        images = new ArrayList<>();
        funds = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public Project(String name, String description, String faq, long start, long end, int objective, String html, Country country, Type type, User user) {
        this.name = name;
        this.description = description;
        this.faq = faq;
        this.start = start;
        this.end = end;
        this.objective = objective;
        this.html = html;
        this.country = country;
        this.type = type;
        this.user = user;

        //Initialize
        updates = new ArrayList<>();
        followers = new ArrayList<>();
        comments = new ArrayList<>();
        images = new ArrayList<>();
        funds = new ArrayList<>();
        tags = new ArrayList<>();

    }

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
            return (int) (end - System.currentTimeMillis()) / (24 * 3600000) + 1;
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

    public void addComment(String comment, User user, long time) {
        Comment com = new Comment(this, user, comment, time);
        comments.add(com);
    }

    public boolean isFollowedBy(User user) {
        return followers.contains(user);
    }

    public void addFund(Fund fund) {
        funds.add(fund);
    }

}