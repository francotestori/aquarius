package utils;

import models.Project;
import models.User;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by franco on 26/05/2015.
 */
public class IndexRecomendator {

    @NotNull
    public static List<Project> getBestProjectsFollowed(User user){
        List<Project> list = filterByUser(Project.find().all(),user);
        sortByFollowerQty(list);
        if(list.size() >= 3) return list.subList(0,2);
        return list;
    }

    @NotNull
    public static List<Project> getBestProjectsCompleted(User user){
        List<Project> list = filterByUser(Project.find().all(),user);
        sortByCompletion(list);
        if(list.size() >= 3) return list.subList(0,2);
        return list;
    }

    @NotNull
    public static List<Project> getBestProjectsPledged(User user){
        List<Project> list = filterByUser(Project.find().all(),user);
        sortByFundsRaised(list);
        if(list.size() >= 3) return list.subList(0,2);
        return list;
    }

    private static List<Project> filterByUser(List<Project> list, User user){
        List<Project> result = new ArrayList<>();
        for(Project project : list){
            if(!project.getUser().getEmail().equals(user.getEmail())) result.add(project);
        }
        return result;
    }

    private static void sortByFollowerQty(List<Project> list){
        list.sort((o1, o2) -> o2.getFollowersQty() - o1.getFollowersQty());
    }

    private static void sortByCompletion(List<Project> list){
        list.sort((o1, o2) -> o2.getObjectiveCompletion() - o1.getObjectiveCompletion());
    }

    private static void sortByFundsRaised(List<Project> list){
        list.sort((o1, o2) -> o2.getFundsRaised() - o1.getFundsRaised());
    }
}
