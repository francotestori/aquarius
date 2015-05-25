package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String path;

    static private Finder<Long, Image> find = new Finder<>(Long.class, Image.class);

    public Image() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Image(String path) {

        this.path = path;
    }

    public static Finder<Long, Image> finder(){
        return find;
    }

    public static Image find(long id){
        return find.byId(id);
    }

    public static Image defaultImage(){
        return find.where().eq("path", "img/defaultuserpic.png").findUnique();
    }
}
