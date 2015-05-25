package tasks;

import models.*;
import org.joda.time.DateTime;
import play.libs.Crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DbPopulatorTask implements Runnable {
    Image image;

    public void createDefaultUserProfPic(){
        image = new Image("img/defaultuserpic.png");
        image.save();
    }

    public void createUsers(){
        final User ns1 = new User();
        ns1.setEmail("nschejtman@hotmail.com");
        ns1.setFirstName("Nicolas");
        ns1.setLastName("Schejtman");
        ns1.setPassword(Crypto.encryptAES("nico0021"));
        ns1.setConfirmedEmail(true);
        ns1.setProfilePicture(image);
        ns1.save();

        final User ns2 = new User();
        ns2.setEmail("nschejtman93@gmail.com");
        ns2.setFirstName("Nicolas");
        ns2.setLastName("Schejtman");
        ns2.setPassword(Crypto.encryptAES("nico0021"));
        ns2.setConfirmedEmail(true);
        ns2.setProfilePicture(image);
        ns2.save();

        final User ft1 = new User();
        ft1.setEmail("franco.testori@hotmail.com");
        ft1.setFirstName("Franco");
        ft1.setLastName("Testori");
        ft1.setPassword(Crypto.encryptAES("1234"));
        ft1.setConfirmedEmail(true);
        ft1.setProfilePicture(image);
        ft1.save();

        final User ft2 = new User();
        ft2.setEmail("franco.testori@ing.austral.edu.ar");
        ft2.setFirstName("Franco");
        ft2.setLastName("Testori");
        ft2.setPassword(Crypto.encryptAES("1234"));
        ft2.setConfirmedEmail(true);
        ft2.setProfilePicture(image);
        ft2.save();
    }

    public void createProjects(){
        for(final User user : User.list()){
            final Project project = new Project();
            project.setUser(user);
            project.setName(user.getFirstName() + "'s project");
            project.setDescription("This is the very brief description");
            project.setStart(DateTime.now().toDate());
            project.setEnd(DateTime.now().plusDays(60).toDate());
            project.setHtml("<h1>Title</h1><p>Paragraph</p>");
            project.setActive(true);
            project.setObjective(100000);
            project.save();
            user.addProject(project);
            user.save();
        }
    }

    public void createCountries(){

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\countries.txt")))
        {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
               if(Country.find(sCurrentLine) == null) {
                   Country country = new Country(sCurrentLine);
                   country.save();
               }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createTypes(){

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\types.txt")))
        {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if(Type.find(sCurrentLine) == null) {
                    Type type = new Type(sCurrentLine);
                    type.save();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        createCountries();
        createTypes();
        createDefaultUserProfPic();
        createUsers();
        createProjects();
    }
}
