package controllers;

import models.Image;
import models.User;

import play.api.mvc.Call;

import play.data.DynamicForm;
import play.data.Form;
import static play.data.Form.form;

import play.libs.Crypto;

import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import play.twirl.api.Html;

import utils.AqMessage;
import views.html.index;
import views.html.login;
import views.html.nav;
import views.html.registerForm;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Application extends Controller {
    @Security.Authenticated(Secured.class)
    public static Result index() {
        final String email = session().get("email");

        if (email == null) {
            return redirect(controllers.routes.Application.login());
        } else {
            final Html html = index.apply("Your new application is ready");
            final User user = User.findByEmail(email);

            return ok(nav.render("Welcome!", user, null, null, html));
        }
    }

    public static Result login() {
        if (session().get("email") == null) {
            return ok(login.render(form(Login.class)));
        } else {
            return redirect("/");
        }
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);

            final Call index = Call.apply("GET", "/");

            return redirect(index);
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
            final Image image = new Image("img/defaultuserpic.png");
            image.save();
            user.setProfilePicture(image);
            user.save();
            sendEmailConf(user.getEmail());
            return redirect(controllers.routes.Application.afterRegister());
        }
    }

    /**
     * Sends and email to the user with a link for confirming his account
     * @param email email address to send mail
     */
    private static void sendEmailConf(String email) {
        final Email emailObj = new Email();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        emailObj.setSubject("Aquarius Mail Verification");
        emailObj.setFrom("noreply@aquarius.com");
        emailObj.addTo(email);

        final String link = (ip != null) ?
                ip + ":9000/confirm/" + Crypto.encryptAES(email) :
                "/confirm/" + Crypto.encryptAES(email);

        emailObj.setBodyHtml("<h3>Thank you for registering</h3>" +
                "<p>To activate your email please click on the follwing link</p>" +
                "<a href=\"" + link + "\" target=_blank>" + link + "</a>");
        MailerPlugin.send(emailObj);
    }

    /**
     * Given an email confirmation link, confirms the account and logs in the user
     * @param encryptedEmail
     * @return
     */
    public static Result confirmEmail(String encryptedEmail) {
        final String email = Crypto.decryptAES(encryptedEmail);
        final User user = User.findByEmail(email);
        if (user != null) {
            user.setConfirmedEmail(true);
            user.save();
            session().clear();
            session("email", email);
            final Call index = Call.apply("GET", "/");
            return redirect(index);
        } else {
            return login();
        }
    }

    public static Result resendConfirmEmail(){
        final DynamicForm dForm = form().bindFromRequest();
        final String email = dForm.get("email");
        if (email == null) {
            return login();
        } else {
            sendEmailConf(email);
            return login();
        }
    }

    public static Result afterRegister() {
        return ok(views.html.confirmEmail.render());
    }

    public static Result logout() {
        session().clear();

        return redirect("/login");
    }

    public static class Login {
        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, Crypto.encryptAES(password)) == null) {
                return "Invalid user or password";
            }

            final User user = User.findByEmail(email);

            if ((user != null) && !user.isConfirmedEmail()) {
                return AqMessage.EMAIL_CONF_REQ.en();
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
