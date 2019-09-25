package com.sendbird.mylibrary.data.source.local;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class BooksLocalDataSource implements BooksDataSource {

    private static volatile BooksLocalDataSource INSTANCE;

    private BooksDao mBooksDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private BooksLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull BooksDao booksDao) {
        mAppExecutors = appExecutors;
        mBooksDao = booksDao;
    }

    public static BooksLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull BooksDao booksDao) {
        if (INSTANCE == null) {
            synchronized (BooksLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BooksLocalDataSource(appExecutors, booksDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getBooks(@NonNull final LoadBooksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Book> books = mBooksDao.getBooks();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (books.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onBooksLoaded(books);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getBook(@NonNull final String bookId, @NonNull final GetBookCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Book book = mBooksDao.getBookById(bookId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (book != null && book.getIsbn10() != null) {
                            callback.onBookLoaded(book);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getBookmark(@NonNull final LoadBooksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Book> books = mBooksDao.getBookmark(true);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (books.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onBooksLoaded(books);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void addBookmark(@NonNull final String bookId) {
        Runnable bookmarkRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.updateBookmark(bookId, true);
            }
        };

        mAppExecutors.diskIO().execute(bookmarkRunnable);
    }

    @Override
    public void removeBookmark(@NonNull final String bookId) {
        Runnable bookmarkRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.updateBookmark(bookId, false);
            }
        };

        mAppExecutors.diskIO().execute(bookmarkRunnable);
    }

    @Override
    public void getHistory(@NonNull final LoadBooksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Book> books = mBooksDao.getHistory();

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (books.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onBooksLoaded(books);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void addHistory(@NonNull final String bookId) {
        Runnable bookmarkRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.updateHistory(bookId, System.currentTimeMillis());
            }
        };

        mAppExecutors.diskIO().execute(bookmarkRunnable);
    }

    @Override
    public void removeHistory(@NonNull final String bookId) {
        Runnable bookmarkRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.updateHistory(bookId, 0L);
            }
        };

        mAppExecutors.diskIO().execute(bookmarkRunnable);
    }

    @Override
    public void searchBooks(@NonNull String query, @NonNull LoadBooksCallback callback) {

    }

    @Override
    public void saveBook(@NonNull final Book book) {
        checkNotNull(book);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.insertBook(book);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshBooks() {
        // Not required because the {@link booksRepository} handles the logic of refreshing the
        // books from all the available data sources.
    }

    @Override
    public void deleteAllBooks() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.deleteBooks();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteBook(@NonNull final String bookId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.deleteBookById(bookId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void addMemo(@NonNull final String bookId, @Nullable final String memo) {
        Runnable bookmarkRunnable = new Runnable() {
            @Override
            public void run() {
                mBooksDao.updateMemo(bookId, memo);
            }
        };

        mAppExecutors.diskIO().execute(bookmarkRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
