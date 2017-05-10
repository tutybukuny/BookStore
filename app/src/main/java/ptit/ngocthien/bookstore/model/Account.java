package ptit.ngocthien.bookstore.model;

/**
 * Created by TrungNguyen on 5/7/2017.
 */

import java.io.Serializable;


public class Account implements Serializable {
    private String username;
    private String password;
    private Integer id;
    private String discriminator;
    private Human humanID;

    public Account() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Human getHumanID() {
        return humanID;
    }

    public void setHumanID(Human humanID) {
        this.humanID = humanID;
    }
}