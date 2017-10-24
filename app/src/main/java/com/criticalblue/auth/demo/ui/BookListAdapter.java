package com.criticalblue.auth.demo.ui;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.criticalblue.auth.demo.R;
import com.criticalblue.auth.demo.books.Book;
import com.squareup.picasso.Picasso;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    private static final String TAG = BookListAdapter.class.getSimpleName();

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View layout;
        private BookSelectionListener selectionListener;

        public ViewHolder(View v, BookSelectionListener selectionListener) {
            super(v);
            layout = v;
            imageView = (ImageView) v.findViewById(R.id.item_image);
        }

        public void resize(int spanCount) {

            int approximateWidth = 300;
            int approximateHeight = 400;

            DisplayMetrics displayMetrics = itemView.getContext().getResources().getDisplayMetrics();

            int screenWidth = displayMetrics.widthPixels;

            int width = screenWidth / spanCount;
            int height = (approximateHeight * width) / approximateWidth;

            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = width;
            params.height = height;
            itemView.setLayoutParams(params);
            itemView.invalidate();
        }
    }

    private List<Book> books;

    private BookSelectionListener selectionListener;

    private int spanCount;

    public BookListAdapter(BookSelectionListener bookSelectionListener, int spanCount) {
        this.books = Collections.emptyList();
        this.selectionListener = bookSelectionListener;
        this.spanCount = spanCount;
    }

    public void setBookList(List<Book> books) {
        this.books = books != null ? books : Collections.emptyList();
        notifyDataSetChanged();
    }

    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_book, parent, false);
        ViewHolder holder = new ViewHolder(view, selectionListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Book book = books.get(holder.getAdapterPosition());

        holder.resize(spanCount);

        holder.imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onSelection(book);
            }
        });

        String imageLink = book.getImageLink();
        if (imageLink == null) imageLink = "- - -";

        Picasso.with(holder.imageView.getContext())
                .load(imageLink)
                .error(R.drawable.unknown_book)
                .fit().centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void add(int position, Book book) {
        books.add(position, book);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        books.remove(position);
        notifyItemRemoved(position);
    }
}
