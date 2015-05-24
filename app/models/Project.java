package models;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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
    Date start;
    Date end;
    int objective;
    String html;
    Boolean active;

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

    static Finder<Long, Project> find = new Finder<>(Long.class, Project.class);

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

    //    @OneToMany(cascade = CascadeType.ALL)(cascade = CascadeType.ALL)(cascade = CascadeType.ALL)(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectView")
    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
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

    public void setObjective(int objective) {
        this.objective = objective;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getDaysRemaining() {
        Period period = new Period(this.start.getTime(), this.end.getTime(), PeriodType.days());
        return period.getDays();
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

    public int getFollowersQty() {
        return followers.size();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
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

    public void addFund(Fund fund) {
        funds.add(fund);
    }

    public void addComment(@NotNull Comment comment) {
        comments.add(comment);
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Finder for advanced queries on controllers
     */
    public static Finder<Long, Project> find() {
        return find;
    }

    public static Project find(long id) {
        return find.byId(id);
    }

    public static List<Project> findProjectsByUserID(long userID) {
        return find.where().like("user.id", "" + userID).findList();
    }

}