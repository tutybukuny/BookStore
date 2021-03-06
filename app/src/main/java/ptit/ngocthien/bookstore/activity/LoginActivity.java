package ptit.ngocthien.bookstore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import ptit.ngocthien.bookstore.Const.Const;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.Request.AppController;
import ptit.ngocthien.bookstore.Request.SendRequest;
import ptit.ngocthien.bookstore.model.Cart;
import ptit.ngocthien.bookstore.model.Human;
import ptit.ngocthien.bookstore.model.SelectedProduct;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private static LoginActivity mainActivity;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;

    private EditText _userNameText, _passwordText, _domainText;
    private Button _loginButton;
    private TextView tvLinkFB, _signupLink;

    private Response.Listener<String> success;
    private Response.ErrorListener error;
    private ProgressDialog progressDialog;

    public static int humanId;
    public static Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLoginFB();
        blindView();

        _loginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
        tvLinkFB.setOnClickListener(this);
    }

    private void setupLoginFB() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        mainActivity = this;
        initFaceBook();
        LoginManager.getInstance().registerCallback(callbackManager, loginResult);
        printKeyHash(this);
    }

    private void blindView() {
        _userNameText = (EditText) findViewById(R.id.input_username);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _domainText = (EditText) findViewById(R.id.input_domain);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        tvLinkFB = (TextView) findViewById(R.id.link_loginFB);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                login();
                break;
            }
            case R.id.link_signup: {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }

            case R.id.link_loginFB: {
                if (isLoggedInFaceBook()) {
                    LoginManager.getInstance().logOut();
                }
                loginFaceBook();
                break;
            }
        }
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        String username = _userNameText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();
        Const.DOMAIN = _domainText.getText().toString().trim();

        JSONObject jsonObjectSend = new JSONObject();
        try {
            jsonObjectSend.put("action", "login");
            JSONObject jsonAcc = new JSONObject();
            jsonAcc.put("username", username);
            jsonAcc.put("password", password);
            jsonObjectSend.putOpt("json", jsonAcc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setupRequest();

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
                , success, error, jsonObjectSend.toString());

        AppController.getInstance().addToRequestQueue(request);

    }

    private void setupRequest() {
        success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                doLogin(response);
                progressDialog.dismiss();
            }
        };
        error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
    }

    private void doLogin(String response) {
        try {
            JSONObject jsonReq = new JSONObject(response);
            String result = jsonReq.getString("result");
            if (result.equals("succes")) {
                Toasty.success(this, "Login success!", Toast.LENGTH_SHORT).show();
                humanId = jsonReq.getInt("humanId");
                Human human = new Human(humanId,"Customer");
                cart = new Cart();
                cart.setHuman(human);
                cart.setListSeleProducts(new ArrayList<SelectedProduct>());
                Log.e("humanId : " , humanId+"");
                Intent intent = new Intent(LoginActivity.this, ProductFeedActivity.class);
                startActivity(intent);
            } else {
                Toasty.warning(this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onLoginFailed() {
        Toasty.warning(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;
        String username = _userNameText.getText().toString();
        String password = _passwordText.getText().toString();
        String domain = _domainText.getText().toString();

        if (username.trim().length() == 0 || password.trim().length() == 0 || domain.length() == 0) {
            valid = false;
        } else {
            _userNameText.setError(null);
            _passwordText.setError(null);
            _domainText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            String packageName = context.getApplicationContext().getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public void initFaceBook() {
        loginResult = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Login thành công xử lý tại đây
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code
                                String name = object.optString(getResources().getString(R.string.name));
                                String id = object.optString(getString(R.string.id));
                                String email = object.optString(getString(R.string.email));
                                String link = object.optString(getString(R.string.link));
                                URL imageURL = extractFacebookIcon(id);

                                Log.d("name: ", name);
                                Log.d("id: ", id);
                                Log.d("email: ", email);
                                Log.d("link: ", link);
                                Log.d("imageURL: ", imageURL.toString());

                                Intent intent = new Intent(LoginActivity.this, ProductFeedActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("imageURL", imageURL.toString());
                                intent.setAction("loginFB");
                                startActivity(intent);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
            }
        };
    }

    //Lấy Avatar
    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

    //Login facebook with permisstion
    public void loginFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(mainActivity,
                Arrays.asList("public_profile", "user_friends", "email"));
    }

    //Hàm check login facebook
    public boolean isLoggedInFaceBook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
