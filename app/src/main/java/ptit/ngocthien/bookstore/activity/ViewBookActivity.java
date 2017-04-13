package ptit.ngocthien.bookstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ptit.ngocthien.bookstore.BookWithImage;
import ptit.ngocthien.bookstore.R;

public class ViewBookActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivItemBook;
    TextView tvType, tvBookName, tvCost, tvNxb, tvAuthor;
    BookWithImage b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        blindView();

        toolbar = (Toolbar) findViewById(R.id.tb_view_book);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        b = (BookWithImage) bundle.getSerializable("book");
        setView();
    }

    private void setView() {
//        Picasso.with(this).load(b.getImage()).into(ivItemBook);
        Picasso.with(this).load(R.drawable.sach).into(ivItemBook);
        tvBookName.setText(b.getBook().getName());
        tvType.setText(b.getBook().getBookType().getName());
        tvCost.setText(String.valueOf(b.getBook().getCost()));
        tvAuthor.setText(b.getBook().getAuthor().getName());
        tvNxb.setText(b.getBook().getPublisher().getName());
    }

    private void blindView() {
        ivItemBook = (ImageView) findViewById(R.id.iv_item_book);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvAuthor = (TextView) findViewById(R.id.tv_author);
        tvBookName = (TextView) findViewById(R.id.tv_book_name);
        tvCost = (TextView) findViewById(R.id.tv_cost);
        tvNxb = (TextView) findViewById(R.id.tv_nxb);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }
}
