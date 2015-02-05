package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import static play.data.Form.form;

/**
 * Created by nicolas on 22/01/15.
 */
public class Users extends Controller{

    public static Result showRegisterForm(){
        return ok(registerForm.render());
    }

    public static Result register() {
        Form<User> userForm = form(User.class).bindFromRequest();
        if (userForm.hasErrors()){
            return badRequest(registerForm.render());
        } else {
            User user = userForm.get();
            user.save();
            session().clear();
            session("email", user.getEmail());
            return redirect(controllers.routes.Application.index());
        }
    }

    public static Result showProfile(){return ok(profile.render());}
}
