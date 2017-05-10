package ptit.ngocthien.bookstore.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import ptit.ngocthien.bookstore.Const.Const;

/**
 * Created by TrungNguyen on 3/22/2017.
 */

public class SendRequest extends StringRequest {

    public static final String url = "http://" + Const.IP + ":8080/Supermarket-war/webresources/service";

    String json;

    public SendRequest(int method, String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener, String json) {
        super(method, url, listener, errorListener);
        this.json = json;
    }

    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {
        return json.getBytes();
    }

    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }
}
