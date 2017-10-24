package com.criticalblue.auth.demo.ui;

import android.content.Intent;

public class ActivityRequest {
    private Intent intent;
    private int rc;

    public ActivityRequest(Intent intent, int rc) {
        this.intent = intent;
        this.rc = rc;
    }

    public ActivityRequest() {
        this(null, 0);
    }

    public Intent getIntent() {
        return intent;
    }

    public int getResultCode() {
        return rc;
    }
}
