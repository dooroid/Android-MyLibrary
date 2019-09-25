package com.sendbird.mylibrary.books;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.BasePresenter;
import com.sendbird.mylibrary.BaseView;
import com.sendbird.mylibrary.data.Book;

import java.util.List;

public interface BooksContract {

    interface View extends BaseView<Presenter> {

        void showBooks(List<Book> books);

        void showBookDetailsUi(String bookId);

        void showNotice(int redId);
    }

    interface Presenter extends BasePresenter {

        void openBookDetails(@NonNull Book requestedBook);
    }
}
