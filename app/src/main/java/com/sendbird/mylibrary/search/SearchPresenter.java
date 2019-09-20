package com.sendbird.mylibrary.search;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter implements SearchContract.Presenter {
    //    private final BooksRepository mBooksRepository;

    private SearchContract.View mSearchView;

    public SearchPresenter(@NonNull SearchContract.View searchView) {

//        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mSearchView = checkNotNull(searchView, "searchView cannot be null!");

        mSearchView.setPresenter(this);
    }

    @Override
    public void start() {
        // loadBookmark();
    }
}
