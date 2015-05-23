package controllers;

import models.Country;
import models.Image;
import models.Project;
import models.User;
import play.data.Form;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.user.profile;
import views.html.user.profileForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Users extends AbstractController {

    public static Result showProfileForm() {
        final User user = getLoggedUser();
        final Form<User> form = Form.form(User.class).fill(user);
        return ok(profileForm.render(user, form));
    }

    public static Result updateProfile() {
        final User user = getLoggedUser();
        final RequestBody body = request().body();
        final FilePart picture = body.asMultipartFormData().getFile("profile-pic");
        final Map<String, String[]> data = body.asMultipartFormData().asFormUrlEncoded();

        //Profile pic
        if (picture != null) {
            final String fileName = user.getId() + "";
            final String contentType = picture.getContentType();
            final String suffix = contentType.substring(contentType.indexOf("/") + 1, contentType.length());
            final File inputFile = picture.getFile();
            final String rootDir = System.getProperty("user.dir");
            final File outputFile = new File(rootDir + "/public/img/user/profile_pics/" + fileName + "." + suffix);
            final FileInputStream inputStream;
            final FileOutputStream outputStream;
            try {
                if (!outputFile.createNewFile()) {
                    assert outputFile.delete();
                    assert outputFile.createNewFile();
                }
                inputStream = new FileInputStream(inputFile);
                outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) outputStream.write(buffer, 0, length);
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            final Image image = new Image("img/user/profile_pics/" + fileName + "." + suffix);
            image.save();
            user.setProfilePicture(image);
        }

        final String birthdayStr = data.get("birthday")[0];
        final String firstName = data.get("firstName")[0];
        final String lastName = data.get("lastName")[0];
        final String countryName = data.get("country")[0];

        Date birthday;
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        try {
            birthday = sdf.parse(birthdayStr);
        } catch (ParseException e) {
            birthday = null;
        }
        user.setFirstName(firstName.equals("") ? null : firstName);
        user.setLastName(lastName.equals("") ? null : lastName);
        user.setBirthday(birthday);
        if (!countryName.equals("")) {
            final Country country = Country.find(Long.parseLong(countryName));
            user.setCountry(country);
        }
        user.update();
        return redirect("/");
    }

    public static Result showProfile(long id) {
        final User user = User.find(id);
        final User loggedUser = getLoggedUser();
        if (user != null) {
            return ok(profile.render(user));
        } else {
            return Application.index();
        }
    }

    public static User getLoggedUser() {
        return User.findByEmail(session().get("email"));
    }

    public static List<Project> getTopProjectsFollow(User user) {
//        return user.getFollowedProjects();
//        final List<Project> topFollow = Projects.getFollowedProjects(user);
//        if(topFollow.isEmpty()) return null;
//        topFollow.sort(new Comparator<Project>() {
//            @Override
//            public int compare(Project o1, Project o2) {
//                return o1.getFundsRaised() - o2.getFundsRaised();
//            }
//        });
//        return topFollow.subList(0,3);
        return null;
    }

    public static List<User> getFollowedUsers(User user) {
        return User.find().where().eq("followers.id", user.getId()).findList();
    }
}
