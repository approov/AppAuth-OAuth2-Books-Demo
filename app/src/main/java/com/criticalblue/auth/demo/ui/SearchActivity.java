package com.criticalblue.auth.demo.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.criticalblue.auth.demo.BooksApp;
import com.criticalblue.auth.demo.books.Book;
import com.criticalblue.auth.demo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();

    private BooksApp app;
    private SearchViewModel viewModel;

    // activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating searchBooks activity");

        super.onCreate(savedInstanceState);

        app = (BooksApp)getApplication();

        // create searchBooks view model

        ViewModelProviders.DefaultFactory factory = new ViewModelProviders.DefaultFactory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel.class);

        // initialize view

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        bindSearchViews();

        // reinitialize observers

        viewModel.getAlertObservable().observe(this, alertObserver);
        viewModel.getProgressObservable().observe(this, progressObserver);
        viewModel.getActivityObservable().observe(this, activityObserver);
        viewModel.getSearchObservable().observe(this, searchObserver);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Resuming searchBooks activity");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Pausing searchBooks activity");

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Destroying searchBooks activity");

        // remove observers

        viewModel.getActionObservable().removeObserver(actionObserver);
        viewModel.getSearchObservable().removeObserver(searchObserver);
        viewModel.getActivityObservable().removeObserver(activityObserver);
        viewModel.getProgressObservable().removeObserver(progressObserver);
        viewModel.getAlertObservable().removeObserver(alertObserver);

        super.onDestroy();
    }

    // activity manager

    private final Observer<ActivityRequest> activityObserver = (activityRequest) -> {
        Log.i(TAG, "Activity request change observed");

        if (activityRequest.getIntent() == null) return;

        startActivityForResult(activityRequest.getIntent(), activityRequest.getResultCode());
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            viewModel.notifyActivityResponse(data, app.RC_FAIL);
        } else {
            viewModel.notifyActivityResponse(data, app.RC_AUTH);
        }
    }


    // action bar

    private ActionState actionState;
    protected MenuItem userItem;
    protected MenuItem searchItem;
    protected MenuItem favoritesItem;
    protected MenuItem loginoutItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "Creating options menu");

        getMenuInflater().inflate(R.menu.menu_shared, menu);

        userItem = menu.findItem(R.id.user_item);
        searchItem = menu.findItem(R.id.search_item);
        favoritesItem = menu.findItem(R.id.favorites_item);
        loginoutItem = menu.findItem(R.id.loginout_item);

        actionState = viewModel.getActionObservable().getValue();

        viewModel.getActionObservable().observe(this, actionObserver);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "Preparing options menu");

        getSupportActionBar().setTitle(actionState.getTitle());

        // someday add user image from userinfo
        userItem.setIcon(R.drawable.ic_account_circle_white_24dp);
        userItem.setVisible(!actionState.isLoginShowing());

        searchItem.setEnabled(actionState.isSearchEnabled());
        favoritesItem.setEnabled(actionState.isFavoritesEnabled());
        loginoutItem.setTitle(actionState.isLoginShowing() ?
                R.string.login : R.string.logout);
        loginoutItem.setEnabled(actionState.isLoginEnabled());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                return (true);
            case R.id.favorites_item:
                Intent myIntent = new Intent(this, FavoritesActivity.class);
                startActivity(myIntent);
                return (true);
            case R.id.loginout_item:
                if (actionState.isLoginShowing()) {
                    viewModel.login();
                } else {
                    viewModel.logout();
                }
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private final Observer<ActionState> actionObserver = (actionState) -> {
        Log.i(TAG, "Action state change observed");

        this.actionState = actionState;

        invalidateOptionsMenu();
    };

    // searchBooks views

    private SearchState searchState;

    protected SearchView searchView;

    @BindView(R.id.search_results)
    RecyclerView booksView;

    @BindView(R.id.search_empty)
    TextView emptyView;

    private BookListAdapter booksAdapter;

    private void bindSearchViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        booksAdapter = new BookListAdapter(selectionListener, gridLayoutManager.getSpanCount());

        booksView.setLayoutManager(gridLayoutManager);
        booksView.setAdapter(booksAdapter);

        searchView=(SearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                View view = SearchActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(SearchActivity.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                clearSearchResults();

                viewModel.search(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final BookSelectionListener selectionListener = new BookSelectionListener() {
        @Override
        public void onSelection(Book book) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Log.i(TAG, "Selected " + book.getTitle());
            }
        }
    };

    private final Observer<SearchState> searchObserver = (searchState) -> {
        Log.i(TAG, "Search change observed");

        this.searchState = searchState;

        displaySearchResults();
    };

    private void clearSearchResults() {
        booksView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    private void displaySearchResults() {
        String query = searchState.getQuery();
        List<Book> books = searchState.getBooks();

        if (books.isEmpty()) {
            if (query != null && query.trim().length() > 0) {
                emptyView.setText(getString(R.string.no_results));
            } else {
                emptyView.setText("");
            }
            booksView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            booksAdapter.setBookList(books);

            emptyView.setVisibility(View.GONE);
            booksView.setVisibility(View.VISIBLE);
        }
    }

    // progress indicator

    private ProgressState progressState;

    @BindView(R.id.progress_overlay)
    View progressOverlay;

    @BindView(R.id.progress_description)
    TextView progressDescription;

    private final Observer<ProgressState> progressObserver = (progressState) -> {
        Log.i(TAG, "Progress change observed");

        this.progressState = progressState;

        displayProgress();
    };

    private void displayProgress() {
        progressOverlay.setVisibility(progressState.isBusy() ? View.VISIBLE : View.GONE);
        if (progressState.getMsg() != null) {
            progressDescription.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            progressDescription.setText(progressState.getMsg());
        } else {
            progressDescription.setText("");
        }
    }

    // alert trigger

    private AlertTrigger alertTrigger;

    private final Observer<AlertTrigger> alertObserver = (alertTrigger) -> {
        Log.i(TAG, "Alert trigger observed");

        this.alertTrigger = alertTrigger;

        displayAlert();
    };

    private void displayAlert() {
        if (alertTrigger == null || alertTrigger.getMsg() == null) return;

        AlertTrigger.Callback callback = alertTrigger.getCallback();
        AlertDialog.OnClickListener listener = null;
        if (callback != null) {
            listener = new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface di, int i) {
                    callback.call();
                }
            };
        }

        AlertBox.show(this, alertTrigger.getMsg(), listener);
    }
}

