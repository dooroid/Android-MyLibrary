package com.sendbird.mylibrary.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.bookdetail.BookDetailActivity;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.ui.BookItemListener;
import com.sendbird.mylibrary.ui.MarginItemDecoration;
import com.sendbird.mylibrary.ui.SimpleBooksAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.Presenter mPresenter;

    private SimpleBooksAdapter mAdapter;

    private AutoCompleteTextView mSearchBox;

    private BookItemListener mItemListener = new BookItemListener() {
        @Override
        public void onBookClick(Book clickedBook) {
            mPresenter.openBookDetails(clickedBook);
        }
    };

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
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
        View root = inflater.inflate(R.layout.search_frag, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.result_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new MarginItemDecoration((int) getContext().getResources()
                                                        .getDimension(R.dimen.list_item_padding)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mSearchBox = root.findViewById(R.id.edit_query);
        mSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    mPresenter.searchBooks(textView.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });

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
    public void updateSearchHistory(List<String> history) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, history);
        mSearchBox.setAdapter(adapter);
    }

    @Override
    public void showNotice(int resId) {
        Snackbar.make(getView(), resId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showBookDetailsUi(String bookId) {
        Intent intent = new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID, bookId);
        startActivity(intent);
    }
}
