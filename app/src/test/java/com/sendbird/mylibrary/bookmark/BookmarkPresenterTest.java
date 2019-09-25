package com.sendbird.mylibrary.bookmark;

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
 * Unit tests for the implementation of {@link BookmarkPresenter}
 */
public class BookmarkPresenterTest {
    private static List<Book> BOOKS;

    @Mock
    private BooksRepository mBooksRepository;

    @Mock
    private BookmarkContract.View mBookmarkView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.LoadBooksCallback> mLoadBooksCallbackCaptor;


    private BookmarkPresenter mBookmarkPresenter;

    @Before
    public void setupBookmarkPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mBookmarkPresenter = new BookmarkPresenter(mBooksRepository, mBookmarkView);

        // We start the books to 3, with one active and two completed
        BOOKS = Lists.newArrayList(new Book("One Thing", "1", true, 0L),
                new Book("The 5AM Miracle", "2", true, 0L),
                new Book("The Power of Detail", "3", true, 0L));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mBookmarkPresenter = new BookmarkPresenter(mBooksRepository, mBookmarkView);

        // Then the presenter is set to the view
        verify(mBookmarkView).setPresenter(mBookmarkPresenter);
    }

    @Test
    public void loadEmptyBooksFromRepository_CallViewToDisplay() {
        BOOKS.clear();

        // When loading of Books is requested
        mBookmarkPresenter.start();

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).getBookmark(mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mBookmarkView).showBooks(BOOKS);
    }

    @Test
    public void loadNonEmptyBooksFromRepository_CallViewToDisplay() {
        mBookmarkPresenter.start();

        // Callback is captured and invoked with stubbed books
        verify(mBooksRepository).getBookmark(mLoadBooksCallbackCaptor.capture());
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS);

        verify(mBookmarkView).showBooks(BOOKS);
    }

    @Test
    public void loadBooksSortedByTitle_CallViewToDisplay() {
        mBookmarkPresenter.sortByTitle();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBookmarkSortedByTitle(mLoadBooksCallbackCaptor.capture());

        // When book is finally loaded
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS); // Trigger callback

        verify(mBookmarkView).showBooks(BOOKS);
    }

    @Test
    public void loadBooksSortedByPrice_CallViewToDisplay() {
        mBookmarkPresenter.sortByPrice();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBookmarkSortedByPrice(mLoadBooksCallbackCaptor.capture());

        // When book is finally loaded
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS); // Trigger callback

        verify(mBookmarkView).showBooks(BOOKS);
    }

    @Test
    public void loadBooksSortedByAuthors_CallViewToDisplay() {
        mBookmarkPresenter.sortByAuthors();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBookmarkSortedByAuthors(mLoadBooksCallbackCaptor.capture());

        // When book is finally loaded
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS); // Trigger callback

        verify(mBookmarkView).showBooks(BOOKS);
    }

    @Test
    public void loadBooksSortedByPublisher_CallViewToDisplay() {
        mBookmarkPresenter.sortByPublisher();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBookmarkSortedByPublisher(mLoadBooksCallbackCaptor.capture());

        // When book is finally loaded
        mLoadBooksCallbackCaptor.getValue().onBooksLoaded(BOOKS); // Trigger callback

        verify(mBookmarkView).showBooks(BOOKS);
    }

    @Test
    public void removeBookmark() {
        mBookmarkPresenter.removeBookmark(BOOKS.get(0).getId());
        verify(mBooksRepository).removeBookmark(BOOKS.get(0).getId());
    }
}
