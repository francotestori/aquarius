package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    public Type(String name) {
        this.name = name;
    }

    public Type() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
