package com.sendbird.mylibrary.bookdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sendbird.mylibrary.R;

import static com.google.common.base.Preconditions.checkNotNull;


public class BookDetailFragment extends Fragment implements BookDetailContract.View {

    @NonNull
    private static final String ARGUMENT_BOOK_ID = "BOOK_ID";

    private  BookDetailContract.Presenter mPresenter;

    public static BookDetailFragment newInstance(@Nullable String bookId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_BOOK_ID, bookId);
        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(@NonNull BookDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bookdetail_frag, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
