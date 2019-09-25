package com.sendbird.mylibrary.data.source.remote;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;

import java.util.ArrayList;
import java.util.List;

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
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    };

    @Override
    public void getBook(@NonNull String bookId, @NonNull final GetBookCallback callback) {
        BookService service = mRetrofit.create(BookService.class);
        Call<Book> call = service.getBook(bookId);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(@Nullable Call<Book> call,
                                   @Nullable Response<Book> response) {
                if (response != null && response.body() != null) {
                    callback.onBookLoaded(response.body());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@Nullable Call<Book> call, @Nullable Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getBookmark(@NonNull LoadBooksCallback callback) {

    }

    @Override
    public void addBookmark(@NonNull String bookId) {

    }

    @Override
    public void removeBookmark(@NonNull String bookId) {

    }

    @Override
    public void getHistory(@NonNull LoadBooksCallback callback) {

    }

    @Override
    public void addHistory(@NonNull String bookId) {

    }

    @Override
    public void removeHistory(@NonNull String bookId) {

    }

    @Override
    public void searchBooks(@NonNull final String query, @NonNull final LoadBooksCallback callback) {
        BookService service = mRetrofit.create(BookService.class);
        Call<ResponseBody> call = service.searchBooks(query);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call,
                                   @Nullable Response<ResponseBody> response) {
                if (response != null && response.body() != null && response.body().error == 0) {
                    if (response.body().total <= 10) {
                        callback.onBooksLoaded(response.body().books);
                    } else {
                        getSearchBooks(query, callback, response.body().total);
                    }
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@Nullable Call<ResponseBody> call,
                                  @Nullable Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getSearchBooks(@NonNull String query,
                                @NonNull final LoadBooksCallback callback,
                                int total) {
        int totalPage = total%10 == 0 ? total/10 : total/10+1;
        final List<Book> books = new ArrayList<>();

        for (int page = 1 ; page <= totalPage ; page++) {
            BookService service = mRetrofit.create(BookService.class);
            Call<ResponseBody> call = service.searchBooks(query, page);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@Nullable Call<ResponseBody> call,
                                       @Nullable Response<ResponseBody> response) {
                    if (response != null && response.body() != null) {
                        books.addAll(response.body().books);
                        callback.onBooksLoaded(books);
                    } else {
                        callback.onDataNotAvailable();
                    }
                }

                @Override
                public void onFailure(@Nullable Call<ResponseBody> call, @Nullable Throwable t) {
                    callback.onDataNotAvailable();
                }
            });
        }
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

    @Override
    public void addMemo(@NonNull String bookId, @Nullable String memo) {

    }
}
