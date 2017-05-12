package ptit.ngocthien.bookstore.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by TrungNguyen on 5/11/2017.
 */

public class Cart implements Serializable {
    private int id;
    private float totalCost;
    private Human human;
    private ArrayList<SelectedProduct> listSeleProducts;

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public ArrayList<SelectedProduct> getListSeleProducts() {
        return listSeleProducts;
    }

    public void setListSeleProducts(ArrayList<SelectedProduct> listSeleProducts) {
        this.listSeleProducts = listSeleProducts;
    }
}
