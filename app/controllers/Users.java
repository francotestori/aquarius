package controllers;

import models.Country;
import models.Project;
import models.User;

import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.user.profile;
import views.html.user.profileForm;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Users extends Controller {

    public static Result showProfileForm() {
        Form<User> form = Form.form(User.class);
        final User user = getLoggedUser();
        return ok(profileForm.render(user, form));
    }

    public static Result updateProfile() {
        final User user = getLoggedUser();
        final Map<String, String> data = Form.form().bindFromRequest().data();
        Date birthday;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        try {
            birthday = sdf.parse(data.get("birthday"));
        } catch (ParseException e) {
            birthday = null;
        }

        user.setFirstName(data.get("firstName"));
        user.setLastName(data.get("lastName"));
        user.setBirthday(birthday);

        final Country country = Country.find(Long.parseLong(data.get("country")));
        user.setCountry(country);
        user.update();

        return redirect("/");
    }

    public static Result showProfile(long id) {
        final User user = User.find(id);

        if (user != null) {
            return ok(profile.render(user));
        } else {
            return Application.index();
        }
    }

    public static User getLoggedUser(){
        return User.findByEmail(session().get("email"));
    }

    public static List<Project> getTopProjectsFollow(User user){
        List<Project> topFollow = Projects.getFollowedProjects(user);
        if(topFollow.isEmpty()) return null;
        topFollow.sort(new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                return o1.getFundsRaised() - o2.getFundsRaised();
            }
        });
        return topFollow.subList(0,3);
    }

    public static List<User> getFollowedUsers(User user){
        return User.find().where().eq("followers.id", user.getId()).findList();
    }
}
