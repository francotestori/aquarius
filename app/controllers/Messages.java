package controllers;

import models.Message;
import models.User;

import play.data.Form;

import play.mvc.Result;

import views.html.message.messageForm;

import java.util.Date;


public class Messages extends AbstractController {
    public static Result showMessageForm() {
        final User user = getSessionUser();
        final Form<Message> form = Form.form(Message.class);

        return ok(messageForm.render(user, form));
    }

    public static Result postMessageForm() {
        final Form<Message> form = Form.form(Message.class).bindFromRequest();
        final User user = getSessionUser();

        if (form.hasGlobalErrors()) {
            return badRequest(messageForm.render(user, form));
        }

        final Message message = form.get();

        message.setSender(user);
        message.setRead(false);
        message.setDate(new Date(System.currentTimeMillis()));
        message.save();

        //TODO
        return ok();
    }

    public static Result showInbox() {
        return ok();
    }
}
