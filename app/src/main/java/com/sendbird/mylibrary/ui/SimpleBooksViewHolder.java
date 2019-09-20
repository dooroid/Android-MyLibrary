package com.sendbird.mylibrary.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sendbird.mylibrary.R;

public class SimpleBooksViewHolder extends RecyclerView.ViewHolder {

    private CardView mView;
    private ImageView mImage;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mPrice;
    private TextView mUrl;
    private TextView mIsbn13;

    public SimpleBooksViewHolder(CardView v) {
        super(v);
        mView = v;
        mImage = v.findViewById(R.id.book_image);
        mTitle = v.findViewById(R.id.title_text);
        mSubtitle = v.findViewById(R.id.subtitle_text);
        mPrice = v.findViewById(R.id.price_text);
        mUrl = v.findViewById(R.id.url_text);
        mIsbn13 = v.findViewById(R.id.isbn13_text);
    }

    public void setImage (String image) {
        Glide.with(mView).load(image).into(mImage);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        mSubtitle.setText(subtitle);
    }

    public void setPrice(String price) {
        mPrice.setText(price);
    }

    public void setUrl(String url) {
        mUrl.setText(url);
    }

    public void setIsbn13(String isbn13) {
        mIsbn13.setText(isbn13);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mView.setOnClickListener(listener);
    }
}
