package com.sendbird.mylibrary.search;

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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link SearchPresenter}
 */
public class SearchPresenterTest {

    private static final String STRING = "computer";

    private static List<Book> BOOKS;

    @Mock
    private BooksRepository mBooksRepository;

    @Mock
    private SearchContract.View mSearchView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.LoadBooksCallback> mLoadBooksCallbackCaptor;


    private SearchPresenter mSearchPresenter;

    @Before
    public void setupSearchPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mSearchPresenter = new SearchPresenter(mBooksRepository, mSearchView);

        // We start the books to 3, with one active and two completed
        BOOKS = Lists.newArrayList(new Book("One Thing", null, "1", null, null, null, null, null, null, null, null, null, null, null, true, 0L),
                new Book("The 5AM Miracle", null, "2", null, null, null, null, null, null, null, null, null, null, null, true, 0L),
                new Book("The Power of Detail", null, "3", null, null, null, null, null, null, null, null, null, null, null, true, 0L));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mSearchPresenter = new SearchPresenter(mBooksRepository, mSearchView);

        // Then the presenter is set to the view
        verify(mSearchView).setPresenter(mSearchPresenter);
    }

    @Test
    public void loadEmptyBooksFromRepository_CallViewToDisplay() {
        BOOKS.clear();

        // When loading of Books is requested
        mSearchPresenter.searchBooks(STRING);

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).searchBooks(eq(STRING), mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mSearchView).showBooks(BOOKS);
    }

    @Test
    public void loadNonEmptyBooksFromRepository_CallViewToDisplay() {
        mSearchPresenter.searchBooks(STRING);

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).searchBooks(eq(STRING), mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mSearchView).showBooks(BOOKS);
    }
}
