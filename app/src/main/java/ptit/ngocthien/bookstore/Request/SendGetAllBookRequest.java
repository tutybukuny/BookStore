package ptit.ngocthien.bookstore.Request;

import com.android.volley.Response;

/**
 * Created by TrungNguyen on 3/22/2017.
 */

public class SendGetAllBookRequest extends SendRequest {

    public SendGetAllBookRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String action, String json) {
        super(method, url, listener, errorListener, action, json);
    }
}
