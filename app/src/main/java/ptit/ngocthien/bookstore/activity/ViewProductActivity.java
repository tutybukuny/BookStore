package ptit.ngocthien.bookstore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.model.Product;

public class ViewProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView ivItemProduct;
    TextView tvType, tvProductName, tvCost, tvNsx;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        blindView();

        toolbar = (Toolbar) findViewById(R.id.tb_view_product);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        setView();
    }

    private void setView() {
        Picasso.with(this).load(product.getPreview().getImage()).into(ivItemProduct);
        tvProductName.setText(product.getName());
        tvCost.setText(String.valueOf(product.getCost()));
        tvNsx.setText(product.getManufacturer().getName());
    }

    private void blindView() {
        ivItemProduct = (ImageView) findViewById(R.id.iv_item_book);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvNsx = (TextView) findViewById(R.id.tv_nsx);
        tvProductName = (TextView) findViewById(R.id.tv_product_name);
        tvCost = (TextView) findViewById(R.id.tv_cost);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }
}
