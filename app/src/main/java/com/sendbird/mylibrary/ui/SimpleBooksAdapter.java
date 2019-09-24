package com.sendbird.mylibrary.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.data.Book;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleBooksAdapter extends RecyclerView.Adapter<SimpleBooksViewHolder> {

    private List<Book> mBooks;
    private BookItemListener mItemListener;

    public SimpleBooksAdapter(List<Book> books, BookItemListener itemListener) {
        setList(books);
        mItemListener = itemListener;
    }

    public void replaceData(List<Book> books) {
        setList(books);
        notifyDataSetChanged();
    }

    private void setList(List<Book> books) {
        mBooks = checkNotNull(books);
    }

    @NonNull
    @Override
    public SimpleBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_books_holder, parent, false);
        SimpleBooksViewHolder vh = new SimpleBooksViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleBooksViewHolder holder, final int position) {
        holder.setImage(mBooks.get(position).getImage());
        holder.setTitle(mBooks.get(position).getTitle());
        holder.setSubtitle(mBooks.get(position).getSubtitle());
        holder.setPrice(mBooks.get(position).getPrice());
        holder.setUrl(mBooks.get(position).getUrl());
        holder.setIsbn13(mBooks.get(position).getId());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(mBooks.get(position).getId());
                mItemListener.onBookClick(mBooks.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
