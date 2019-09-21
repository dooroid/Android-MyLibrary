package com.sendbird.mylibrary.data.source;

import androidx.annotation.NonNull;

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

    void addBookmark(@NonNull Book book);

    void removeBookmark(@NonNull Book book);

    void searchBooks(@NonNull String query, @NonNull LoadBooksCallback callback);

    void saveBook(@NonNull Book book);

    void refreshBooks();

    void deleteAllBooks();

    void deleteBook(@NonNull String bookId);
}
