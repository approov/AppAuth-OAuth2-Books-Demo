package com.criticalblue.auth.demo.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;

import com.criticalblue.auth.demo.BooksApp;
import com.criticalblue.auth.demo.R;
import com.criticalblue.auth.demo.auth.AuthEvent;
import com.criticalblue.auth.demo.auth.AuthException;
import com.criticalblue.auth.demo.auth.AuthLoginListener;
import com.criticalblue.auth.demo.auth.AuthLogoutListener;
import com.criticalblue.auth.demo.auth.AuthRepo;
import com.criticalblue.auth.demo.auth.AuthRepoListener;
import com.criticalblue.auth.demo.books.BooksRepo;

import java.util.Collections;
import java.util.concurrent.ExecutorService;

public class SearchViewModel extends AndroidViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();

    private BooksApp app;

    private AuthRepo authRepo;
    private BooksRepo booksRepo;

    private String actionTitle;
    private MutableLiveData<ActionState> actionObservable;
    public LiveData<ActionState> getActionObservable() {
        return actionObservable;
    }

    private MutableLiveData<AlertTrigger> alertObservable;
    public LiveData<AlertTrigger> getAlertObservable() {
        return alertObservable;
    }

    private MutableLiveData<ProgressState> progressObservable;
    public LiveData<ProgressState> getProgressObservable() {
        return progressObservable;
    }

    private MutableLiveData<ActivityRequest> activityObservable;
    public LiveData<ActivityRequest> getActivityObservable() {
        return activityObservable;
    }

    private MutableLiveData<SearchState> searchObservable;
    public LiveData<SearchState> getSearchObservable() {
        return searchObservable;
    }

    public SearchViewModel(Application application) {
        super(application);

        Log.i(TAG, "Creating searchBooks view model");

        // find repositories

        app = (BooksApp) application;
        authRepo = app.getAuthRepo();
        booksRepo = app.getBooksRepo();

        // establish view observables

        progressObservable = new MutableLiveData<ProgressState>();
        progressObservable.setValue(new ProgressState());

        alertObservable = new MutableLiveData<AlertTrigger>();
        alertObservable.setValue(new AlertTrigger());

        actionTitle = app.getString(R.string.search_title);
        actionObservable = new MutableLiveData<ActionState>();
        actionObservable.setValue(new ActionState(
                actionTitle,
                true, authRepo.isAuthorized(), true,
                !authRepo.isAuthorized(), null)
        );

        activityObservable = new MutableLiveData<ActivityRequest>();
        activityObservable.setValue(new ActivityRequest());

        searchObservable = new MutableLiveData<SearchState>();
        searchObservable.setValue(new SearchState(
                null, Collections.emptyList()));
    }

    public void login() {
        authRepo.login(loginListener);
    }

    public void notifyActivityResponse(Intent data, int resultCode) {
        authRepo.notifyUserAgentResponse(data, resultCode);
    }

    private final AuthLoginListener loginListener =  new AuthLoginListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
            progressObservable.postValue(new ProgressState(true, description));
            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
                    false, false, false, true, null));
        }

        public void onEvent(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            switch (event) {
                case AUTH_SERVICE_DISCOVERY_START:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_SERVICE_DISCOVERY_FINISH:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState());
                    break;
                case AUTH_USER_AUTH_START:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_USER_AUTH_FINISH:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState());
                    break;
                case AUTH_CODE_EXCHANGE_START:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_CODE_EXCHANGE_FINISH:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState());
                    break;
                case AUTH_USER_INFO_START:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState(true, description));
                    break;
                case AUTH_USER_INFO_FINISH:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState());
                    break;
                default:
                    Log.i(TAG, description);
                    progressObservable.postValue(new ProgressState());
                    break;
            }
        }

        public void onUserAgentRequest(AuthRepo repo, Intent intent) {

            Log.i(TAG, "User Agent Request!");

            activityObservable.postValue(new ActivityRequest(intent, app.RC_AUTH));
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
                    true, true, true, false, null));
            progressObservable.postValue(new ProgressState());
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            Log.i(TAG, description);
            alertObservable.postValue(new AlertTrigger(description, null));
            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
                    true, false, true, true, null));
            progressObservable.postValue(new ProgressState());
        }
    };

    public void logout() {
        authRepo.logout(logoutListener);
    }

    private final AuthLogoutListener logoutListener =  new AuthLogoutListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
            progressObservable.postValue(new ProgressState(true, description));
            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
                    false, false, false, false, null));
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
                    true, false, true, true, null));
            progressObservable.postValue(new ProgressState());
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            Log.i(TAG, description);
            actionObservable.postValue(new ActionState(app.getString(R.string.search_title),
                    true, true, true, false, null));
            progressObservable.postValue(new ProgressState());
            alertObservable.postValue(new AlertTrigger(description, null));
        }
    };

    public void search(String query) {
        booksRepo.search(query, (q, books, ex) -> {
            // handle error here

            SearchState searchState = new SearchState(q, books);
            searchObservable.postValue(searchState);

            progressObservable.postValue(new ProgressState());
        });
    }
}