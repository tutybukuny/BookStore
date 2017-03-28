package ptit.ngocthien.bookstore.activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import ptit.ngocthien.bookstore.BookWithImage;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.Request.SendRequest;
import ptit.ngocthien.bookstore.adapter.BookAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import Model.Book;
import es.dmoral.toasty.Toasty;

public class BookFeedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<BookWithImage> bookList;
    ArrayList<Book> books;

    private Response.Listener<String> success;
    private Response.ErrorListener error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_feed);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        bookList = new ArrayList<>();
        adapter = new BookAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        setupRequest();
        SendRequest request = new SendRequest(Request.Method.POST, SendRequest.url
                , success, error, "getBooks", "noaction");
        Volley.newRequestQueue(this).add(request);


    }

    @Override
    protected void onResume() {
        super.onResume();
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
        TypeToken<ArrayList<Book>> token = new TypeToken<ArrayList<Book>>() {};
        books = new Gson().fromJson(response, token.getType());
        for (Book book : books){
            BookWithImage bImage = new BookWithImage();
            bImage.setBook(book);
            bImage.setImage(R.drawable.sach1);
            bookList.add(bImage);
        }
        adapter.notifyDataSetChanged();
        Log.e("size", books.size() + "");
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
