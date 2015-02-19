package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Tag extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    static Finder<Long, Tag> find = new Finder<Long, Tag>(Long.class, Tag.class);

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Finder<Long, Tag> getFinder() {
        return find;
    }

}
