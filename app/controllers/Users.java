package controllers;

import play.mvc.Result;

import play.twirl.api.Html;

import views.html.user.profile;


public class Users extends Navigation {
    public static Result showProfile() {
        final Html profileHtml = profile.apply();

        return display("Profile", profileHtml);
    }
}
