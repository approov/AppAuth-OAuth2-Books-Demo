package com.criticalblue.auth.demo;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.criticalblue.auth.demo.auth.AuthRepo;
import com.criticalblue.auth.demo.books.BooksRepo;
import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BooksApp extends Application {
    private final String TAG = BooksApp.class.getSimpleName();

    public final static int RC_FAIL = 0;
    public final static int RC_AUTH = 100;

    private AuthRepo authRepo;

    private BooksRepo booksRepo;

    @Override
    public void onCreate (){
        super.onCreate();

        Log.i(TAG, "Creating BooksApp");

        authRepo = new AuthRepo(this);
        Log.i(TAG, "Auth repo created");

        booksRepo = new BooksRepo(this, authRepo);
        Log.i(TAG, "Books service created");
    }

    public AuthRepo getAuthRepo() {
        return authRepo;
    }

    public BooksRepo getBooksRepo() {
        return booksRepo;
    }

    public boolean isRegisteredUri(Uri uri) {
        Intent redirectIntent = new Intent();
        redirectIntent.setPackage(this.getPackageName());
        redirectIntent.setAction(Intent.ACTION_VIEW);
        redirectIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        redirectIntent.setData(uri);

        return !this.getPackageManager().queryIntentActivities(redirectIntent, 0).isEmpty();
    }

    public String getSignature() {
        PackageManager pm = getPackageManager();
        String packageName = getPackageName();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (packageInfo == null
                    || packageInfo.signatures == null
                    || packageInfo.signatures.length == 0
                    || packageInfo.signatures[0] == null) {
                return null;
            }
            return signatureDigest(packageInfo.signatures[0]);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static String signatureDigest(Signature sig) {
        byte[] signature = sig.toByteArray();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] digest = md.digest(signature);
            return BaseEncoding.base16().lowerCase().encode(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public int getColorValue(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getColor(color);
        } else {
            return getResources().getColor(color);
        }
    }
}
