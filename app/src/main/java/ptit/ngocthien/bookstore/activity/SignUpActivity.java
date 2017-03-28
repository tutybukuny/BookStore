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

import Model.Address;
import Model.Birthday;
import Model.Human;
import Model.Name;
import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {
    EditText _nameText;
    EditText _addressText;
    EditText _emailText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    Button _signupButton;
    TextView _loginLink;
    ProgressDialog progressDialog;

    private Response.Listener<String> success;
    private Response.ErrorListener error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        blindView();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void blindView() {
        _nameText = (EditText) findViewById(R.id.input_name);
        _addressText = (EditText) findViewById(R.id.input_address);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        setupRequest();

        progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        Human human = new Human();
        Name n = new Name();
        n.setFirstName(name);
        n.setLastName("");
        n.setMiddleName("");
        Address add = new Address();
        add.setNumber("");
        add.setCity("");
        add.setCountry("");
        add.setStreet("");
        Birthday birthday = new Birthday();
        birthday.setDay(0);
        birthday.setMonth("");
        birthday.setYear(0);

        human.setAddress(add);
        human.setBirthday(birthday);
        human.setName(n);

        SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
                , success, error, "signup", new Gson().toJson(human));
        Volley.newRequestQueue(this).add(request);
    }

    private void setupRequest() {
        success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                doSignup(response);
                progressDialog.dismiss();
            }
        };
        error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
                Toasty.error(SignUpActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        };
    }

    private void doSignup(String response) {
        Log.e("response: ", response);
        if (!response.contains("error")) {
            Toasty.success(this, "Login success!", Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            Human human = (Human) gson.fromJson(response, Human.class);
        }else {
            Toasty.warning(this, "Khong dang ky duoc", Toast.LENGTH_SHORT).show();
        }
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
