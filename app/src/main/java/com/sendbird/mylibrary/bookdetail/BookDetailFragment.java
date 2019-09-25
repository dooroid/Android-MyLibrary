package com.sendbird.mylibrary.bookdetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.data.Book;

import static com.google.common.base.Preconditions.checkNotNull;


public class BookDetailFragment extends Fragment implements BookDetailContract.View {

    @NonNull
    private static final String ARGUMENT_BOOK_ID = "BOOK_ID";

    private  BookDetailContract.Presenter mPresenter;

    private ImageView mImage;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mPrice;
    private TextView mUrl;
    private TextView mIsbn13;

    private TextView mAuthors;
    private TextView mPublisher;
    private TextView mLanguage;
    private TextView mIsbn10;
    private TextView mPages;
    private TextView mYear;
    private TextView mRating;
    private TextView mDesc;

    private TextView mMemoView;

    private FloatingActionButton mBookmark;

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

        mImage = root.findViewById(R.id.book_image);
        mTitle = root.findViewById(R.id.title_text);
        mSubtitle = root.findViewById(R.id.subtitle_text);
        mPrice = root.findViewById(R.id.price_text);
        mUrl = root.findViewById(R.id.url_text);
        mIsbn13 = root.findViewById(R.id.isbn13_text);

        mAuthors = root.findViewById(R.id.authors_text);
        mPublisher = root.findViewById(R.id.publisher_text);
        mLanguage = root.findViewById(R.id.language_text);
        mIsbn10 = root.findViewById(R.id.isbn10_text);
        mPages = root.findViewById(R.id.pages_text);
        mYear = root.findViewById(R.id.year_text);
        mRating = root.findViewById(R.id.rating_text);
        mDesc = root.findViewById(R.id.desc_text);

        mMemoView = root.findViewById(R.id.memo_view);

        // Set up floating action button
        mBookmark = root.findViewById(R.id.fab_bookmark);
        mBookmark.setImageResource(R.drawable.ic_star_disabled);
        mBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.bookmarkBook();
            }
        });

        FloatingActionButton memoEdit = root.findViewById(R.id.fab_memo);
        memoEdit.setImageResource(R.drawable.ic_write);
        memoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View memoView = inflater.inflate(R.layout.memo_dialog, null);
                final EditText memo = memoView.findViewById(R.id.memo_edit);

                builder.setTitle(R.string.memo_title)
                        .setView(memoView)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mPresenter.addMemo(memo.getText().toString());
                            }})
                        .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }})
                        .setNegativeButton(R.string.remove, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mPresenter.addMemo("");
                            }});
                AlertDialog dialog = builder.create();
                dialog.show();
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
    public void showBookDetail(Book book) {
        Glide.with(this).load(book.getImage()).into(mImage);
        mTitle.setText(book.getTitle());
        mSubtitle.setText(book.getSubtitle());
        mPrice.setText(book.getPrice());
        mUrl.setText(book.getUrl());
        mIsbn13.setText(book.getId());

        mAuthors.setText(book.getAuthors());
        mPublisher.setText(book.getPublisher());
        mLanguage.setText(book.getLanguage());
        mIsbn10.setText(book.getIsbn10());
        mPages.setText(book.getPages());
        mYear.setText(book.getYear());
        mRating.setText(book.getRating());
        mDesc.setText(book.getDesc());
        showMemo(book.getMemo());

        mPresenter.addHistory();
    }

    @Override
    public void showBookmark(boolean isBookmark) {
        if (isBookmark) {
            mBookmark.setImageResource(R.drawable.ic_star_enabled);
        } else {
            mBookmark.setImageResource(R.drawable.ic_star_disabled);
        }
    }

    @Override
    public void showMemo(String memo) {
        if (memo == null || memo.isEmpty()) {
            mMemoView.setVisibility(View.INVISIBLE);
        } else {
            mMemoView.setVisibility(View.VISIBLE);
            mMemoView.setText(memo);
        }
    }
}
