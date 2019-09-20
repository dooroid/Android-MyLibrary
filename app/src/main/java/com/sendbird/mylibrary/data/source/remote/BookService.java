package com.sendbird.mylibrary.data.source.remote;


import com.sendbird.mylibrary.data.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookService {
    @GET("/1.0/new")
    Call<ResponseBody> getBooks();

    @GET("/1.0/books/{isbn13}")
    Call<Book> getBook(@Path("isbn13") String isbn3);

    @GET("/1.0/search/{qeury}")
    Call<List<Book>> searchBooks(@Query("qeury") String qeury);

    @GET("/1.0/search/{qeury}/{page}")
    Call<List<Book>> searchBooks(@Query("qeury") String qeury, @Path("page") String page);
}
