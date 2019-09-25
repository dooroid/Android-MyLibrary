package com.sendbird.mylibrary.history;

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
 * Unit tests for the implementation of {@link HistoryPresenter}
 */
public class HistoryPresenterTest {
    private static List<Book> BOOKS;

    @Mock
    private BooksRepository mBooksRepository;

    @Mock
    private HistoryContract.View mHistoryView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.LoadBooksCallback> mLoadBooksCallbackCaptor;


    private HistoryPresenter mHistoryPresenter;

    @Before
    public void setupHistoryPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mHistoryPresenter = new HistoryPresenter(mBooksRepository, mHistoryView);

        // We start the books to 3, with one active and two completed
        BOOKS = Lists.newArrayList(new Book("One Thing", "1", true, 0L),
                new Book("The 5AM Miracle", "2", true, 0L),
                new Book("The Power of Detail", "3", true, 0L));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mHistoryPresenter = new HistoryPresenter(mBooksRepository, mHistoryView);

        // Then the presenter is set to the view
        verify(mHistoryView).setPresenter(mHistoryPresenter);
    }

    @Test
    public void loadEmptyBooksFromRepository_CallViewToDisplay() {
        BOOKS.clear();

        // When loading of Books is requested
        mHistoryPresenter.start();

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).getHistory(mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mHistoryView).showBooks(BOOKS);
    }

    @Test
    public void loadNonEmptyBooksFromRepository_CallViewToDisplay() {
        mHistoryPresenter.start();

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).getHistory(mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mHistoryView).showBooks(BOOKS);
    }

    @Test
    public void removeHistory() {
        mHistoryPresenter.removeHistory(BOOKS.get(0).getId());
        verify(mBooksRepository).removeHistory(BOOKS.get(0).getId());
    }
}
