package utils;

import models.Project;
import models.User;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProjectRecomendator {

    @NotNull
    public static List<Project> recommendMeSomeProjects(@NotNull User user) {
        //TODO implement!
        return dummies(16);
    }

    @NotNull
    private static List<Project> dummies(@NotNull int qty) {
        final ArrayList<Project> dummies = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            final Project project = new DummyProject();
            project.setName("Project " + i);
            project.setDescription("The quick, brown fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog. Junk MTV quiz graced by fox whelps. Bawds jog, flick quartz, vex nymphs. Waltz, bad nymph, for quick jigs vex! Fox nymphs grab quick-jived waltz. Brick quiz whangs jumpy veldt");
            project.setId((long) i);
            dummies.add(project);
        }
        return dummies;
    }

    private static class DummyProject extends Project {
        static final Random rndm = new Random(System.currentTimeMillis());

        @Override
        public int getFollowersQty() {
            return rndm.nextInt(10000);
        }

        @Override
        public int getDaysRemaining() {
            return rndm.nextInt(60);
        }

        @Override
        public int getFundsRaised() {
            return rndm.nextInt(10000);
        }
    }


}
