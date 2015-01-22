package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String path;

    public Image() {
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
}
