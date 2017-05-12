package ptit.ngocthien.bookstore.helper;

import org.json.JSONObject;

import ptit.ngocthien.bookstore.model.Product;

/**
 * Created by TrungNguyen on 5/12/2017.
 */

public interface JsonParse {
    Product parseProduct(JSONObject jsonProduct);
    String sendCard();
}
