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

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sendbird.mylibrary.data.Book;

import java.util.List;


@Dao
public interface BooksDao {

    @Query("SELECT * FROM Books")
    List<Book> getBooks();

    @Query("SELECT * FROM Books WHERE id = :bookId")
    Book getBookById(String bookId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Update
    int updateBook(Book book);

    @Query("UPDATE books SET bookmark = :bookmark WHERE id = :bookId")
    void updateBookmark(String bookId, boolean bookmark);

//    @Query("UPDATE books SET completed = :completed WHERE entryid = :bookId")
//    void updateCompleted(String bookId, boolean completed);

    @Query("DELETE FROM Books WHERE id = :bookId")
    int deleteBookById(String bookId);

    @Query("DELETE FROM Books")
    void deleteBooks();

//    @Query("DELETE FROM Books WHERE completed = 1")
//    int deleteCompletedBooks();
}
