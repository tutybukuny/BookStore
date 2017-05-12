package ptit.ngocthien.bookstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ptit.ngocthien.bookstore.R;
import ptit.ngocthien.bookstore.model.SelectedProduct;

/**
 * Created by TrungNguyen on 5/12/2017.
 */

public class SelecProductAdapter extends RecyclerView.Adapter<SelecProductAdapter.SpHolder>{

    private Context mContext;
    private List<SelectedProduct> mList;
    private LayoutInflater mLayoutInflater;

    public SelecProductAdapter(Context mContext, List<SelectedProduct> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SelecProductAdapter.SpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_sp, parent, false);
        return new SpHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelecProductAdapter.SpHolder holder, int position) {
        SelectedProduct selectedProduct = mList.get(position);

        Picasso.with(mContext).load(selectedProduct.getProduct().getPreview().getImage())
                .into(holder.ivProduct);
        holder.tvSpName.setText(selectedProduct.getProduct().getName());
        holder.tvSpCost.setText(String.valueOf(selectedProduct.getProduct().getCost()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class SpHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_sp_video)
        ImageView ivProduct;
        @BindView(R.id.tv_sp_name)
        TextView tvSpName;
        @BindView(R.id.tv_sp_cost)
        TextView tvSpCost;

        public SpHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
