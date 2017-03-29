package ptit.ngocthien.bookstore.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TrungNguyen on 3/22/2017.
 */

public class SendRequest extends StringRequest {
    public static final String url = "http://192.168.75.147:8084/KTTKServer/ServiceManagement";

    protected String action;
    protected String json;

    public SendRequest(int method, String url, Response.Listener<String> listener,
                       Response.ErrorListener errorListener, String action, String json) {
        super(method, url, listener, errorListener);
        this.action = action;
        this.json = json;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
//        params.put("Content-Type", "application/json; charset=utf-8");
        params.put("Content-Type", "text/html; charset=utf-8");
        params.put("action", action);
        params.put("json", json);

        return params;
    }
}
