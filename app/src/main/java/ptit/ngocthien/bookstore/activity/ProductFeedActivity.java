package ptit.ngocthien.bookstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import ptit.ngocthien.bookstore.Const.Const;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.Request.AppController;
import ptit.ngocthien.bookstore.Request.SendRequest;
import ptit.ngocthien.bookstore.adapter.ProductAdapter;
import ptit.ngocthien.bookstore.event.OnItemClickListener;
import ptit.ngocthien.bookstore.event.RecyclerItemClickListener;
import ptit.ngocthien.bookstore.helper.JsonHelper;
import ptit.ngocthien.bookstore.model.Product;

public class ProductFeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View navHeader;
    private TextView tvUsername, tvEmail;
    private CircleImageView ivAvatar;

    private Response.Listener<String> success;
    private Response.ErrorListener error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_feed);

        blindView();
        prepareUI();

        setupRequest();
        setupRecylerView();
        try {
            JSONObject jsonObject = new JSONObject().put("action", "getAllProducts");
            SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
                    , success, error, jsonObject.toString());
            AppController.getInstance().addToRequestQueue(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void prepareUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createNavigationView();
    }

    private void createNavigationView() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void blindView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        navHeader = navigationView.getHeaderView(0);
        tvUsername = (TextView) navHeader.findViewById(R.id.tv_username_nav);
        tvEmail = (TextView) navHeader.findViewById(R.id.tv_name);
        ivAvatar = (CircleImageView) navHeader.findViewById(R.id.profile_image);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupRecylerView() {
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ProductFeedActivity.this, ViewProductActivity.class);
                Product product = productList.get(position);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        }));
    }

    private void setupRequest() {
        success = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getListBook(response);
            }
        };

        error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(ProductFeedActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getListBook(String response) {
        JSONObject jobj = null;
        response = response.replaceAll("localhost", Const.DOMAIN);
        try {
            jobj = new JSONObject(response);
            JSONArray jsonArray = jobj.getJSONArray("products");
            Log.e("size", jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length() + 1; i++) {
                JSONObject jsProduct = jsonArray.getJSONObject(i);

                JsonHelper jsonHelper = new JsonHelper();
                Product product = jsonHelper.parseProduct(jsProduct);
                productList.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()) item.setCheckable(false);
        else item.setCheckable(true);
        drawerLayout.closeDrawers();

        switch (item.getItemId()) {
            case R.id.profile_user:
                break;
            case R.id.menu_watch_cart:
                Intent intent = new Intent(this,ViewCartActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
