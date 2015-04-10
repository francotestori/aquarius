package controllers;

import models.User;

import play.mvc.Controller;
import play.mvc.Security;

import javax.validation.constraints.NotNull;


public abstract class AbstractController extends Controller {
    @NotNull
    @Security.Authenticated(Secured.class)
    public static User getSessionUser() {
        final String email = session().get("email");

        if (email != null) {
            return User.findByEmail(email);
        } else {
            return null; //Should never return null!
        }
    }
}
