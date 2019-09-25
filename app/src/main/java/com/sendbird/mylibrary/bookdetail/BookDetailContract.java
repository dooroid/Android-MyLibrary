package com.sendbird.mylibrary.bookdetail;

import com.sendbird.mylibrary.BasePresenter;
import com.sendbird.mylibrary.BaseView;
import com.sendbird.mylibrary.data.Book;

public interface BookDetailContract {

    interface View extends BaseView<Presenter> {

        void showBookDetail(Book book);

        void showBookmark(boolean isBookmark);

        void showMemo(String memo);

    }

    interface Presenter extends BasePresenter {
        void removeBookmark();

        void addBookmark();

        void addHistory();

        void addMemo(String memo);
    }
}
