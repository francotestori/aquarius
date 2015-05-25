package utils;

import models.Project;
import models.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Random;

public class ProjectUtils {

    public static List<User> get15RandomFollowers(@NotNull Project project){
        final List<User> followers = project.getFollowers();
        final int size = followers.size();
        if (size < 15) return followers;
        else{
            followers.sort((o1, o2) -> {
                final Random random = new Random(System.currentTimeMillis());
                return random.nextInt(3) - 1;
            });
            return followers.subList(0, 14);
        }
    }
}
