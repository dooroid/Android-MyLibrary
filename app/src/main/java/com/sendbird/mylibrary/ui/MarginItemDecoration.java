package com.sendbird.mylibrary.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private int mMargin;

    public MarginItemDecoration(int margin) {
        this.mMargin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mMargin;
        }
        outRect.left = mMargin;
        outRect.right = mMargin;
        outRect.bottom = mMargin;
    }
}
