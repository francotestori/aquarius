package controllers;

import models.Message;
import models.User;

import play.data.Form;

import play.mvc.Result;

import views.html.message.messageForm;
import views.html.message.messageList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


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

        final String recipientEmail = message.getRecipient().getEmail();
        final User recipient = User.findByEmail(recipientEmail);
        message.setRecipient(recipient);
        message.save();

        return showInbox();
    }

    public static Result showInbox() {
        final User user = getSessionUser();
        final List<Message> inbox = user.getInbox();
        final Comparator<Message> cmp = new Comparator<Message>() {
                @Override
                public int compare(Message message, Message t1) {
                    return t1.getDate().compareTo(message.getDate());
                }

                @Override
                public boolean equals(Object o) {
                    return false;
                }
            };

        Collections.sort(inbox, cmp);

        return ok(messageList.render(user, inbox));
    }

    public static Result showMessage(Long id) {
        final User user = getSessionUser();
        final Message message = user.getMessage(id);

        if (message == null) {
            return showInbox();
        } else {
            if (!message.isRead()) {
                message.setRead(true);
                message.update();
            }

            return ok();
        }
    }
}
