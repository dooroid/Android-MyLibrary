package com.sendbird.mylibrary.history;

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
import com.sendbird.mylibrary.ui.BookItemListener;
import com.sendbird.mylibrary.ui.DetailBooksAdapter;
import com.sendbird.mylibrary.ui.MarginItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class HistoryFragment extends Fragment implements HistoryContract.View {

    private HistoryContract.Presenter mPresenter;

    private DetailBooksAdapter mAdapter;

    private boolean mIsEdit = false;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DetailBooksAdapter(new ArrayList<Book>(), new BookItemListener() {
            @Override
            public void onBookClick(Book clickedBook) {
                mPresenter.removeHistory(clickedBook.getId());
                mAdapter.removeData(clickedBook);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_frag, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = root.findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new MarginItemDecoration((int) getContext().getResources()
                                                        .getDimension(R.dimen.list_item_padding)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.history_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem editMenu = menu.findItem(R.id.edit_list);
        MenuItem goneMenu = menu.findItem(R.id.done);

        if (mIsEdit) {
            editMenu.setVisible(false);
            goneMenu.setVisible(true);
        } else {
            editMenu.setVisible(true);
            goneMenu.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_list :
                showEditButton();
                mIsEdit = true;
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.done :
                hideEditButton();
                mIsEdit = false;
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void showEditButton() {
        mAdapter.setEditButtonVisibility(View.VISIBLE);
    }

    private void hideEditButton() {
        mAdapter.setEditButtonVisibility(View.GONE);
    }
}
