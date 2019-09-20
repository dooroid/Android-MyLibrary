package com.sendbird.mylibrary.bookdetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sendbird.mylibrary.data.source.BooksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookDetailPresenter implements BookDetailContract.Presenter {
    private final BooksRepository mBooksRepository;

    private final BookDetailContract.View mBookDetailView;

    @Nullable
    private String mBookId;

    public BookDetailPresenter(@Nullable String bookId, @NonNull BooksRepository booksRepository, @NonNull BookDetailContract.View bookDetailView) {

        mBookId = bookId;

        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mBookDetailView = checkNotNull(bookDetailView, "bookDetailView cannot be null!");

        mBookDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        // loadBookmark();
    }
}
