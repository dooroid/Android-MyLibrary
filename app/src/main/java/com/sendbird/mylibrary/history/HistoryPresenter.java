package com.sendbird.mylibrary.history;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class HistoryPresenter implements HistoryContract.Presenter {
    //    private final BooksRepository mBooksRepository;

    private HistoryContract.View mHistoryView;

    public HistoryPresenter(@NonNull HistoryContract.View historyView) {

//        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mHistoryView = checkNotNull(historyView, "historyView cannot be null!");

        mHistoryView.setPresenter(this);
    }

    @Override
    public void start() {
        // loadBookmark();
    }
}
