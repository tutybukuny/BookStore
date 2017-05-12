package ptit.ngocthien.bookstore.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ptit.ngocthien.bookstore.model.Manufacturer;
import ptit.ngocthien.bookstore.model.Preview;
import ptit.ngocthien.bookstore.model.Product;
import ptit.ngocthien.bookstore.model.SelectedProduct;

import static ptit.ngocthien.bookstore.activity.LoginActivity.cart;

/**
 * Created by TrungNguyen on 5/12/2017.
 */

public class JsonHelper implements JsonParse{

    @Override
    public Product parseProduct(JSONObject jsProduct) {
        Product product = null;
        try {
            int id = jsProduct.getInt("id");
            String name = jsProduct.getString("name");
            String des = jsProduct.getString("description");
            float cost = (float) jsProduct.getDouble("cost");

            JSONObject jsPreview = jsProduct.getJSONObject("preview");
            int idPreview = jsPreview.getInt("id");
            String intro = jsPreview.getString("intro");
            String image = jsPreview.getString("image");
            Preview preview = new Preview(idPreview, intro, image);

            JSONObject jsManu = jsProduct.getJSONObject("manufacturer");
            int idManu = jsManu.getInt("id");
            String nameManu = jsManu.getString("name");
            String desManu = jsManu.getString("des");
            Manufacturer manufacturer = new Manufacturer(idManu, nameManu, desManu);

            product = new Product(id, name, des, cost, preview, manufacturer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public String sendCard() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action", "sendCart");

            JSONObject objectCart = new JSONObject();

            JSONObject ojectHuman = new JSONObject();
            ojectHuman.put("humanId",cart.getHuman().getId());
            ojectHuman.put("discri",cart.getHuman().getDiscriminator());
            objectCart.put("human",ojectHuman);

            JSONArray spArray = new JSONArray();
            float totalCost = 0;
            for (SelectedProduct sp : cart.getListSeleProducts()){
                totalCost = totalCost + sp.getProduct().getCost();

                JSONObject spOject = new JSONObject();
                spOject.put("productId",sp.getProduct().getId());
                spArray.put(spOject);
            }
            objectCart.put("selectproducts",spArray);
            objectCart.put("totalCost",totalCost);

            jsonObject.put("cart",objectCart);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
