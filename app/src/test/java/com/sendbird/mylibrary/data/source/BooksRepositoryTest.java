package com.sendbird.mylibrary.data.source;

import com.google.common.collect.Lists;
import com.sendbird.mylibrary.data.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
public class BooksRepositoryTest {

    private static final String STRING = "computer";

    public static final String BOOK_ID = "1";

    private static List<Book> BOOKS;

    private BooksRepository mBooksRepository;

    @Mock
    private BooksDataSource mBooksRemoteDataSource;

    @Mock
    private BooksDataSource mBooksLocalDataSource;

    @Mock
    private BooksDataSource.GetBookCallback mGetBookCallback;

    @Mock
    private BooksDataSource.LoadBooksCallback mLoadBooksCallback;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.LoadBooksCallback> mLoadBooksCallbackCaptor;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.GetBookCallback> mGetBookCallbackCaptor;

    @Before
    public void setupBooksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mBooksRepository = BooksRepository.getInstance(
                mBooksRemoteDataSource, mBooksLocalDataSource);

        BOOKS = Lists.newArrayList(new Book("One Thing", "1",false, 123533L),
                new Book("The 5AM Miracle", "2",true, 0L),
                new Book("The Power of Detail", "3", true, 0L));
    }

    @After
    public void destroyRepositoryInstance() {
        mBooksRepository.destroyInstance();
    }

    @Test
    public void getBooks_repositoryCachesAfterFirstApiCall() {
        // Given a setup Captor to capture callbacks
        // When two calls are issued to the books repository
        twoBooksLoadCallsToRepository(mLoadBooksCallback);

        // Then books were only requested once from Service API
        verify(mBooksRemoteDataSource).getBooks(any(BooksDataSource.LoadBooksCallback.class));
    }

    @Test
    public void getBooks_requestsAllBooksFromLocalDataSource() {
        // When books are requested from the books repository
        mBooksRepository.getBooks(mLoadBooksCallback);

        // Then books are loaded from the local data source
        verify(mBooksLocalDataSource).getBooks(any(BooksDataSource.LoadBooksCallback.class));
    }

    @Test
    public void getBook_repositoryCachesAfterFirstApiCall() {
        // Given a setup Captor to capture callbacks
        // When two calls are issued to the books repository
        twoBookGetCallsToRepository(mGetBookCallback);

        // Then books were only requested once from Service API
        verify(mBooksRemoteDataSource).getBook(eq(BOOK_ID), any(BooksDataSource.GetBookCallback.class));
    }

    @Test
    public void getBook_requestsSingleBookFromLocalDataSource() {
        // When a book is requested from the books repository
        mBooksRepository.getBook(BOOK_ID, mGetBookCallback);

        // Then the book is loaded from the database
        verify(mBooksLocalDataSource).getBook(eq(BOOK_ID), any(
                BooksDataSource.GetBookCallback.class));
    }

    @Test
    public void saveBook_savesBookToServiceAPI() {
        // When a book is saved to the books repository
        Book newBook = BOOKS.get(0);
        mBooksRepository.saveBook(newBook);

        // Then the service API and persistent repository are called and the cache is updated
        verify(mBooksRemoteDataSource).saveBook(newBook);
        verify(mBooksLocalDataSource).saveBook(newBook);
        assertThat(mBooksRepository.mCachedBooks.size(), is(1));
    }

    @Test
    public void addBookmarkToServiceAPIUpdatesCache() {
        // Given a stub active book with title and description added in the repository
        Book newBook = BOOKS.get(0);
        mBooksRepository.saveBook(newBook);

        // When a book is completed to the books repository
        mBooksRepository.addBookmark(newBook.getId());

        // Then the service API and persistent repository are called and the cache is updated
        verify(mBooksRemoteDataSource).addBookmark(newBook.getId());
        verify(mBooksLocalDataSource).addBookmark(newBook.getId());
        assertThat(mBooksRepository.mCachedBooks.size(), is(1));
    }

    @Test
    public void removeBookmarkToServiceAPIUpdatesCache() {
        // Given a stub active book with title and description added in the repository
        Book newBook = BOOKS.get(1);
        mBooksRepository.saveBook(newBook);

        // When a book is completed to the books repository
        mBooksRepository.removeBookmark(newBook.getId());

        // Then the service API and persistent repository are called and the cache is updated
        verify(mBooksRemoteDataSource).removeBookmark(newBook.getId());
        verify(mBooksLocalDataSource).removeBookmark(newBook.getId());
        assertThat(mBooksRepository.mCachedBooks.size(), is(1));
    }

    @Test
    public void addHistoryToServiceAPIUpdatesCache() {
        // Given a stub active book with title and description added in the repository
        Book newBook = BOOKS.get(0);
        mBooksRepository.saveBook(newBook);

        // When a book is completed to the books repository
        mBooksRepository.addHistory(newBook.getId());

        // Then the service API and persistent repository are called and the cache is updated
        verify(mBooksRemoteDataSource).addHistory(newBook.getId());
        verify(mBooksLocalDataSource).addHistory(newBook.getId());
        assertThat(mBooksRepository.mCachedBooks.size(), is(1));
    }

    @Test
    public void removeHistoryToServiceAPIUpdatesCache() {
        // Given a stub active book with title and description added in the repository
        Book newBook = BOOKS.get(0);
        mBooksRepository.saveBook(newBook);

        // When a book is completed to the books repository
        mBooksRepository.removeHistory(newBook.getId());

        // Then the service API and persistent repository are called and the cache is updated
        verify(mBooksRemoteDataSource).removeHistory(newBook.getId());
        verify(mBooksLocalDataSource).removeHistory(newBook.getId());
        assertThat(mBooksRepository.mCachedBooks.size(), is(1));
    }

    @Test
    public void searchBook_requestsBooksFromRemoteDataSource() {
        // When a book is requested from the books repository
        mBooksRepository.searchBooks(STRING, mLoadBooksCallback);

        // Then the book is loaded from the database
        verify(mBooksRemoteDataSource).searchBooks(eq(STRING), any(
                BooksDataSource.LoadBooksCallback.class));
    }

    @Test
    public void deleteAllBooks_deleteBooksToServiceAPIUpdatesCache() {
        mBooksRepository.saveBook(BOOKS.get(0));
        mBooksRepository.saveBook(BOOKS.get(1));
        mBooksRepository.saveBook(BOOKS.get(2));

        // When all books are deleted to the books repository
        mBooksRepository.deleteAllBooks();

        // Verify the data sources were called
        verify(mBooksRemoteDataSource).deleteAllBooks();
        verify(mBooksLocalDataSource).deleteAllBooks();

        assertThat(mBooksRepository.mCachedBooks.size(), is(0));
    }

    @Test
    public void deleteBook_deleteBookToServiceAPIRemovedFromCache() {
        Book newBook = BOOKS.get(0);
        mBooksRepository.saveBook(newBook);
        assertThat(mBooksRepository.mCachedBooks.containsKey(newBook.getId()), is(true));

        // When deleted
        mBooksRepository.deleteBook(newBook.getId());

        // Verify the data sources were called
        verify(mBooksRemoteDataSource).deleteBook(newBook.getId());
        verify(mBooksLocalDataSource).deleteBook(newBook.getId());

        // Verify it's removed from repository
        assertThat(mBooksRepository.mCachedBooks.containsKey(newBook.getId()), is(false));
    }

    /**
     * Convenience method that issues two calls to the books repository
     */
    private void twoBooksLoadCallsToRepository(BooksDataSource.LoadBooksCallback callback) {
        // When books are requested from repository
        mBooksRepository.getBooks(callback); // First call to API

        // Use the Mockito Captor to capture the callback
        verify(mBooksLocalDataSource).getBooks(mLoadBooksCallbackCaptor.capture());

        // Local data source doesn't have data yet
        mLoadBooksCallbackCaptor.getValue().onDataNotAvailable();


        // Verify the remote data source is queried
        verify(mBooksRemoteDataSource).getBooks(mLoadBooksCallbackCaptor.capture());

        // Trigger callback so books are cached
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        mBooksRepository.getBooks(callback); // Second call to API
    }

    private void twoBookGetCallsToRepository(BooksDataSource.GetBookCallback callback) {
        // When books are requested from repository
        mBooksRepository.getBook(BOOK_ID, callback); // First call to API

        // Use the Mockito Captor to capture the callback
        verify(mBooksLocalDataSource).getBook(eq(BOOK_ID), mGetBookCallbackCaptor.capture());

        // Local data source doesn't have data yet
        mGetBookCallbackCaptor.getValue().onDataNotAvailable();


        // Verify the remote data source is queried
        verify(mBooksRemoteDataSource).getBook(eq(BOOK_ID), mGetBookCallbackCaptor.capture());

        // Trigger callback so books are cached
        mGetBookCallbackCaptor.getValue().onBookLoaded(BOOKS.get(0));

        mBooksRepository.getBook(BOOK_ID, callback); // Second call to API
    }
}
