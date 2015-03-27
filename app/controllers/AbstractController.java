package controllers;

import models.User;

import play.mvc.Controller;

import javax.annotation.Nullable;


public abstract class AbstractController extends Controller {
    @Nullable
    public static User getSessionUser() {
        final String email = session().get("email");

        if (email != null) {
            return User.findByEmail(email);
        } else {
            return null;
        }
    }
}
