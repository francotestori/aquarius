package models;

import java.io.Serializable;

public class Type implements Serializable {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
