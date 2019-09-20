package com.sendbird.mylibrary.bookdetail;

import com.sendbird.mylibrary.BasePresenter;
import com.sendbird.mylibrary.BaseView;
import com.sendbird.mylibrary.data.Book;

public interface BookDetailContract {

    interface View extends BaseView<Presenter> {
        void showBookDetail(Book book);
    }

    interface Presenter extends BasePresenter {

    }
}
