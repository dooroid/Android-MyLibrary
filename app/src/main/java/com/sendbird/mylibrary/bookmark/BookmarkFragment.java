package com.sendbird.mylibrary.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.ui.DetailBooksAdapter;
import com.sendbird.mylibrary.ui.MarginItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class BookmarkFragment extends Fragment implements BookmarkContract.View {

    private  BookmarkContract.Presenter mPresenter;

    private DetailBooksAdapter mAdapter;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    @Override
    public void setPresenter(@NonNull BookmarkContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DetailBooksAdapter(new ArrayList<Book>(0));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bookmark_frag, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = root.findViewById(R.id.bookmark_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new MarginItemDecoration((int) getContext().getResources()
                                                        .getDimension(R.dimen.list_item_padding)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.title_sort :
                mPresenter.sortByTitle();
                return true;
            case R.id.price_sort:
                mPresenter.sortByPrice();
                return true;
            case R.id.authors_sort:
                mPresenter.sortByAuthors();
                return true;
            case R.id.publisher_sort:
                mPresenter.sortByPublisher();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showBooks(List<Book> books) {
        mAdapter.replaceData(books);
    }
}
