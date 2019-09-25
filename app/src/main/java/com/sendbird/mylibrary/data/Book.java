package com.sendbird.mylibrary.data;

import android.annotation.TargetApi;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Immutable model class for a Book.
 */
@Entity(tableName = "books")
public final class Book implements Comparable<Book> {

    @Nullable
    @ColumnInfo(name = "title")
    @SerializedName(value = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "subtitle")
    @SerializedName(value = "subtitle")
    private final String mSubtitle;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName(value = "isbn13")
    private final String mId;

    @Nullable
    @ColumnInfo(name = "price")
    @SerializedName(value = "price")
    private final String mPrice;

    @Nullable
    @ColumnInfo(name = "image")
    @SerializedName(value = "image")
    private final String mImage;

    @Nullable
    @ColumnInfo(name = "url")
    @SerializedName(value = "url")
    private final String mUrl;

    // for detail
    @Nullable
    @ColumnInfo(name = "authors")
    @SerializedName(value = "authors")
    private final String mAuthors;

    @Nullable
    @ColumnInfo(name = "publisher")
    @SerializedName(value = "publisher")
    private final String mPublisher;

    @Nullable
    @ColumnInfo(name = "language")
    @SerializedName(value = "language")
    private final String mLanguage;

    @Nullable
    @ColumnInfo(name = "isbn10")
    @SerializedName(value = "isbn10")
    private final String mIsbn10;

    @Nullable
    @ColumnInfo(name = "pages")
    @SerializedName(value = "pages")
    private final String mPages;

    @Nullable
    @ColumnInfo(name = "year")
    @SerializedName(value = "year")
    private final String mYear;

    @Nullable
    @ColumnInfo(name = "rating")
    @SerializedName(value = "rating")
    private final String mRating;

    @Nullable
    @ColumnInfo(name = "desc")
    @SerializedName(value = "desc")
    private final String mDesc;

    @ColumnInfo(name = "bookmark")
    private final boolean mBookmark;

    @ColumnInfo(name = "history")
    private final long mHistory;

    @Nullable
    @ColumnInfo(name = "memo")
    private String mMemo;

    @Ignore
    public Book(@Nullable String title,
                @NonNull String id,
                boolean bookmark,
                long history) {
        this(title, "", id, "", "", "", "",
                "", "", "", "", "", "",
                "", bookmark, history, "");
    }

    public Book(@Nullable String title,
                @Nullable String subtitle,
                @NonNull String id,
                @Nullable String price,
                @Nullable String image,
                @Nullable String url,
                @Nullable String authors,
                @Nullable String publisher,
                @Nullable String language,
                @Nullable String isbn10,
                @Nullable String pages,
                @Nullable String year,
                @Nullable String rating,
                @Nullable String desc,
                boolean bookmark,
                long history,
                @Nullable String memo) {

        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mId = id;
        this.mPrice = price;
        this.mImage = image;
        this.mUrl = url;
        this.mAuthors = authors;
        this.mPublisher = publisher;
        this.mLanguage = language;
        this.mIsbn10 = isbn10;
        this.mPages = pages;
        this.mYear = year;
        this.mRating = rating;
        this.mDesc = desc;
        this.mBookmark = bookmark;
        this.mHistory = history;
        this.mMemo = memo;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getSubtitle() {
        return mSubtitle;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getPrice() {
        return mPrice;
    }

    @Nullable
    public String getImage() {
        return mImage;
    }

    @Nullable
    public String getUrl() {
        return mUrl;
    }

    @Nullable
    public String getAuthors() {
        return mAuthors;
    }

    @Nullable
    public String getPublisher() {
        return mPublisher;
    }

    @Nullable
    public String getLanguage() {
        return mLanguage;
    }

    @Nullable
    public String getIsbn10() {
        return mIsbn10;
    }

    @Nullable
    public String getPages() {
        return mPages;
    }

    @Nullable
    public String getYear() {
        return mYear;
    }

    @Nullable
    public String getRating() {
        return mRating;
    }

    @Nullable
    public String getDesc() {
        return mDesc;
    }

    public boolean isBookmark() {
        return mBookmark;
    }

    public long getHistory() {
        return mHistory;
    }

    public void setMemo(String memo) {
        if (memo != null) {
            this.mMemo = memo;
        }
    }

    @Nullable
    public String getMemo() {
        return mMemo;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(@NonNull Book book) {
        return Long.compare(book.getHistory(), this.mHistory);
    }

//    public boolean isEmpty() {
//        return Strings.isNullOrEmpty(mTitle) &&
//                Strings.isNullOrEmpty(mDescription);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Task task = (Task) o;
//        return Objects.equal(mId, task.mId) &&
//                Objects.equal(mTitle, task.mTitle) &&
//                Objects.equal(mDescription, task.mDescription);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(mId, mTitle, mDescription);
//    }
//
//    @Override
//    public String toString() {
//        return "Task with title " + mTitle;
//    }
}
