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
import com.criticalblue.auth.demo.auth.AuthLogoutListener;
import com.criticalblue.auth.demo.auth.AuthRepo;
import com.criticalblue.auth.demo.books.BooksRepo;

import java.util.Collections;

public class FavoritesViewModel extends AndroidViewModel {
    private static final String TAG = FavoritesViewModel.class.getSimpleName();

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

    private MutableLiveData<FavoritesState> favoritesObservable;
    public LiveData<FavoritesState> getFavoritesObservable() {
        return favoritesObservable;
    }

    public FavoritesViewModel(Application application) {
        super(application);

        Log.i(TAG, "Creating favorites view model");

        // find repositories

        app = (BooksApp) application;
        authRepo = app.getAuthRepo();
        booksRepo = app.getBooksRepo();

        // establish view observables

        progressObservable = new MutableLiveData<ProgressState>();
        progressObservable.setValue(new ProgressState());

        alertObservable = new MutableLiveData<AlertTrigger>();
        alertObservable.setValue(new AlertTrigger());

        String firstName = null;
        if (authRepo.isAuthorized() && authRepo.getUserInfo() != null) firstName = authRepo.getUserInfo().getfirstName();
        if (firstName != null) {
            actionTitle = app.getString(R.string.favorites_format, firstName);
        } else {
            actionTitle = app.getString(R.string.favorites_blank);
        }
        actionObservable = new MutableLiveData<ActionState>();
        actionObservable.setValue(new ActionState(
                actionTitle,
                true, false, true,
                !authRepo.isAuthorized(), null)
        );

        activityObservable = new MutableLiveData<ActivityRequest>();
        activityObservable.setValue(new ActivityRequest());

        favoritesObservable = new MutableLiveData<FavoritesState>();
        favoritesObservable.setValue(new FavoritesState(
                Collections.emptyList()));
    }

    public void logout() {
        authRepo.logout(logoutListener);
    }

    private final AuthLogoutListener logoutListener =  new AuthLogoutListener() {
        public void onStart(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
            progressObservable.postValue(new ProgressState(true, description));
            actionObservable.postValue(new ActionState(actionTitle,
                    false, false, false, false, null));
        }

        public void onSuccess(AuthRepo repo, AuthEvent event) {
            String description = event.getDescription();
            Log.i(TAG, description);
            actionObservable.postValue(new ActionState(actionTitle,
                    true, false, true, true, null));
            progressObservable.postValue(new ProgressState());
        }

        public void onFailure(AuthRepo repo, AuthEvent event, AuthException ex) {
            String description = event.getDescription() + ": " + ex.getMessage();
            Log.i(TAG, description);
            actionObservable.postValue(new ActionState(actionTitle,
                    true, false, true, false, null));
            progressObservable.postValue(new ProgressState());
            alertObservable.postValue(new AlertTrigger(description, null));
        }
    };

    public void findFavorites() {
        booksRepo.findFavorites((q, books, ex) -> {
            // handle error here

            FavoritesState favoritesState = new FavoritesState(books);
            favoritesObservable.postValue(favoritesState);

            progressObservable.postValue(new ProgressState());
        });
    }
}
