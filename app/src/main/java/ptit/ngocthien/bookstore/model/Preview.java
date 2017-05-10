package ptit.ngocthien.bookstore.model;

/**
 * Created by TrungNguyen on 5/10/2017.
 */

public class Preview {
    private int id;
    private String intro;
    private String image;

    public Preview() {
    }

    public Preview(int id, String intro, String image) {
        this.id = id;
        this.intro = intro;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
