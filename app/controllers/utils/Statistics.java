package controllers.utils;

import models.Project;


public class Statistics {
    int newToday; //How many projects were added today

    public int calcNewToday() {
        Project.find().query();

        return 0;
    }
}
