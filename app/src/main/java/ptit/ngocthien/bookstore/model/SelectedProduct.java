package ptit.ngocthien.bookstore.model;

import java.io.Serializable;

/**
 * Created by TrungNguyen on 5/11/2017.
 */

public class SelectedProduct implements Serializable {
    private int id;
    private Product product;
    private Cart cart;

    public SelectedProduct() {
    }

    public SelectedProduct(int id, Product product, Cart cart) {
        this.id = id;
        this.product = product;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
