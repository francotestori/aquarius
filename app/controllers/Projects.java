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


public class Projects extends Navigation {

    public static Result showProjectForm() {
        Form<Project> form = Form.form(Project.class);
        final String email = session("email");
        final User user = User.findByEmail(email);

        return ok(projectForm.render(user,form));
    }

    public static Result createProject() {
        final String email = session().get("email");
        final User user = User.findByEmail(email);
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
        final String email = session().get("email");
        final User user = User.findByEmail(email);

        if (project != null) {
            return ok(projectView.render(project,user));
        } else {
            return Application.index();
        }
    }
}
