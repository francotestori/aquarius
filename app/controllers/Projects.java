package controllers;

import models.*;
import org.joda.time.DateTime;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;
import utils.Function;
import utils.Tuple2;
import views.html.project.projectForm;
import views.html.project.projectList;
import views.html.project.projectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        final List<Tuple2<String, String>> userComment =
                stream(newComments).map(new Function<Comment, Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> execute(Comment comment) {
                        return Tuple2.apply(comment.getUser().getUsername(), comment.getComment());
                    }
                }).collect();

        return ok(Json.toJson(userComment));
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
        return ok("ajax call!");
    }

    public static Result showProjectForm() {
        Form<Project> form = Form.form(Project.class);
        final User user = getSessionUser();

        return ok(projectForm.render(user, form));
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
            return badRequest(projectForm.render(user, Form.form(Project.class)));
        } else {
            final String id = myProjectForm.data().get("id");
            final Project project;

            if((id != null) || !("".equals(id))) {
                project = Project.find(Long.valueOf(id));
                project.setName(myProjectForm.data().get("name"));
                project.setObjective(Integer.valueOf(myProjectForm.data().get("objective")));
                project.setDescription(myProjectForm.data().get("description"));
                project.setHtml(myProjectForm.data().get("html"));
            }
            else {
                project = myProjectForm.get();
                project.setStart(new Date(System.currentTimeMillis()));
            }

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
            project.setEnd(endDate);
            project.save();
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

    public static Result update(long id) {
        final Project project = Project.find(id);
        final User user = getSessionUser();

        Form<Project> form = Form.form(Project.class).fill(project);

        return ok(projectForm.render(user, form));
    }

    public static String parseDate(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
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

    public static List<Project> getFollowedProjects(User user) {
        return null;
//        return Project.find().where().eq("followers.id", user.getId()).findList();
    }

    public static List<Project> getUserProjects(User user) {
        return null;
//        return Project.find().where().eq("user.id",user.getId()).findList();
    }
}
