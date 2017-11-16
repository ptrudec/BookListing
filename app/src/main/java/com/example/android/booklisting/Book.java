package com.example.android.booklisting;

import android.media.Image;

/**
 * Created by Pero on 6.11.2017..
 */

public class Book {
    private String mTitleOfBook;
    private String mAuthorOfBook;
    private String mImageLocation;

    public Book(String title, String author, String image) {
        this.mTitleOfBook = title;
        this.mAuthorOfBook = author;
        this.mImageLocation = image;
    }

    public String getTitle() {
        return mTitleOfBook;
    }

    public String getAuthor() {
        return mAuthorOfBook;
    }

    public String getImage () { return mImageLocation;}

}
