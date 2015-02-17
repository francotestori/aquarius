package models;

import play.db.ebean.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Country extends Model {
    private static Finder<Long, Country> find = new Finder<Long, Country>(Long.class,
            Country.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Country> list() {
        return find.all();
    }

    public static Country find(long id) {
        return find.byId(id);
    }
}
