package ptit.ngocthien.bookstore.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.Request.AppController;
import ptit.ngocthien.bookstore.Request.SendRequest;
import ptit.ngocthien.bookstore.adapter.SelecProductAdapter;
import ptit.ngocthien.bookstore.helper.JsonHelper;

import static ptit.ngocthien.bookstore.activity.LoginActivity.cart;

public class ViewCartActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_view_cart)
    Toolbar toolbar;
    @BindView(R.id.rv_list_sp)
    RecyclerView rvListSp;
    @BindView(R.id.fab_oder)
    FloatingActionButton fabOder;

    SelecProductAdapter adapter;
    private Response.Listener<String> success;
    private Response.ErrorListener error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupRv();
        fabOder.setOnClickListener(this);
    }

    private void setupRv() {
        adapter = new SelecProductAdapter(this, cart.getListSeleProducts());
        rvListSp.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListSp.setLayoutManager(linearLayoutManager);
        rvListSp.setHasFixedSize(true);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_oder:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Bạn có muốn đặt hàng không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (cart.getListSeleProducts().size() > 0) {
                            setupRequest();
                            JsonHelper jsonHelper = new JsonHelper();
                            SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
                                    , success, error, jsonHelper.sendCard());
                            Log.e("json add to card : ", jsonHelper.sendCard());
                            AppController.getInstance().addToRequestQueue(request);
                        }
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Để sau", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

    private void setupRequest() {
        success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toasty.success(ViewCartActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
            }
        };

        error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(ViewCartActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
