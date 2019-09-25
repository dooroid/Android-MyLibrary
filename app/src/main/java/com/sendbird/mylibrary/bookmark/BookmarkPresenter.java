package com.sendbird.mylibrary.bookmark;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookmarkPresenter implements BookmarkContract.Presenter {
    private final BooksRepository mBooksRepository;

    private final BookmarkContract.View mBookmarkView;

    public BookmarkPresenter(@NonNull BooksRepository booksRepository, @NonNull BookmarkContract.View bookmarkView) {

        mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        mBookmarkView = checkNotNull(bookmarkView, "bookmarkView cannot be null!");

        mBookmarkView.setPresenter(this);
    }

    @Override
    public void start() {
         mBooksRepository.getBookmark(new BooksDataSource.LoadBooksCallback() {
             @Override
             public void onBooksLoaded(List<Book> books) {
                 mBookmarkView.showBooks(books);
             }

             @Override
             public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
             }
         });
    }

    @Override
    public void sortByTitle() {
        mBooksRepository.getBookmarkByTitleSort(new BooksDataSource.LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                mBookmarkView.showBooks(books);
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
            }
        });
    }

    @Override
    public void sortByPrice() {
        mBooksRepository.getBookmarkByPriceSort(new BooksDataSource.LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                mBookmarkView.showBooks(books);
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
            }
        });
    }

    @Override
    public void sortByAuthors() {
        mBooksRepository.getBookmarkByAuthorsSort(new BooksDataSource.LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                mBookmarkView.showBooks(books);
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
            }
        });
    }

    @Override
    public void sortByPublisher() {
        mBooksRepository.getBookmarkByPublisherSort(new BooksDataSource.LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                mBookmarkView.showBooks(books);
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("onDataNotAvailable");
            }
        });
    }

    @Override
    public void removeBookmark(String bookId) {
        mBooksRepository.removeBookmark(bookId);
    }
}
