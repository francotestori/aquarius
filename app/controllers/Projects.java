package controllers;

import models.*;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;
import utils.Function;
import utils.Tuple2;
import views.html.project.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static play.data.Form.form;
import static utils.Stream.stream;


public class Projects extends AbstractController {

    public static Result updateComments() {
        final Map<String, String> data = form().bindFromRequest().data();
        final Long lup = Long.valueOf(data.get("lastUpdate"));
        final Long pid = Long.valueOf(data.get("projectId"));
        final List<Comment> newComments = Comment
                .find()
                .where()
                .eq("project_id", pid)
                .ge("date", new DateTime(lup))
                .orderBy("date")
                .findList();
        final StringBuilder strb = new StringBuilder();
        strb.append("{\"comments\":[");
        for (final Comment comment : newComments) {
            strb.append(comment.toJson()).append(",");
        }
        final int length = strb.length();
        if(strb.charAt(length - 1) == ',') strb.deleteCharAt(length-1);
        strb.append("]}");
        return ok(strb.toString());
    }

    public static Result comment() {
        final Map<String, String> data = form().bindFromRequest().data();
        final Comment comment = new Comment();
        final User user = User.find(Long.valueOf(data.get("userId")));
        final Project project = Project.find(Long.valueOf(data.get("projectId")));
        comment.setComment(data.get("comment"));
        comment.setProject(project);
        comment.setUser(user);
        comment.setDate(DateTime.now());
        comment.save();
        project.addComment(comment);
        project.update();

        final Notification notification = new Notification();
        notification.setUser(project.getUser());
        notification.setDate(DateTime.now().toDate());
        notification.setRead(false);
        final String ownerMessage = user.getUsername() +
                Languages.message(" has commented on ")
                + project.getName();
        notification.setMessage(Languages.message(ownerMessage));
        notification.save();

        return ok("{ " +
                "username : " + user.getUsername() + "," +
                "date : " + DateTime.now() + "," +
                "imgsrc : " + controllers.routes.Assets.at(user.getProfilePicture().getPath()) + "," +
                "href : " + controllers.routes.Users.showProfile(user.getId()) +
                "}");
    }

    public static Result showProjectForm() {
        Form<Project> form = Form.form(Project.class);
        final User user = getSessionUser();

        return ok(newProject.render(user, form));
    }

    public static Result createProject() {
        final User user = getSessionUser();
        final Form<Project> myProjectForm = Form.form(Project.class).bindFromRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date endDate;

        try {
            endDate = sdf.parse(myProjectForm.data().get("aux-end"));
        } catch (ParseException e) {
            endDate = null;
        }

        if (myProjectForm.hasErrors()) {
            return badRequest(newProject.render(user, Form.form(Project.class)));
        } else {
            final String id = myProjectForm.data().get("id");
            final Project project;

            project = myProjectForm.get();
            project.setStart(new Date(System.currentTimeMillis()));

            project.setUser(user);

            // Add Types TODO
            project.setType(Type.find(myProjectForm.data().get("type")));

            // Add tags
            if (myProjectForm.data().get("source-tags") != null) {
                String[] strTags = myProjectForm.data().get("source-tags").split(",");
                for (String strTag : strTags) {
                    Tag tag = Tag.getFinder().where().like("name", strTag).findUnique();
                    if (tag == null) {
                        tag = new Tag(strTag);
                        tag.save();
                    }
                    if(!project.getTags().contains(tag)) project.addTag(tag);
                }
            }
            project.setActive(false);
            project.setEnd(endDate);
            project.save();

            for (final User follower : user.getFollowers()) {
                final Notification notification = new Notification();
                notification.setUser(follower);
                notification.setDate(DateTime.now().toDate());
                notification.setRead(false);
                final String ownerMessage = user.getUsername() +
                        Languages.message(" has started a new project called ") +
                        project.getName();
                notification.setMessage(Languages.message(ownerMessage));
                notification.save();
            }

            return redirect("/project/" + project.getId());
        }
    }

    public static Result showProject(long id) {
        final Project project = Project.find(id);
        final User user = getSessionUser();

        if (project != null) {
            return ok(projectView.render(project, user));
        } else {
            return Application.index();
        }
    }

    public static Result showProjectList(){
        final User user = getSessionUser();
        final List<Project> projects = Project.findProjectsByUserID(user.getId());

        if (!projects.isEmpty()) {
            return ok(projectList.render(user, projects));
        } else {
            return Application.index();
        }
    }

    public static Result updateProject(){
        final Form<Project> myProjectForm = Form.form(Project.class).bindFromRequest();

        final String id = myProjectForm.data().get("id");
        final Project project;

        project = Project.find(Long.valueOf(id));
        project.setName(myProjectForm.data().get("name"));
        project.setObjective(Integer.valueOf(myProjectForm.data().get("objective")));
        project.setDescription(myProjectForm.data().get("description"));
        project.setHtml(myProjectForm.data().get("html"));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date endDate;

        try {
            endDate = sdf.parse(myProjectForm.data().get("aux-end"));
        } catch (ParseException e) {
            endDate = null;
        }

        // Add Types TODO
        project.setType(Type.find(myProjectForm.data().get("type")));

        // Add tags
        if (myProjectForm.data().get("source-tags") != null) {
            String[] strTags = myProjectForm.data().get("source-tags").split(",");
            for (String strTag : strTags) {
                Tag tag = Tag.getFinder().where().like("name", strTag).findUnique();
                if (tag == null) {
                    tag = new Tag(strTag);
                    tag.save();
                }
                if(!project.getTags().contains(tag)) project.addTag(tag);
            }
        }

        project.setEnd(endDate);
        project.update();
        return redirect("/project/" + project.getId());
    }

    public static Result showEditForm(long id) {
        final Project project = Project.find(id);
        final User user = getSessionUser();

        Form<Project> form = Form.form(Project.class).fill(project);

        return ok(editProject.render(user, form));
    }

    public static String parseDate(Date date) {
        return (date != null) ? new SimpleDateFormat("MM/dd/yyyy").format(date) : "";
    }

    public static String getStringTag(List<Tag> tags) {
        String strTag = "";
        for (Tag tag : tags) {
            strTag += tag.getName() + ",";
        }
        return strTag;
    }

    public static String getName(Boolean isNew) {
        return isNew ? "New" : "Edit";
    }

    public static Result addFollower(long id) {
        final Project project = Project.find(id);
        final User user = getSessionUser();
        project.addFollower(user);
        return ok(projectView.render(project, user));
    }

    public static Result followProject(){
        final User sessionUser = getSessionUser();
        final String id = form().bindFromRequest().get("id");
        final Project project = Project.find(Long.valueOf(id));

        final Notification notification = new Notification();
        notification.setUser(project.getUser());
        notification.setDate(DateTime.now().toDate());
        notification.setRead(false);
        final String ownerMessage = sessionUser.getUsername() +
                Languages.message(" is now following ") + project.getName();
        notification.setMessage(Languages.message(ownerMessage));
        notification.save();

        project.addFollower(sessionUser);
        project.save();
        return ok();
    }

    public static Result unfollowProject(){
        final User sessionUser = getSessionUser();
        final String id = form().bindFromRequest().get("id");
        final Project project = Project.find(Long.valueOf(id));
        project.getFollowers().remove(sessionUser);
        project.save();
        return ok();
    }

    public static Result addPledge(){
        final User sessionUser = getSessionUser();
        final DynamicForm form = form().bindFromRequest();
        final long id = Long.valueOf(form.get("id"));
        final Integer amount = Integer.valueOf(form.get("amount"));
        final Project project = Project.find(id);

        final Fund fund = new Fund(amount, sessionUser, project);
        fund.save();

        final Notification notification = new Notification();
        notification.setUser(project.getUser());
        notification.setDate(DateTime.now().toDate());
        notification.setRead(false);
        final String ownerMessage = sessionUser.getUsername() +
                Languages.message(" has contributed ") +
                "$" + amount + Languages.message(" to ")
                + project.getName();
        notification.setMessage(Languages.message(ownerMessage));
        notification.save();

        for (final User user : project.getFollowers()) {
            final Notification followerNotif = new Notification();
            followerNotif.setRead(false);
            followerNotif.setMessage(project.getName() + Languages.message(" received funds"));
            followerNotif.setDate(DateTime.now().toDate());
            followerNotif.setUser(user);
            followerNotif.save();
        }

        project.addFund(fund);
        project.save();
        return ok();
    }

    public static List<Project> getFollowedProjects(long userID) {
        List<Project> result = Project.find().where().like("followers.id", "" + userID).findList();

        result.sort((o1, o2) -> o2.getFundsRaised() - o1.getFundsRaised());

        if(result.size() >= 3) return result.subList(0,2);
        else return result.isEmpty() ? new ArrayList<>() :  result.subList(0,result.size() - 1);
    }
}
