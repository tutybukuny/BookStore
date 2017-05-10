package ptit.ngocthien.bookstore.model;

/**
 * Created by TrungNguyen on 5/10/2017.
 */

public class Manufacturer {
    private int id;
    private String name;
    private String description;

    public Manufacturer() {
    }

    public Manufacturer(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}