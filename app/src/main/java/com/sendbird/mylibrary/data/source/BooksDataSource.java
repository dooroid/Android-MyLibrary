package com.sendbird.mylibrary.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sendbird.mylibrary.data.Book;

import java.util.List;

public interface BooksDataSource {

    interface LoadBooksCallback {

        void onBooksLoaded(List<Book> books);

        void onDataNotAvailable();
    }

    interface GetBookCallback {

        void onBookLoaded(Book book);

        void onDataNotAvailable();
    }

    void getBooks(@NonNull LoadBooksCallback callback);

    void getBook(@NonNull String bookId, @NonNull GetBookCallback callback);

    void getBookmark(@NonNull LoadBooksCallback callback);

    void addBookmark(@NonNull String bookId);

    void removeBookmark(@NonNull String bookId);

    void getHistory(@NonNull LoadBooksCallback callback);

    void addHistory(@NonNull String bookId);

    void removeHistory(@NonNull String bookId);

    void searchBooks(@NonNull String query, @NonNull LoadBooksCallback callback);

    void saveBook(@NonNull Book book);

    void refreshBooks();

    void deleteAllBooks();

    void deleteBook(@NonNull String bookId);

    void addMemo(@NonNull String bookId, @Nullable String memo);
}
