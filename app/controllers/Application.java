package controllers;

import models.User;

import play.data.Form;
import static play.data.Form.form;

import play.libs.Crypto;

import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

import play.mvc.*;

import views.html.*;


public class Application extends Controller {
    public static Result index() {
        if (session().get("email") == null) {
            return redirect(controllers.routes.Application.login());
        } else {
            return ok(index.render("Your new application is ready."));
        }
    }

    public static Result login() {
        return ok(login.render(form(Login.class)));
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

    public static Result register() {
        Form<User> form = form(User.class).bindFromRequest();

        return ok(registerForm.render(form));
    }

    public static Result createUser() {
        final Form<User> userForm = form(User.class).bindFromRequest();

        if (userForm.hasErrors()) {
            return badRequest(registerForm.render(form(User.class)));
        } else {
            final User user = userForm.get();

            if (User.findByEmail(user.getEmail()) != null) {
                userForm.reject("Email address is already registered");

                return badRequest(registerForm.render(userForm));
            }

            final String password = user.getPassword();
            final String encryptedPassword = Crypto.encryptAES(password);
            user.setPassword(encryptedPassword);
            user.setConfirmedEmail(false);
            user.save();

            Email email = new Email();
            email.setSubject("Aquarius Mail Verification");
            email.setFrom("noreply@aquarius.com");
            email.addTo(user.getEmail());
            email.setBodyText("verify mother fucker");
            MailerPlugin.send(email);

            return redirect(controllers.routes.Application.index());
        }
    }

    public static Result confirmEmail(String confirmationCode) {
        final String email = Crypto.decryptAES(confirmationCode);
        final User user = User.findByEmail(email);

        if (user != null) {
            user.setConfirmedEmail(true);
        }

        return login();
    }

    public static class Login {
        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, Crypto.encryptAES(password)) == null) {
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
}
