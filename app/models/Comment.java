package models;

import org.joda.time.DateTime;
import play.db.ebean.Model;

import java.util.List;

import javax.persistence.*;


@Entity
public class Comment extends Model {
    //Constructor variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String comment;
    DateTime date;
    @ManyToOne
    User user;
    @ManyToOne
    Project project;

    static final Finder<Long, Comment> find = new Finder(Long.class, Comment.class);

    public long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public static Finder<Long, Comment> find() {
        return find;
    }

    public String toJson(){
        final StringBuilder strb = new StringBuilder();
        strb.append("{");
        strb.append("\"username\":\"").append(user.getUsername()).append("\"");
        strb.append(",");
        strb.append("\"date\":\"").append(DateTime.now()).append("\"");
        strb.append(",");
        strb.append("\"imgsrc\":\"").append(controllers.routes.Assets.at(user.getProfilePicture().getPath())).append("\"");
        strb.append(",");
        strb.append("\"href\":\"").append(controllers.routes.Users.showProfile(user.getId())).append("\"");
        strb.append(",");
        strb.append("\"comment\":\"").append(comment).append("\"");
        strb.append("}");
        return strb.toString();
    }

}
