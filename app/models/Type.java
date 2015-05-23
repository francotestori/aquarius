package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
public class Type extends Model implements Serializable {

    private static Finder<Long, Type> find = new Finder<Long, Type>(Long.class,
            Type.class);

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

    public static List<Type> list() {
        return find.all();
    }

    public static Type find(long id) {
        return find.byId(id);
    }
    public static Type find(String type) {
        return find.where().like("name",type).findUnique();
    }
}
