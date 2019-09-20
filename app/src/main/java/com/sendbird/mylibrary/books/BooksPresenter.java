package com.sendbird.mylibrary.books;

import androidx.annotation.NonNull;


import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class BooksPresenter implements BooksContract.Presenter {
    private final BooksRepository mBooksRepository;

    private BooksContract.View mBooksView;

    public BooksPresenter(@NonNull BooksRepository booksRepository, @NonNull BooksContract.View booksView) {

        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mBooksView = checkNotNull(booksView, "booksView cannot be null!");

        mBooksView.setPresenter(this);
    }

    @Override
    public void start() {
        mBooksRepository.getBooks(new BooksDataSource.LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                System.out.println(books.get(0).getPrice());
                mBooksView.showBooks(books);
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
            }
        });
    }

    @Override
    public void openBookDetails(@NonNull Book requestedBook) {
        checkNotNull(requestedBook, "requestedBook cannot be null!");
        mBooksView.showBookDetailsUi(requestedBook.getId());
    }
}
