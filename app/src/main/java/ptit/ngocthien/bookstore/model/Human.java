package ptit.ngocthien.bookstore.model;

import java.io.Serializable;

/**
 * Created by TrungNguyen on 5/7/2017.
 */

public class Human implements Serializable{
    private Integer id;
    private String discriminator;

    public Human() {
    }

    public Human(Integer id) {
        this.id = id;
    }

    public Human(Integer id, String discriminator) {
        this.id = id;
        this.discriminator = discriminator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }
}
