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

public class DetailBooksAdapter extends RecyclerView.Adapter<DetailBooksViewHolder> {

    private List<Book> mBooks;

    private BookItemListener mRemoveClickListener;

    private int mEditButtonVisibility = View.GONE;

    public DetailBooksAdapter(List<Book> books, BookItemListener itemListener) {
        setList(books);
        mRemoveClickListener = itemListener;
    }

    public DetailBooksAdapter(List<Book> books) {
        setList(books);
    }

    public void replaceData(List<Book> books) {
        setList(books);
        notifyDataSetChanged();
    }

    public void removeData(Book book) {
        mBooks.remove(book);
        setList(mBooks);
        notifyDataSetChanged();
    }

    public void setEditButtonVisibility (int visibility) {
        mEditButtonVisibility = visibility;
        notifyDataSetChanged();
    }

    private void setList(List<Book> books) {
        mBooks = checkNotNull(books);
    }

    @NonNull
    @Override
    public DetailBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_books_holder, parent, false);
        DetailBooksViewHolder vh = new DetailBooksViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailBooksViewHolder holder, final int position) {
        holder.setImage(mBooks.get(position).getImage());
        holder.setTitle(mBooks.get(position).getTitle());
        holder.setSubtitle(mBooks.get(position).getSubtitle());
        holder.setPrice(mBooks.get(position).getPrice());
        holder.setUrl(mBooks.get(position).getUrl());
        holder.setIsbn13(mBooks.get(position).getId());

        holder.setAuthors(mBooks.get(position).getAuthors());
        holder.setPublisher(mBooks.get(position).getPublisher());
        holder.setLanguage(mBooks.get(position).getLanguage());
        holder.setIsbn10(mBooks.get(position).getIsbn10());
        holder.setPages(mBooks.get(position).getPages());
        holder.setYear(mBooks.get(position).getYear());
        holder.setRating(mBooks.get(position).getRating());
        holder.setDesc(mBooks.get(position).getDesc());

        holder.setOnRemoveClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRemoveClickListener.onBookClick(mBooks.get(position));
            }
        });
        holder.setRemoveFabVisibility(mEditButtonVisibility);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
