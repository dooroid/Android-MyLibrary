package com.sendbird.mylibrary.ui;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sendbird.mylibrary.Injection;
import com.sendbird.mylibrary.R;
import com.sendbird.mylibrary.bookmark.BookmarkFragment;
import com.sendbird.mylibrary.bookmark.BookmarkPresenter;
import com.sendbird.mylibrary.books.BooksFragment;
import com.sendbird.mylibrary.books.BooksPresenter;
import com.sendbird.mylibrary.data.source.BooksRepository;
import com.sendbird.mylibrary.history.HistoryFragment;
import com.sendbird.mylibrary.history.HistoryPresenter;
import com.sendbird.mylibrary.search.SearchFragment;
import com.sendbird.mylibrary.search.SearchPresenter;

public class LibraryPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_new,
                                                    R.string.tab_search,
                                                    R.string.tab_bookmark,
                                                    R.string.tab_history};
    private static final int SEARCH = 1;
    private static final int BOOKMARK = 2;
    private static final int HISTORY = 3;

    private final Context mContext;


    public LibraryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        BooksRepository booksRepository =
                Injection.provideBooksRepository(mContext.getApplicationContext());

        switch (position) {
            case SEARCH :
                fragment = SearchFragment.newInstance();
                new SearchPresenter(booksRepository, (SearchFragment) fragment);
                break;

            case BOOKMARK :
                fragment = BookmarkFragment.newInstance();
                new BookmarkPresenter(booksRepository, (BookmarkFragment) fragment);
                break;

            case HISTORY :
                fragment = HistoryFragment.newInstance();
                new HistoryPresenter((HistoryFragment) fragment);
                break;

            default:
                fragment = BooksFragment.newInstance();
                new BooksPresenter(booksRepository, (BooksFragment) fragment);
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }


    @Override
    public int getCount() {
        return 4;
    }
}
