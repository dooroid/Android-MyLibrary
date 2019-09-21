package com.sendbird.mylibrary.bookdetail;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookDetailPresenter implements BookDetailContract.Presenter {
    private final BooksRepository mBooksRepository;

    private final BookDetailContract.View mBookDetailView;

    @NonNull
    private String mBookId;

    private Book mBook;

    public BookDetailPresenter(@NonNull String bookId, @NonNull BooksRepository booksRepository, @NonNull BookDetailContract.View bookDetailView) {

        mBookId = bookId;

        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mBookDetailView = checkNotNull(bookDetailView, "bookDetailView cannot be null!");

        mBookDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openBook();
    }

    private void openBook() {
        mBooksRepository.getBook(mBookId, new BooksDataSource.GetBookCallback() {
            @Override
            public void onBookLoaded(Book book) {
                mBook = book;
                mBookDetailView.showBookDetail(mBook);
                mBookDetailView.showBookmark(mBook.isBookmark());
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
            }
        });
    }

    @Override
    public void bookmarkBook() {
        if (mBook == null) {
            return;
        }

        if (mBook.isBookmark()) {
            mBooksRepository.removeBookmark(mBook);
        } else {
            mBooksRepository.addBookmark(mBook);
        }

        openBook();
    }

    @Override
    public void addHistory() {
        if (mBook != null) {
            mBooksRepository.addHistory(mBook);
        }
    }
}
