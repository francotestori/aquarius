package controllers;

import models.Project;

import models.Tag;
import models.User;
import play.data.Form;
import play.mvc.Result;

import views.html.project.projectForm;
import views.html.project.projectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Projects extends AbstractController {

    public static Result showProjectForm() {
        Form<Project> form = Form.form(Project.class);
        final User user = getSessionUser();

        return ok(projectForm.render(user,form));
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

        if(myProjectForm.hasErrors()){
            return badRequest(projectForm.render(user,Form.form(Project.class)));
        } else{
            final Project project = myProjectForm.get();

            project.setUser(user);
            // Add Types TODO
            // Add tags
            if(myProjectForm.data().get("source-tags") != null){
                String[] strTags = myProjectForm.data().get("source-tags").split(",");
                for(String strTag : strTags){
                    Tag tag = Tag.getFinder().where().like("name",strTag).findUnique();
                    if(tag == null){
                        tag = new Tag(strTag);
                        tag.save();
                    }
                    project.addTag(tag);
                }
            }
            project.setStart(new Date(System.currentTimeMillis()));
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

    public static Result update(long id){
        final Project project = Project.find(id);
        final User user = getSessionUser();

        Form<Project> form = Form.form(Project.class).fill(project);

        return ok(projectForm.render(user,form));
    }

    public static String parseDate(Date date){
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    public static String getStringTag(List<Tag> tags){
        String strTag = "";
        for (Tag tag: tags){
            strTag += tag.getName() + ",";
        }
        return strTag;
    }

    public static String getName(Boolean isNew){
        return isNew ? "Edit" : "New";
    }

    public static List<Project> getFollowedProjects(User user){
        return  null;
//        return Project.find().where().eq("followers.id", user.getId()).findList();
    }

    public static List<Project> getUserProjects(User user){
        return null;
//        return Project.find().where().eq("user.id",user.getId()).findList();
    }
}
