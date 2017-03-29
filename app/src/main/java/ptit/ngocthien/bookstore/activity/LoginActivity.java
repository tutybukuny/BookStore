package ptit.ngocthien.bookstore.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.Request.SendRequest;

import com.google.gson.Gson;

import Model.Account;
import Model.Human;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText _userName;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;

    private Response.Listener<String> success;
    private Response.ErrorListener error;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blindView();

        _loginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
    }

    private void blindView() {
        _userName = (EditText) findViewById(R.id.input_username);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupLink = (TextView) findViewById(R.id.link_signup);
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
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            }
        }
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        String username = _userName.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();
        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);

        setupRequest();

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
                , success, error, "login", new Gson().toJson(acc));
        Volley.newRequestQueue(this).add(request);

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
        if (response.contains("acc")) {
            Log.e("response: ", response);
            Toasty.success(this, "Login success!", Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            Human human = (Human) gson.fromJson(response, Human.class);
            Intent intent = new Intent(LoginActivity.this, BookFeedActivity.class);
            startActivity(intent);
        } else {
            Toasty.warning(this, "Sai mat khau!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginFailed() {
        Toasty.warning(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;
        String password = _passwordText.getText().toString();

        if (_userName.getText().toString().trim().length() == 0 || _passwordText.getText().toString().trim().length() == 0) {
            valid = false;
        } else {
            _userName.setError(null);
            _passwordText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}
