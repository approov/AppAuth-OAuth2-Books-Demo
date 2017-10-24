package com.criticalblue.auth.demo.ui;

public class ActionState {
    private String title;
    private boolean searchEnabled;
    private boolean favoritesEnabled;
    private boolean loginEnabled;
    private boolean loginShowing;
    private String userImage;

    public ActionState(
            String title,
            boolean searchEnabled,
            boolean favoritesEnabled,
            boolean loginEnabled,
            boolean loginShowing,
            String userImage
    ) {
        this.title = title;
        this.searchEnabled = searchEnabled;
        this.favoritesEnabled = favoritesEnabled;
        this.loginEnabled = loginEnabled;
        this.loginShowing = loginShowing;
        this.userImage = userImage;
    }


    public ActionState(ActionState that) {
        if (that == null) { that = new ActionState(); }

        this.title = that.title;
        this.searchEnabled = that.searchEnabled;
        this.favoritesEnabled = that.favoritesEnabled;
        this.loginEnabled = that.loginEnabled;
        this.loginShowing = that.loginShowing;
        this.userImage = that.userImage;
    }

    public ActionState() {
        this("", false, false, false, true, null);
    }

    public void setTitle() {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSearchEnabled(boolean searchEnabled) {
        this.searchEnabled = searchEnabled;
    }

    public boolean isSearchEnabled() {
        return searchEnabled;
    }

    public void setFavoritesEnabled(boolean favoritesEnabled) {
        this.favoritesEnabled = favoritesEnabled;
    }

    public boolean isFavoritesEnabled() {
        return favoritesEnabled;
    }

    public void setLoginEnabled(boolean loginEnabled) {
        this.loginEnabled = loginEnabled;
    }

    public boolean isLoginEnabled() {
        return loginEnabled;
    }

    public void setLoginShowing(boolean loginShowing) {
        this.loginShowing = loginShowing;
    }

    public boolean isLoginShowing() {
        return loginShowing;
    }

    public void setUserImage() {
        this.userImage = userImage;
    }

    public String getUserImage() {
        return userImage;
    }
}
