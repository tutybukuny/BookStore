package ptit.ngocthien.bookstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ptit.ngocthien.bookstore.BookWithImage;
import ptit.ngocthien.bookstore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by TrungNguyen on 3/22/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private List<BookWithImage> bookList;

    public BookAdapter(Context mContext, List<BookWithImage> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookAdapter.BookViewHolder holder, int position) {
        BookWithImage book = bookList.get(position);
        holder.title.setText(book.getBook().getName());
        holder.count.setText(book.getBook().getCost()+"");
        Picasso.with(mContext).load(book.getImage()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        public TextView title, count;
        public ImageView thumbnail;

        public BookViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            count = (TextView) itemView.findViewById(R.id.count);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
