package com.sendbird.mylibrary.bookmark;

import com.sendbird.mylibrary.BasePresenter;
import com.sendbird.mylibrary.BaseView;
import com.sendbird.mylibrary.data.Book;

import java.util.List;

public interface BookmarkContract {

    interface View extends BaseView<Presenter> {

        void showBooks(List<Book> books);

    }

    interface Presenter extends BasePresenter {



    }
}
