package com.sendbird.mylibrary.data.source.remote;


import com.sendbird.mylibrary.data.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookService {
    @GET("/1.0/new")
    Call<ResponseBody> getBooks();

    @GET("/1.0/books/{isbn13}")
    Call<Book> getBook(@Path("isbn13") String isbn3);

    @GET("/1.0/search/{qeury}")
    Call<ResponseBody> searchBooks(@Path("qeury") String qeury);

    @GET("/1.0/search/{qeury}/{page}")
    Call<ResponseBody> searchBooks(@Path("qeury") String qeury, @Path("page") int page);
}
