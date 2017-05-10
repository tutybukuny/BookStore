package ptit.ngocthien.bookstore.model;

/**
 * Created by TrungNguyen on 5/10/2017.
 */

public class Product {
    private int id;
    private String name;
    private String description;
    private float cost;
    private Preview preview;
    private Manufacturer manufacturer;

    public Product() {
    }

    public Product(int id, String name, String description, float cost, Preview preview, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.preview = preview;
        this.manufacturer = manufacturer;
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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}


