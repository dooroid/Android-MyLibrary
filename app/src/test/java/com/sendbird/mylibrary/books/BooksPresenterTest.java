package com.sendbird.mylibrary.books;

import com.google.common.collect.Lists;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link BooksPresenter}
 */
public class BooksPresenterTest {
    private static List<Book> BOOKS;

    @Mock
    private BooksRepository mBooksRepository;

    @Mock
    private BooksContract.View mBooksView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.LoadBooksCallback> mLoadBooksCallbackCaptor;


    private BooksPresenter mBooksPresenter;

    @Before
    public void setupBooksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mBooksPresenter = new BooksPresenter(mBooksRepository, mBooksView);

        // We start the tasks to 3, with one active and two completed
        BOOKS = Lists.newArrayList(new Book("One Thing", null, "1", null, null, null, null, null, null, null, null, null, null, null, true, 0L),
                new Book("The 5AM Miracle", null, "2", null, null, null, null, null, null, null, null, null, null, null, true, 0L),
                new Book("The Power of Detail", null, "3", null, null, null, null, null, null, null, null, null, null, null, true, 0L));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mBooksPresenter = new BooksPresenter(mBooksRepository, mBooksView);

        // Then the presenter is set to the view
        verify(mBooksView).setPresenter(mBooksPresenter);
    }

    @Test
    public void loadEmptyBooksFromRepository_CallViewToDisplay() {
        BOOKS.clear();

        // When loading of Books is requested
        mBooksPresenter.start();

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).getBooks(mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mBooksView).showBooks(BOOKS);
    }

    @Test
    public void loadNonEmptyBooksFromRepository_CallViewToDisplay() {
        mBooksPresenter.start();

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).getBooks(mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mBooksView).showBooks(BOOKS);
    }

    @Test
    public void openBookDetail_CallViewToDisplayChanged() {
        mBooksPresenter.openBookDetails(BOOKS.get(0));
        verify(mBooksView).showBookDetailsUi("1");
    }
}
