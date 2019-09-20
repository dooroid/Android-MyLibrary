package com.sendbird.mylibrary.bookmark;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.data.source.BooksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookmarkPresenter implements BookmarkContract.Presenter {
//    private final BooksRepository mBooksRepository;

    private final BookmarkContract.View mBookmarkView;

    public BookmarkPresenter(@NonNull BookmarkContract.View bookmarkView) {

//        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mBookmarkView = checkNotNull(bookmarkView, "bookmarkView cannot be null!");

        mBookmarkView.setPresenter(this);
    }

    @Override
    public void start() {
        // loadBookmark();
    }
}
