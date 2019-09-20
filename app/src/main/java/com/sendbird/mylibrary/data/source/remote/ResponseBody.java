package com.sendbird.mylibrary.data.source.remote;

import com.sendbird.mylibrary.data.Book;

import java.util.List;

public class ResponseBody {

    String error;
    String total;

    List<Book> books;

    @Override
    public String toString() {
        return total;
    }
}
