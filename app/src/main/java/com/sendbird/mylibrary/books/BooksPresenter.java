package com.sendbird.mylibrary.books;

import androidx.annotation.NonNull;


import static com.google.common.base.Preconditions.checkNotNull;

public class BooksPresenter implements BooksContract.Presenter {
    //    private final BooksRepository mBooksRepository;

    private BooksContract.View mBooksView;

    public BooksPresenter(@NonNull BooksContract.View booksView) {

//        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mBooksView = checkNotNull(booksView, "booksView cannot be null!");

        mBooksView.setPresenter(this);
    }

    @Override
    public void start() {
        // loadBookmark();
    }
}
