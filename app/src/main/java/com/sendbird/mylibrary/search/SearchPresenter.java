package com.sendbird.mylibrary.search;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter implements SearchContract.Presenter {

    private final BooksRepository mBooksRepository;

    private SearchContract.View mSearchView;

    public SearchPresenter(@NonNull BooksRepository booksRepository,  @NonNull SearchContract.View searchView) {

        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mSearchView = checkNotNull(searchView, "searchView cannot be null!");

        mSearchView.setPresenter(this);
    }

    @Override
    public void start() {
        mSearchView.updateSearchHistory(mBooksRepository.getSearchHistory());
    }

    @Override
    public void searchBooks(String str) {
        mBooksRepository.searchBooks(str, new BooksDataSource.LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                mSearchView.showBooks(books);
                mSearchView.updateSearchHistory(mBooksRepository.getSearchHistory());
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
                mSearchView.showNotice(R.string.notice_no_data);
            }
        });
    }
}
