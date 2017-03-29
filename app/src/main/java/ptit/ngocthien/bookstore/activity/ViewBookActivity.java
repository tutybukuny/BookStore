package ptit.ngocthien.bookstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import ptit.ngocthien.bookstore.BookWithImage;
import ptit.ngocthien.bookstore.R;

public class ViewBookActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        toolbar = (Toolbar) findViewById(R.id.tb_view_book);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        BookWithImage b = (BookWithImage) bundle.getSerializable("book");
        Toast.makeText(this, b.getBook().getName(), Toast.LENGTH_SHORT).show();
    }
}
