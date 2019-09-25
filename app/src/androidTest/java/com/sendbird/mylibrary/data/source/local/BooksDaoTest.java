package com.sendbird.mylibrary.data.source.local;


import com.sendbird.mylibrary.data.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class BooksDaoTest {
    private static final Book BOOK = new Book("One Thing", "1", true, 0L);

    private LibraryDatabase mDatabase;

    @Before
    public void initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                LibraryDatabase.class).build();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void insertBook_GetById() {
        // When inserting a book
        mDatabase.booksDao().insertBook(BOOK);

        // When getting the book by id from the database
        Book loaded = mDatabase.booksDao().getBookById(BOOK.getId());

        // The loaded data contains the expected values
        assertBook(loaded, BOOK.getId(), BOOK.getTitle(),  BOOK.isBookmark(), BOOK.getHistory());
    }

    @Test
    public void insertBook_ReplacesOnConflict() {
        //Given that a book is inserted
        mDatabase.booksDao().insertBook(BOOK);

        // When a book with the same id is inserted
        Book newBook = new Book("The 5AM Miracle", "1",false, 454646L);
        mDatabase.booksDao().insertBook(newBook);
        // When getting the book by id from the database
        Book loaded = mDatabase.booksDao().getBookById(BOOK.getId());

        // The loaded data contains the expected values
        assertBook(loaded, newBook.getId(), newBook.getTitle(), newBook.isBookmark(), newBook.getHistory());
    }

    @Test
    public void insertBook_GetBooks() {
        // When inserting a book
        mDatabase.booksDao().insertBook(BOOK);

        // When getting the books from the database
        List<Book> books = mDatabase.booksDao().getBooks();

        // There is only 1 book in the database
        assertThat(books.size(), is(1));
        // The loaded data contains the expected values
        assertBook(books.get(0), BOOK.getId(), BOOK.getTitle(),  BOOK.isBookmark(), BOOK.getHistory());
    }

    @Test
    public void updateBook_GetById() {
        // When inserting a book
        mDatabase.booksDao().insertBook(BOOK);

        // When the book is updated
        Book newBook = new Book("The 5AM Miracle", "1", false, 454646L);
        mDatabase.booksDao().updateBook(newBook);

        // When getting the book by id from the database
        Book loaded = mDatabase.booksDao().getBookById(BOOK.getId());

        // The loaded data contains the expected values
        assertBook(loaded, newBook.getId(), newBook.getTitle(), newBook.isBookmark(), newBook.getHistory());
    }

    @Test
    public void updateBookmark_GetById() {
        // When inserting a book
        mDatabase.booksDao().insertBook(BOOK);

        // When the book is updated
        mDatabase.booksDao().updateBookmark(BOOK.getId(), false);

        // When getting the book by id from the database
        Book loaded = mDatabase.booksDao().getBookById(BOOK.getId());

        // The loaded data contains the expected values
        assertBook(loaded, BOOK.getId(), BOOK.getTitle(),  false, BOOK.getHistory());
    }

    @Test
    public void updateHistory_GetById() {
        // When inserting a book
        mDatabase.booksDao().insertBook(BOOK);

        // When the book is updated
        mDatabase.booksDao().updateHistory(BOOK.getId(), 123L);

        // When getting the book by id from the database
        Book loaded = mDatabase.booksDao().getBookById(BOOK.getId());

        // The loaded data contains the expected values
        assertBook(loaded, BOOK.getId(), BOOK.getTitle(),  BOOK.isBookmark(), 123L);
    }

    @Test
    public void updateMemo_GetById() {
        String memo = "LALLDLFALSDFAS";

        // When inserting a book
        mDatabase.booksDao().insertBook(BOOK);

        // When the book is updated
        mDatabase.booksDao().updateMemo(BOOK.getId(), memo);

        // When getting the book by id from the database
        Book loaded = mDatabase.booksDao().getBookById(BOOK.getId());

        // The loaded data contains the expected values
        assertThat(loaded.getMemo(), is(memo));
    }

    @Test
    public void deleteBookById_GettingBooks() {
        //Given a book inserted
        mDatabase.booksDao().insertBook(BOOK);

        //When deleting a book by id
        mDatabase.booksDao().deleteBookById(BOOK.getId());

        //When getting the books
        List<Book> books = mDatabase.booksDao().getBooks();
        // The list is empty
        assertThat(books.size(), is(0));
    }

    @Test
    public void deleteBooks_GettingBooks() {
        //Given a book inserted
        mDatabase.booksDao().insertBook(BOOK);

        //When deleting all books
        mDatabase.booksDao().deleteBooks();

        //When getting the books
        List<Book> books = mDatabase.booksDao().getBooks();
        // The list is empty
        assertThat(books.size(), is(0));
    }

    private void assertBook(Book book, String id, String title, boolean bookmark, long history) {
        assertThat(book, notNullValue());
        assertThat(book.getId(), is(id));
        assertThat(book.getTitle(), is(title));
        assertThat(book.isBookmark(), is(bookmark));
        assertThat(book.getHistory(), is(history));
    }
}
