package controllers;

import models.Project;

import play.data.Form;
import static play.data.Form.form;

import play.mvc.Result;

import play.twirl.api.Html;

import views.html.project.newProject;
import views.html.project.projectView;


public class Projects extends Navigation {
    public static Result showProjectForm() {
        //        final Html content = newProject.apply();
        //
        //        return display(content);
        return ok();
    }

    public static Result createProject() {
        //        Form<Project> projectForm = form(Project.class).bindFromRequest();
        //
        //        if (projectForm.hasErrors()) {
        //            return badRequest(newProject.render());
        //        } else {
        //            Project project = projectForm.get();
        //            project.save();
        //            session().clear();
        //
        //            return redirect(controllers.routes.Application.index());
        //        }
        return ok();
    }

    public static Result showProject(long id) {
        //        final Project project = Project.find(id);
        //
        //        return ok(projectView.render(project));
        return ok();
    }
}
