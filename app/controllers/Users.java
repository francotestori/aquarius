package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by nicolas on 22/01/15.
 */
public class Users extends Controller{

    public static Result showProfile(){return ok(profile.render());}

}
