package com.sendbird.mylibrary.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sendbird.mylibrary.R;

public class DetailBooksViewHolder extends RecyclerView.ViewHolder {

    private View mView;
    private ImageView mImage;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mPrice;
    private TextView mUrl;
    private TextView mIsbn13;

    private TextView mAuthors;
    private TextView mPublisher;
    private TextView mLanguage;
    private TextView mIsbn10;
    private TextView mPages;
    private TextView mYear;
    private TextView mRating;
    private TextView mDesc;

    public DetailBooksViewHolder(View v) {
        super(v);
        mView = v;
        mImage = v.findViewById(R.id.book_image);
        mTitle = v.findViewById(R.id.title_text);
        mSubtitle = v.findViewById(R.id.subtitle_text);
        mPrice = v.findViewById(R.id.price_text);
        mUrl = v.findViewById(R.id.url_text);
        mIsbn13 = v.findViewById(R.id.isbn13_text);

        mAuthors = v.findViewById(R.id.authors_text);
        mPublisher = v.findViewById(R.id.publisher_text);
        mLanguage = v.findViewById(R.id.language_text);
        mIsbn10 = v.findViewById(R.id.isbn10_text);
        mPages = v.findViewById(R.id.pages_text);
        mYear = v.findViewById(R.id.year_text);
        mRating = v.findViewById(R.id.rating_text);
        mDesc = v.findViewById(R.id.desc_text);
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

    public void setAuthors(String authors) {
        mAuthors.setText(authors);
    }

    public void setPublisher(String publisher) {
        mPublisher.setText(publisher);
    }

    public void setLanguage(String language) {
        mLanguage.setText(language);
    }

    public void setIsbn10(String isbn10) {
        mIsbn10.setText(isbn10);
    }

    public void setPages(String pages) {
        mPages.setText(pages);
    }

    public void setYear(String year) {
        mYear.setText(year);
    }

    public void setRating(String rating) {
        mRating.setText(rating);
    }

    public void setDesc(String desc) {
        mDesc.setText(desc);
    }
}
