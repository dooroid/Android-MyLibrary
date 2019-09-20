package com.sendbird.mylibrary.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sendbird.mylibrary.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class BooksFragment extends Fragment implements BooksContract.View {

    private BooksContract.Presenter mPresenter;

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public void setPresenter(BooksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.books_frag, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
