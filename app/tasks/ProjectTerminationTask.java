package tasks;

import models.Project;
import org.joda.time.DateTime;

import java.util.List;

public class ProjectTerminationTask implements Runnable {
    @Override
    public void run() {
        final List<Project> projects = Project.find().where().eq("active", true).le("end", DateTime.now()).findList();
        for (Project project : projects) {
            project.setActive(false);
            project.save();
        }
    }
}
