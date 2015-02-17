package controllers;

import models.User;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import play.twirl.api.Html;

import views.html.nav;


public abstract class Navigation extends Controller {
    @Security.Authenticated(Secured.class)
    public static Result display(String title, Html style, Html scripts,
        Html content) {
        final String email = session().get("email");
        final User user = User.findByEmail(email);

        return ok(nav.render(title, user, content, style, scripts));
    }

    @Security.Authenticated(Secured.class)
    public static Result display(String title, Html content) {
        final String email = session().get("email");
        final User user = User.findByEmail(email);

        return ok(nav.render(title, user, content, null, null));
    }

    @Security.Authenticated(Secured.class)
    public static Result display(Html content) {
        final String email = session().get("email");
        final User user = User.findByEmail(email);

        return ok(nav.render("", user, content, null, null));
    }
}
