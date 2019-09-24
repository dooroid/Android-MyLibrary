package com.sendbird.mylibrary.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.data.Book;
import com.sendbird.mylibrary.ui.DetailBooksAdapter;
import com.sendbird.mylibrary.ui.MarginItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class HistoryFragment extends Fragment implements HistoryContract.View {

    private HistoryContract.Presenter mPresenter;

    private DetailBooksAdapter mAdapter;

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
        mAdapter = new DetailBooksAdapter(new ArrayList<Book>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_frag, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new MarginItemDecoration((int) getContext().getResources()
                                                        .getDimension(R.dimen.list_item_padding)));
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
}
