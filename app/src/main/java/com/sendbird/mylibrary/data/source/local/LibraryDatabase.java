/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sendbird.mylibrary.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sendbird.mylibrary.data.Book;

/**
 * The Room Database that contains the Book table.
 */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {

    private static LibraryDatabase INSTANCE;

    public abstract BooksDao booksDao();

    private static final Object sLock = new Object();

    public static LibraryDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LibraryDatabase.class, "Books.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
