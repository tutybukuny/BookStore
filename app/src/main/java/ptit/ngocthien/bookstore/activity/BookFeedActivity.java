package ptit.ngocthien.bookstore.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Book;
import Model.Human;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import ptit.ngocthien.bookstore.BookWithImage;
import ptit.ngocthien.bookstore.Const.Const;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.Request.SendRequest;
import ptit.ngocthien.bookstore.adapter.BookAdapter;

public class BookFeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<BookWithImage> bookList;
    ArrayList<Book> books;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View navHeader;
    private TextView tvUsername,tvEmail;
    private CircleImageView ivAvatar;

    private Response.Listener<String> success;
    private Response.ErrorListener error;

    private Human human;
    private static final int REQUEST_LOGIN_FB = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_feed);

        blindView();
        prepareUI();

        setupRecylerView();
        setupRequest();

        Intent intent = getIntent();
        if (intent.getAction().equals("loginFB")){
            String name = intent.getStringExtra("name");
            String email = intent.getStringExtra("email");
            String imageURL = intent.getStringExtra("imageURL");

            tvUsername.setText(name);
            tvEmail.setText(email);
//            Picasso.with(this)
//                    .load(imageURL)
//                    .placeholder(R.drawable.user)
//                    .fit()
//                    .into(ivAvatar);
        }else if (intent.getAction().equals("loginAcc")){

        }

//        SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
//                , success, error, "getBooks", "noaction");
//        Volley.newRequestQueue(this).add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 3:
                Toast.makeText(this, "aaaaaaaa", Toast.LENGTH_SHORT).show();
                break;
            case RESULT_OK:
                break;
        }
    }

    private void setView() {
        View navHeaderView = navigationView.getHeaderView(0);
        TextView txtUsername = (TextView) navHeaderView.findViewById(R.id.tv_username_nav);
        TextView txtName = (TextView) navHeaderView.findViewById(R.id.tv_name);

        if (human == null) {
            txtUsername.setText("Name");
            txtName.setText("Address");
        } else {
            txtUsername.setText(human.getName().getLastName());
            txtName.setText(human.getAddress().getStreet());
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
        bookList = new ArrayList<>();
        adapter = new BookAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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
                Toasty.error(BookFeedActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getListBook(String response) {
        TypeToken<ArrayList<Book>> token = new TypeToken<ArrayList<Book>>() {
        };
        books = new Gson().fromJson(response, token.getType());
        int i = 0;
        for (Book book : books) {
            BookWithImage bImage = new BookWithImage();
            bImage.setBook(book);
            bImage.setImage(R.drawable.sach);
            i++;
            bookList.add(bImage);
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
            case R.id.book:
                break;
        }
        return true;
    }
}
