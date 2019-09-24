package com.sendbird.mylibrary.bookdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sendbird.mylibrary.util.Injection;
import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.util.ActivityUtils;

public class BookDetailActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "BOOK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        String bookId = getIntent().getStringExtra(EXTRA_BOOK_ID);

        BookDetailFragment bookDetailFragment = (BookDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (bookDetailFragment == null) {
            bookDetailFragment = BookDetailFragment.newInstance(bookId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    bookDetailFragment, R.id.contentFrame);
        }

        // Create the presenter
        new BookDetailPresenter(
                bookId,
                Injection.provideBooksRepository(getApplicationContext()),
                bookDetailFragment);
    }
}
