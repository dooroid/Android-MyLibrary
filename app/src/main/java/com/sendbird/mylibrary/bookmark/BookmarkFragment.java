package com.sendbird.mylibrary.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sendbird.mylibrary.R;

import static com.google.common.base.Preconditions.checkNotNull;


public class BookmarkFragment extends Fragment implements BookmarkContract.View {

    private  BookmarkContract.Presenter mPresenter;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    @Override
    public void setPresenter(@NonNull BookmarkContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bookmark_frag, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
