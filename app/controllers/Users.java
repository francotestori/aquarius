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

    public static final Form<User> userForm = form(User.class);

    public static Result showNewUserForm(){
        return ok(newUserForm.render("yeah mother fucker!"));
    }

    public static Result createUser(){
        if(userForm.hasErrors()){
            return showNewUserForm();
        } else {
            User user = userForm.bindFromRequest().get();
            System.out.println(user.getEmail());

        }

        return Application.index();
    }
}
