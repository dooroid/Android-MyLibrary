package com.sendbird.mylibrary.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sendbird.mylibrary.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class HistoryFragment extends Fragment implements HistoryContract.View {

    private HistoryContract.Presenter mPresenter;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_frag, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
