package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import static play.data.Form.form;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result login() {
        return ok(login.render(form(Login.class)));
    }

    public static class Login {
        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(controllers.routes.Application.index());
        }
    }

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
}
