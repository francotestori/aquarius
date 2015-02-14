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

        return ok(nav.render(title, style, scripts, user, content));
    }

    @Security.Authenticated(Secured.class)
    public static Result display(String title, Html content) {
        final String email = session().get("email");
        final User user = User.findByEmail(email);

        return ok(nav.render(title, null, null, user, content));
    }

    @Security.Authenticated(Secured.class)
    public static Result display(Html content) {
        final String email = session().get("email");
        final User user = User.findByEmail(email);

        return ok(nav.render("Aquarius", null, null, user, content));
    }
}
