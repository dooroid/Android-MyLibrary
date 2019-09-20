package com.sendbird.mylibrary;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sendbird.mylibrary.data.source.BooksRepository;
import com.sendbird.mylibrary.data.source.local.BooksLocalDataSource;
import com.sendbird.mylibrary.data.source.local.LibraryDatabase;
import com.sendbird.mylibrary.data.source.remote.BooksRemoteDataSource;
import com.sendbird.mylibrary.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static BooksRepository provideBooksRepository(@NonNull Context context) {
        checkNotNull(context);
        LibraryDatabase database = LibraryDatabase.getInstance(context);
        return BooksRepository.getInstance(BooksRemoteDataSource.getInstance(),
                BooksLocalDataSource.getInstance(new AppExecutors(),
                        database.booksDao()));
    }
}

