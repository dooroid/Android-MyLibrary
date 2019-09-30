package com.sendbird.mylibrary.search;

import com.sendbird.mylibrary.BasePresenter;
import com.sendbird.mylibrary.BaseView;
import com.sendbird.mylibrary.data.Book;

import java.util.List;

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void showBooks(List<Book> books);

        void updateSearchHistory(List<String> history);

        void showNotice(int resId);

        void showBookDetailsUi(String bookId);

    }

    interface Presenter extends BasePresenter {

        void searchBooks(String str);

        void openBookDetails(Book clickedBook);
    }
}
