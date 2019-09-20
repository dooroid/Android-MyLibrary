package com.sendbird.mylibrary.data.source.remote;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksRemoteDataSource implements BooksDataSource {
    private static BooksRemoteDataSource INSTANCE;

    private final static Retrofit mRetrofit;

    static {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.itbook.store/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static BooksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BooksRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private BooksRemoteDataSource() {}

    @Override
    public void getBooks(@NonNull final LoadBooksCallback callback) {
        BookService service = mRetrofit.create(BookService.class);
        Call<ResponseBody> call = service.getBooks();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call,
                                   @Nullable Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    callback.onBooksLoaded(response.body().books);
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    };

    @Override
    public void getBook(@NonNull String bookId, @NonNull GetBookCallback callback) {

    }

    @Override
    public void saveBook(@NonNull Book book) {

    }

    @Override
    public void refreshBooks() {

    }

    @Override
    public void deleteAllBooks() {

    }

    @Override
    public void deleteBook(@NonNull String bookId) {

    }
}
