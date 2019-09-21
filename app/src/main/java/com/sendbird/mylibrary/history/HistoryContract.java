package com.sendbird.mylibrary.history;

import com.sendbird.mylibrary.BasePresenter;
import com.sendbird.mylibrary.BaseView;
import com.sendbird.mylibrary.data.Book;

import java.util.List;

public interface HistoryContract {

    interface View extends BaseView<Presenter> {
        void showBooks(List<Book> books);
    }

    interface Presenter extends BasePresenter {

    }
}
