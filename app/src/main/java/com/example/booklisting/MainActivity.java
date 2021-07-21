package com.example.booklisting;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Books>> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    private String Google_REQUEST_URL;
    private BooksAdapter adapter;
    private static final int Books_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private EditText edtxt;
    private GridView booksListView;

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"onCreate");
        edtxt = (EditText) findViewById(R.id.searchEditText);

        if (isEmpty(edtxt)) {
            BooksInfo("best+seller");
        }else
        {
            String text = edtxt.getText().toString();
            BooksInfo(text.replace(" ", "+"));

        }

    }


    public void search(View v) {
        edtxt = (EditText) findViewById(R.id.searchEditText);
        String text = edtxt.getText().toString();
        if (!text.isEmpty()) {
            BooksInfo(text.replace(" ", "+"));
        }
    }

    private void BooksInfo(String query) {
        Google_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=40&orderBy=relevance";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(Books_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        booksListView = (GridView) findViewById(R.id.list);

        adapter = new BooksAdapter(this, new ArrayList<Books>());
        booksListView.setAdapter(adapter);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Books link = adapter.getItem(position);
                Intent i = new Intent(MainActivity.this,BookInfoActivity.class);
                i.putExtra("title",link.getTitle());
                i.putExtra("subTitle",link.getSubTitle());
                i.putExtra("ratingsCount",link.getRatingsCount());
                i.putExtra("price",link.getPrice());
                i.putExtra("publisher",link.getPublisher());
                i.putExtra("description",link.getDescription());
                i.putExtra("category",link.getCategory());
                i.putExtra("date",link.getDate());
                i.putExtra("img_link",link.getImg_link());
                i.putExtra("info_link",link.getInfo_link());
                i.putExtra("rating",link.getRating());

                startActivity(i);
            }
        });
    }

    @Override
    public Loader<ArrayList<Books>> onCreateLoader(int i, Bundle args) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        return new BooksLoader(this, Google_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Books>> loader, ArrayList<Books> data) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            adapter.clear();
            if (data != null && !data.isEmpty()) {
                adapter.addAll(data);
            } else {
                mEmptyStateTextView.setText(R.string.no_books);
            }
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            booksListView = (GridView) findViewById(R.id.list);
            booksListView.setVisibility(View.GONE);
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Books>> loader) {
        adapter.clear();
    }
}