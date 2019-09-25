package com.sendbird.mylibrary.bookdetail;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookDetailPresenter implements BookDetailContract.Presenter {
    private final BooksRepository mBooksRepository;

    private final BookDetailContract.View mBookDetailView;

    @NonNull
    private String mBookId;

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
                mBookDetailView.showBookDetail(book);
                mBookDetailView.showBookmark(book.isBookmark());
                mBookDetailView.showMemo(book.getMemo());
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
                mBookDetailView.showNotice(R.string.notice_no_data);
            }
        });
    }

    @Override
    public void removeBookmark() {
        mBooksRepository.removeBookmark(mBookId);
        mBookDetailView.showBookmark(false);
        mBookDetailView.showNotice(R.string.notice_remove_bookmark);
    }

    @Override
    public void addBookmark() {
        mBooksRepository.addBookmark(mBookId);
        mBookDetailView.showBookmark(true);
        mBookDetailView.showNotice(R.string.notice_add_bookmark);
    }

    @Override
    public void addHistory() {
        mBooksRepository.addHistory(mBookId);
    }

    @Override
    public void addMemo(String memo) {
        mBooksRepository.addMemo(mBookId, memo);
        mBookDetailView.showMemo(memo);
    }
}
