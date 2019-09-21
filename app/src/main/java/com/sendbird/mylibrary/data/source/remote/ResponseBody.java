package com.sendbird.mylibrary.data.source.remote;

import com.sendbird.mylibrary.data.Book;

import java.util.List;

public class ResponseBody {

    int error;
    int total;
    int page;

    List<Book> books;
}
