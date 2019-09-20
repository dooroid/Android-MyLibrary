package com.sendbird.mylibrary.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.bookdetail.BookDetailActivity;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.ui.BookItemListener;
import com.sendbird.mylibrary.ui.SimpleBooksAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class BooksFragment extends Fragment implements BooksContract.View {

    private BooksContract.Presenter mPresenter;

    private SimpleBooksAdapter mAdapter;
    private BookItemListener mItemListener = new BookItemListener() {
        @Override
        public void onBookClick(Book clickedBook) {
            mPresenter.openBookDetails(clickedBook);
        }
    };

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public void setPresenter(BooksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SimpleBooksAdapter(new ArrayList<Book>(0), mItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.books_frag, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.books_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showBooks(List<Book> books) {
        mAdapter.replaceData(books);
    }

    @Override
    public void showBookDetailsUi(String bookId) {
        Intent intent = new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID, bookId);
        startActivity(intent);
    }
}
