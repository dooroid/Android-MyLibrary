package com.sendbird.mylibrary.bookdetail;

import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.data.source.BooksDataSource;
import com.sendbird.mylibrary.data.source.BooksRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link BookDetailPresenter}
 */
public class BookDetailPresenterTest {

    public static final String BOOK_ID = "1";

    public static final Book BOOK_MARKED = new Book("One Thing", "1", true, 0L);
    public static final Book BOOK_NOT_MARKED = new Book("One Thing", "1", false, 0L);

    @Mock
    private BooksRepository mBooksRepository;

    @Mock
    private BookDetailContract.View mBookDetailView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<BooksDataSource.GetBookCallback> mGetBookCallbackCaptor;


    private BookDetailPresenter mBookDetailPresenter;

    @Before
    public void setupBookDetailPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mBookDetailPresenter = new BookDetailPresenter(BOOK_ID, mBooksRepository, mBookDetailView);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mBookDetailPresenter = new BookDetailPresenter(BOOK_ID, mBooksRepository, mBookDetailView);

        // Then the presenter is set to the view
        verify(mBookDetailView).setPresenter(mBookDetailPresenter);
    }

    @Test
    public void getActiveBookFromRepository_CallViewToDisplay() {
        mBookDetailPresenter = new BookDetailPresenter(BOOK_ID, mBooksRepository, mBookDetailView);
        mBookDetailPresenter.start();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBook(eq(BOOK_ID), mGetBookCallbackCaptor.capture());

        // When book is finally loaded
        mGetBookCallbackCaptor.getValue().onBookLoaded(BOOK_MARKED); // Trigger callback

        verify(mBookDetailView).showBookDetail(BOOK_MARKED);
        verify(mBookDetailView).showBookmark(BOOK_MARKED.isBookmark());
    }

    @Test
    public void removeBookmark_CallViewToDisplay() {
        mBookDetailPresenter = new BookDetailPresenter(BOOK_ID, mBooksRepository, mBookDetailView);
        mBookDetailPresenter.start();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBook(eq(BOOK_ID), mGetBookCallbackCaptor.capture());

        // When book is finally loaded
        mGetBookCallbackCaptor.getValue().onBookLoaded(BOOK_MARKED); // Trigger callback

        mBookDetailPresenter.removeBookmark();
        verify(mBooksRepository).removeBookmark(BOOK_ID);
    }

    @Test
    public void addBookmark_CallViewToDisplay() {
        mBookDetailPresenter = new BookDetailPresenter(BOOK_ID, mBooksRepository, mBookDetailView);
        mBookDetailPresenter.start();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBook(eq(BOOK_ID), mGetBookCallbackCaptor.capture());

        // When book is finally loaded
        mGetBookCallbackCaptor.getValue().onBookLoaded(BOOK_NOT_MARKED); // Trigger callback

        mBookDetailPresenter.addBookmark();
        verify(mBooksRepository).addBookmark(BOOK_ID);
    }

    @Test
    public void addHistory() {
        mBookDetailPresenter = new BookDetailPresenter(BOOK_ID, mBooksRepository, mBookDetailView);
        mBookDetailPresenter.start();

        // Then book is loaded from model, callback is captured and progress indicator is shown
        verify(mBooksRepository).getBook(eq(BOOK_ID), mGetBookCallbackCaptor.capture());

        // When book is finally loaded
        mGetBookCallbackCaptor.getValue().onBookLoaded(BOOK_NOT_MARKED); // Trigger callback

        mBookDetailPresenter.addHistory();
        verify(mBooksRepository).addHistory(BOOK_ID);
    }
}
