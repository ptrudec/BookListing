package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    private static final String REQUEST = "https://www.googleapis.com/books/v1/volumes";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private String requestUrl;
    private String input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_search);

        ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);


        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /*ListView bookListView = (ListView) findViewById(R.id.list);

                        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                        bookListView.setEmptyView(mEmptyStateTextView);

                        mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());

                        bookListView.setAdapter(mAdapter);*/
                        EditText text = (EditText) findViewById(R.id.search_input);

                        Uri baseUri = Uri.parse(REQUEST);
                        Uri.Builder uriBuilder = baseUri.buildUpon();
                        input = text.getText().toString();

                        uriBuilder.appendQueryParameter("q", input);
                        uriBuilder.appendQueryParameter("maxResults", "10");


                        requestUrl = uriBuilder.toString();
                        Log.e("Query", "Query: " + requestUrl);
                        // Get a reference to the ConnectivityManager to check state of network connectivity
                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        // Get details on the currently active default data network
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                        if (networkInfo != null && networkInfo.isConnected()) {

                            View loadingIndicator = findViewById(R.id.loading_indicator);
                            View emptyView = findViewById(R.id.empty_view);
                            loadingIndicator.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);

                            LoaderManager loaderManager = getLoaderManager();
                            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                            // because this activity implements the LoaderCallbacks interface).

                            if (loaderManager.getLoader(BOOK_LOADER_ID) == null) {
                                loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                            } else {
                                loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                            }

                        } else {
                            // Otherwise, display error
                            // First, hide loading indicator so error message will be visible
                            View loadingIndicator = findViewById(R.id.loading_indicator);
                            loadingIndicator.setVisibility(View.GONE);

                            // Update empty state with no connection error message
                            mEmptyStateTextView.setText(R.string.no_connection);

                        }

                    }
                }

        );

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, requestUrl);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

}
