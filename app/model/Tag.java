package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
