package com.criticalblue.auth.demo.ui;

public class AlertTrigger {
    private String msg;
    private Callback callback;

    public AlertTrigger(String msg, AlertTrigger.Callback callback) {
        this.callback = callback;
        this.msg = msg;
    }

    public AlertTrigger(String msg) {
        this(msg, null);
    }

    public AlertTrigger() {
        this(null, null);
    }

    public String getMsg() {
        return msg;
    }

    public Callback getCallback() {
        return callback;
    }

    public static class Callback {
        void call() {
        }
    }
}