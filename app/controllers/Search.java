package controllers;

import models.Project;
import models.User;
import play.mvc.Result;
import views.html.search.searchResults;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

/**
 * Created by franco on 23/05/2015.
 */
public class Search extends AbstractController {

    public static Result search(){
        final User user = getSessionUser();
        final String criteria = form().bindFromRequest().data().get("criteria");

        List<Project> searchedProjects = searchProjects(criteria);
        List<User> searchedUsers = searchUsers(criteria);

        return ok(searchResults.render(user,searchedUsers,searchedProjects));
    }

    public static Result showResults(){
        return search();
    }

    private static List<Project> searchProjects(String criteria){
        List<Project> result = new ArrayList<>();

        List<Project> byName = Project.find().where().ilike("name","%" + criteria + "%").findList();
        List<Project> byTag = Project.find().where().ilike("tags.name", "%" + criteria + "%").findList();
        List<Project> byType = Project.find().where().ilike("type.name","%" + criteria + "%").findList();
        List<Project> byUserEmail = Project.find().where().ilike("user.email","%" + criteria + "%").findList();
        List<Project> byDescription = Project.find().where().ilike("description","%" + criteria + "%").findList();

        for(Project project : byName){
            if(!result.contains(project)) result.add(project);
        }

        for(Project project : byTag){
            if(!result.contains(project)) result.add(project);
        }

        for(Project project : byType){
            if(!result.contains(project)) result.add(project);
        }

        for(Project project : byUserEmail){
            if(!result.contains(project)) result.add(project);
        }

        for(Project project : byDescription){
            if(!result.contains(project)) result.add(project);
        }

        return result;

    }

    private static List<User> searchUsers(String criteria){
        List<User> result = new ArrayList<>();

        List<User> byFirstName = User.find().where().ilike("firstName","%" + criteria + "%").findList();
        List<User> byLastName = User.find().where().ilike("lastName","%" + criteria + "%").findList();
        List<User> byEmail = User.find().where().ilike("email","%" + criteria + "%").findList();

        for(User user : byFirstName){
            if(!result.contains(user)) result.add(user);
        }

        for(User user : byLastName){
            if(!result.contains(user)) result.add(user);
        }

        for(User user : byEmail){
            if(!result.contains(user)) result.add(user);
        }

        return result;
    }
}
