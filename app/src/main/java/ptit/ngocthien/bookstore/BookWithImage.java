package ptit.ngocthien.bookstore;

import java.io.Serializable;

import Model.Book;

/**
 * Created by TrungNguyen on 3/22/2017.
 */

public class BookWithImage implements Serializable {
    private Book book;
    private int image;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
