package ptit.ngocthien.bookstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.model.Product;
import ptit.ngocthien.bookstore.model.SelectedProduct;

import static ptit.ngocthien.bookstore.activity.LoginActivity.cart;

public class ViewProductActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageView ivItemProduct, ivAddToCart;
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
        ivAddToCart.setOnClickListener(this);
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
        ivAddToCart = (ImageView) findViewById(R.id.iv_cart_add);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cart_add:
                SelectedProduct selectedProduct = new SelectedProduct();
                selectedProduct.setCart(cart);
                selectedProduct.setProduct(product);
                int kt = 0;
                for (SelectedProduct sp : cart.getListSeleProducts()) {
                    if (sp.getProduct().getId() == selectedProduct.getProduct().getId()) {
                        kt = 1;
                        break;
                    }
                }
                if (kt == 0) {
                    cart.getListSeleProducts().add(selectedProduct);
                    Toasty.success(this, "Đã Thêm Vào Giỏ", Toast.LENGTH_SHORT).show();
                }else {
                    Toasty.warning(this, "Đã có mặt hàng đấy trong giở", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
