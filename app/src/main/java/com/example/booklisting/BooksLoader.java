package com.example.booklisting;

import android.content.Context;

import androidx.annotation.Nullable;

import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class BooksLoader extends AsyncTaskLoader<ArrayList<Books>> {
    private static final String LOG_TAG = BooksLoader.class.getName();
    private String url;

    public BooksLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Books> loadInBackground() {
        if (url == null) {
            return null;
        }
        ArrayList<Books> book = QueryUtils.fetchBookData(url);
        return book;
    }
}
