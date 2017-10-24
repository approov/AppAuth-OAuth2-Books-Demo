package com.criticalblue.auth.demo.ui;

public class ProgressState {
    private boolean busy;
    private String msg;

    public ProgressState(boolean busy, String msg) {
        this.busy = busy;
        this.msg = msg;
    }

    public ProgressState() {
        this(false, null);
    }

    public boolean isBusy() {
        return busy;
    }

    public String getMsg() {
        return msg;
    }
}