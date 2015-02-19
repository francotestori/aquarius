package controllers;

import models.Country;
import models.User;

import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.user.profile;
import views.html.user.profileForm;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;


public class Users extends Controller {

    public static Result showProfileForm() {
        Form<User> form = Form.form(User.class);
        final String email = session("email");
        final User user = User.findByEmail(email);

        return ok(profileForm.render(user, form));
    }

    public static Result updateProfile() {
        final String email = session().get("email");
        final User user = User.findByEmail(email);
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
}
