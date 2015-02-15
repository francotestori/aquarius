package controllers.utils;

import models.Project;

import play.twirl.api.Html;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class IndexBuilder {
    private static Html popularProyects() {
        final int MAX = 1000;
        final List<Project> newest = Project.find().query().orderBy()
                                            .desc("start").setMaxRows(MAX)
                                            .findList();

        Comparator<Project> cmp = new Comparator<Project>() {
                @Override
                public int compare(Project p1, Project p2) {
                    final int q1 = p1.getFollowersQty();
                    final int q2 = p2.getFollowersQty();

                    if (q1 > q2) {
                        return -1; //Reverse for desc order
                    } else if (q1 == q2) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            };

        Collections.sort(newest, cmp);

        if (newest.size() > 3) {
            final List<Project> aux = newest.subList(0, 2);

            return views.html.indexViews.popularProjects.apply(aux);
        } else {
            return views.html.indexViews.popularProjects.apply(newest);
        }
    }
}
