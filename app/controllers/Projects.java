package controllers;

import models.Project;
import play.data.Form;
import play.mvc.*;
import play.mvc.Controller;
import views.html.newProject;

import static play.data.Form.form;

/**
 * Created by nicolas on 25/01/15.
 */
public class Projects extends Controller {

    public static Result showProjectForm(){ return ok(newProject.render());}

    public static Result createProject(){
        Form<Project> projectForm = form(Project.class).bindFromRequest();
        if(projectForm.hasErrors()){
            return badRequest(newProject.render());
        }
        else{
            Project project = projectForm.get();
            project.save();
            session().clear();
            return redirect(controllers.routes.Application.index());
        }
    }

}
