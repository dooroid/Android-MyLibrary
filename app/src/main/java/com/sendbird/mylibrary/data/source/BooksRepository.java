package com.sendbird.mylibrary.data.source;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sendbird.mylibrary.data.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class BooksRepository implements BooksDataSource {

    private static BooksRepository INSTANCE = null;

    private final BooksDataSource mBooksRemoteDataSource;

    private final BooksDataSource mBooksLocalDataSource;

    Map<String, Book> mCachedBooks;

    Map<String, List<Book>> mSearcedBookCache;

    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private BooksRepository(@NonNull BooksDataSource booksRemoteDataSource,
                            @NonNull BooksDataSource booksLocalDataSource) {
        mBooksRemoteDataSource = checkNotNull(booksRemoteDataSource);
        mBooksLocalDataSource = checkNotNull(booksLocalDataSource);
    }

    public static BooksRepository getInstance(BooksDataSource booksRemoteDataSource,
                                              BooksDataSource booksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new BooksRepository(booksRemoteDataSource, booksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getBooks(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedBooks != null && !mCacheIsDirty) {
            callback.onBooksLoaded(new ArrayList<>(mCachedBooks.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getBooksFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mBooksLocalDataSource.getBooks(new LoadBooksCallback() {
                @Override
                public void onBooksLoaded(List<Book> books) {
                    refreshCache(books);
                    callback.onBooksLoaded(new ArrayList<>(mCachedBooks.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getBooksFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getBook(@NonNull final String bookId, @NonNull final GetBookCallback callback) {
        checkNotNull(bookId);
        checkNotNull(callback);

        Book cachedBook = getBookWithId(bookId);

        // Respond immediately with cache if available
        if (cachedBook != null && cachedBook.getIsbn10() != null) {
            callback.onBookLoaded(cachedBook);
            return;
        }

        // Load from server/persisted if needed.

        // Is the book in the local data source? If not, query the network.
        mBooksLocalDataSource.getBook(bookId, new GetBookCallback() {
            @Override
            public void onBookLoaded(Book book) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedBooks == null) {
                    mCachedBooks = new LinkedHashMap<>();
                }
                mCachedBooks.put(book.getId(), book);
                callback.onBookLoaded(book);
            }

            @Override
            public void onDataNotAvailable() {
                mBooksRemoteDataSource.getBook(bookId, new GetBookCallback() {
                    @Override
                    public void onBookLoaded(Book book) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedBooks == null) {
                            mCachedBooks = new LinkedHashMap<>();
                        }
                        mCachedBooks.put(book.getId(), book);
                        mBooksLocalDataSource.saveBook(book);
                        callback.onBookLoaded(book);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void getBookmark(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        mBooksLocalDataSource.getBookmark(new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                Collections.sort(books);
                callback.onBooksLoaded(books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getBookmarkByTitleSort(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        mBooksLocalDataSource.getBookmark(new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return book.getTitle().compareTo(t1.getTitle());
                    }
                });
                callback.onBooksLoaded(books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getBookmarkByPriceSort(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        mBooksLocalDataSource.getBookmark(new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return book.getPrice().compareTo(t1.getPrice());
                    }
                });
                callback.onBooksLoaded(books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getBookmarkByAuthorsSort(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        mBooksLocalDataSource.getBookmark(new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return book.getAuthors().compareTo(t1.getAuthors());
                    }
                });
                callback.onBooksLoaded(books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getBookmarkByPublisherSort(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        mBooksLocalDataSource.getBookmark(new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book book, Book t1) {
                        return book.getPublisher().compareTo(t1.getPublisher());
                    }
                });
                callback.onBooksLoaded(books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void addBookmark(@NonNull String bookId) {
        checkNotNull(bookId);
        mBooksRemoteDataSource.addBookmark(bookId);
        mBooksLocalDataSource.addBookmark(bookId);
        refreshCache(bookId);
    }

    @Override
    public void removeBookmark(@NonNull String bookId) {
        checkNotNull(bookId);
        mBooksRemoteDataSource.removeBookmark(bookId);
        mBooksLocalDataSource.removeBookmark(bookId);
        refreshCache(bookId);
    }

    @Override
    public void getHistory(@NonNull final LoadBooksCallback callback) {
        checkNotNull(callback);

        mBooksLocalDataSource.getHistory(new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                Collections.sort(books);
                callback.onBooksLoaded(books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void addHistory(@NonNull String bookId) {
        checkNotNull(bookId);
        mBooksRemoteDataSource.addHistory(bookId);
        mBooksLocalDataSource.addHistory(bookId);
        refreshCache(bookId);
    }

    @Override
    public void removeHistory(@NonNull String bookId) {
        checkNotNull(bookId);
        mBooksRemoteDataSource.removeHistory(bookId);
        mBooksLocalDataSource.removeHistory(bookId);
        refreshCache(bookId);
    }

    @Override
    public void searchBooks(@NonNull final String query, @NonNull final LoadBooksCallback callback) {
        if (mSearcedBookCache != null && mSearcedBookCache.containsKey(query)) {
            callback.onBooksLoaded(mSearcedBookCache.get(query));
            return;
        }

        mBooksRemoteDataSource.searchBooks(query, new LoadBooksCallback() {
            @Override
            public void onBooksLoaded(List<Book> books) {
                callback.onBooksLoaded(books);
                addSearchCache(query, books);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveBook(@NonNull Book book) {
        checkNotNull(book);
        mBooksRemoteDataSource.saveBook(book);
        mBooksLocalDataSource.saveBook(book);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedBooks == null) {
            mCachedBooks = new LinkedHashMap<>();
        }
        mCachedBooks.put(book.getId(), book);
    }

    @Override
    public void refreshBooks() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllBooks() {
        mBooksRemoteDataSource.deleteAllBooks();
        mBooksLocalDataSource.deleteAllBooks();

        if (mCachedBooks == null) {
            mCachedBooks = new LinkedHashMap<>();
        }
        mCachedBooks.clear();
    }

    @Override
    public void deleteBook(@NonNull String bookId) {
        mBooksRemoteDataSource.deleteBook(checkNotNull(bookId));
        mBooksLocalDataSource.deleteBook(checkNotNull(bookId));

        mCachedBooks.remove(bookId);
    }

    @Override
    public void addMemo(@NonNull String bookId, @Nullable String memo) {
        checkNotNull(bookId);
        mBooksLocalDataSource.addMemo(bookId, memo);
        refreshCache(bookId);
    }

    public List<String> getSearchHistory() {
        if (mSearcedBookCache != null && mSearcedBookCache.keySet().size() != 0) {
            ArrayList<String> history = new ArrayList<>(mSearcedBookCache.keySet());
            Collections.reverse(history);
            return history;
        } else {
            return null;
        }
    }

    private void addSearchCache(@NonNull String keyword, @NonNull List<Book> books) {
        checkNotNull(books);

        if (mSearcedBookCache == null) {
            mSearcedBookCache = new LinkedHashMap<>();
        }

        mSearcedBookCache.put(keyword, books);
    }

    private void getBooksFromRemoteDataSource(@NonNull final LoadBooksCallback callback) {
        mBooksRemoteDataSource.getBooks(new LoadBooksCallback() {

            @Override
            public void onBooksLoaded(List<Book> books) {
                refreshCache(books);
                refreshLocalDataSource(books);
                callback.onBooksLoaded(new ArrayList<>(mCachedBooks.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Book> books) {
        if (mCachedBooks == null) {
            mCachedBooks = new LinkedHashMap<>();
        }
        mCachedBooks.clear();
        for (Book book : books) {
            mCachedBooks.put(book.getId(), book);
        }
        mCacheIsDirty = false;
    }

    private void refreshCache(final String bookId) {
        if (mCachedBooks == null) {
            mCachedBooks = new LinkedHashMap<>();
        }

        mBooksLocalDataSource.getBook(bookId, new GetBookCallback() {
            @Override
            public void onBookLoaded(Book book) {
                mCachedBooks.put(book.getId(), book);
            }

            @Override
            public void onDataNotAvailable() {
                mBooksRemoteDataSource.getBook(bookId, new GetBookCallback() {
                    @Override
                    public void onBookLoaded(Book book) {
                        mBooksLocalDataSource.saveBook(book);
                        mCachedBooks.put(bookId, book);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mCachedBooks.remove(bookId);
                    }
                });
            }
        });
    }

    private void refreshLocalDataSource(List<Book> books) {
        mBooksLocalDataSource.deleteAllBooks();
        for (Book book : books) {
            mBooksLocalDataSource.saveBook(book);
        }
    }

    @Nullable
    private Book getBookWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedBooks == null || mCachedBooks.isEmpty()) {
            return null;
        } else {
            return mCachedBooks.get(id);
        }
    }
}
