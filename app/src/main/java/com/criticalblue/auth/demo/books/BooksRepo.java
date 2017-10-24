package com.criticalblue.auth.demo.books;

import android.util.Log;

import com.criticalblue.auth.demo.BooksApp;
import com.criticalblue.auth.demo.auth.AuthRepo;
import com.criticalblue.auth.demo.books.api.BookListResult;
import com.criticalblue.auth.demo.books.api.BookShelfResult;
import com.criticalblue.auth.demo.books.api.ImageLinks;
import com.criticalblue.auth.demo.books.api.Item;
import com.criticalblue.auth.demo.books.api.VolumeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksRepo {
    private static final String TAG = BooksRepo.class.getSimpleName();

    private static final String BOOKS_URL_BASE = "https://www.googleapis.com/books/v1/";

    private BooksApp app;
    private AuthRepo authRepo;
    private BooksAPI booksAPIwithKey;
    private BooksAPI booksAPIwithToken;

    public BooksRepo(BooksApp app, AuthRepo authRepo) {
        this.app = app;
        this.authRepo = authRepo;
        this.booksAPIwithKey = createBooksAPI(true, false);
        this.booksAPIwithToken = createBooksAPI(false, true);
    }

    private BooksAPI createBooksAPI(boolean withKey, boolean withToken) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        if (withKey) clientBuilder.addInterceptor(authRepo.getApiKeyInterceptor());
        if (withToken) clientBuilder.addInterceptor(authRepo.getAccessTokenInterceptor());
        if (true) clientBuilder.addInterceptor(logger);

        OkHttpClient client = clientBuilder.build();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BOOKS_URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(BooksAPI.class);
    }

    public void search(String query, BookListCallback callback) {
        if (query == null || query.trim().length() == 0 || callback == null) return;

        Call<BookListResult> request = booksAPIwithKey.searchBooks(query);
        request.enqueue(new SearchCallback(query, callback));
    }

    private static class SearchCallback implements Callback<BookListResult> {
        private String query;
        private BookListCallback callback;
        public SearchCallback(String query, BookListCallback callback) {
            this.query = query;
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<BookListResult> call, Response<BookListResult> response) {
            if(response.isSuccessful()) {
                BookListResult result = response.body();

                List<Book> books = new ArrayList<Book>();
                List<Item> items = result.getItems();
                if (items != null) {
                    for (Item item : items) {
                        VolumeInfo info = item.getVolumeInfo();
                        String title = info.getTitle();
                        StringBuilder authorsBuilder = new StringBuilder();
                        List<String> authorList = info.getAuthors();
                        String authors;
                        if (authorList != null && authorList.size() > 0) {
                            for (String author : info.getAuthors()) {
                                authorsBuilder.append(author);
                                authorsBuilder.append(", ");
                            }
                            authors = authorsBuilder.toString();
                        } else {
                            authors = "<none>";
                        }
                        ImageLinks imageLinks = info.getImageLinks();
                        String imageLink = (imageLinks != null) ? imageLinks.getThumbnail() : null;
                        if (imageLink != null) {
                            imageLink = imageLink.replace("edge=curl", "");
                        }

                        books.add(new Book(title, null, null, null,
                                authors, null, imageLink));
                    }
                }
                callback.call(query, books, null);
            } else {
                callback.call(query, Collections.emptyList(), new BooksException("Invalid searchBooks response"));
            }
        }

        @Override
        public void onFailure(Call<BookListResult> call, Throwable t) {
            callback.call(query, Collections.emptyList(),
                    new BooksException("Invalid searchBooks response: " + t.getMessage()));
        }
    }

    private static final String FAVORITES_ID = "0";

    public void findFavorites(BookListCallback callback) {
        Log.i(TAG, "Starting favorites");

        if (callback == null) return;

        fetchBookshelf(FAVORITES_ID, callback);
    }

    private void fetchBookshelf(String id, BookListCallback callback) {
        if (callback == null) return;

        Log.i(TAG, "enqueing bookshelf call");

        Call<BookShelfResult> request = booksAPIwithToken.getBookShelf(id);
        request.enqueue(new BookShelfResultCallback(id, callback));
    }

    private static String SELF_LINK_PREFIX = "https://www.googleapis.com/books/v1/users/";
    private static String SELF_LINK_SUFFIX = "/bookshelves/0";

    private class BookShelfResultCallback implements Callback<BookShelfResult> {
        private String sId;
        private BookListCallback callback;
        public BookShelfResultCallback(String sId, BookListCallback callback) {
            this.sId = sId;
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<BookShelfResult> call, Response<BookShelfResult> response) {
            if(response.isSuccessful()) {
                BookShelfResult result = response.body();

                Log.i(TAG, "good bookshelf response");

                if (result != null && result.getSelfLink() != null) {
                    String uId = null;
                    String selfLink = result.getSelfLink();
                    if (selfLink.startsWith(SELF_LINK_PREFIX) && selfLink.endsWith(SELF_LINK_SUFFIX)) {
                        uId = selfLink.substring(SELF_LINK_PREFIX.length(), selfLink.length() - SELF_LINK_SUFFIX.length());
                        Log.i(TAG, "Found " + uId + "; fetching favorites");
                        fetchFavorites(uId, sId, callback);
                        return;
                    }
                }
            }
            callback.call(null, Collections.emptyList(), new BooksException("Unable to find User's Books ID"));
        }

        @Override
        public void onFailure(Call<BookShelfResult> call, Throwable t) {
            callback.call(null, Collections.emptyList(), new BooksException("Unable to find User's Books ID"));
        }
    }

    private void fetchFavorites(String uId, String sId, BookListCallback callback) {
        if (callback == null) return;

        Log.i(TAG, "calling findShelvedBooks " + uId + ", " + sId);

        Call<BookListResult> request = booksAPIwithToken.findShelvedBooks(uId, sId);
        request.enqueue(new FavoritesCallback(uId, sId, callback));
    }

    private static class FavoritesCallback implements Callback<BookListResult> {
        private String uId;
        private String sId;
        private BookListCallback callback;
        public FavoritesCallback(String uId, String sId, BookListCallback callback) {
            this.uId = uId;
            this.sId = sId;
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<BookListResult> call, Response<BookListResult> response) {
            if(response.isSuccessful()) {
                BookListResult result = response.body();

                Log.i(TAG, "Found good list of shelved books");

                List<Book> books = new ArrayList<Book>();
                List<Item> items = result.getItems();
                if (items != null) {
                    for (Item item : items) {
                        VolumeInfo info = item.getVolumeInfo();
                        String title = info.getTitle();
                        StringBuilder authorsBuilder = new StringBuilder();
                        List<String> authorList = info.getAuthors();
                        String authors;
                        if (authorList != null && authorList.size() > 0) {
                            for (String author : info.getAuthors()) {
                                authorsBuilder.append(author);
                                authorsBuilder.append(", ");
                            }
                            authors = authorsBuilder.toString();
                        } else {
                            authors = "<none>";
                        }
                        ImageLinks imageLinks = info.getImageLinks();
                        String imageLink = (imageLinks != null) ? imageLinks.getThumbnail() : null;
                        if (imageLink != null) {
                            imageLink = imageLink.replace("edge=curl", "");
                        }

                        books.add(new Book(title, null, null, null,
                                authors, null, imageLink));
                    }
                }
                callback.call(null, books, null);
            } else {
                callback.call(null, Collections.emptyList(), new BooksException("Invalid favorites response"));
            }
        }

        @Override
        public void onFailure(Call<BookListResult> call, Throwable t) {
            callback.call(null, Collections.emptyList(),
                    new BooksException("Invalid favorites response: " + t.getMessage()));
        }
    }
}
