package com.criticalblue.auth.demo.books;

import com.criticalblue.auth.demo.books.api.BookListResult;
import com.criticalblue.auth.demo.books.api.BookShelfResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BooksAPI {

    @GET("volumes/")
    Call<BookListResult> searchBooks(@Query("q") String q);

    @GET("mylibrary/bookshelves/{sid}")
    Call<BookShelfResult> getBookShelf(@Path("sid") String sid);

    @GET("users/{uid}/bookshelves/{sid}/volumes")
    Call<BookListResult> findShelvedBooks(@Path("uid") String uid, @Path("sid") String sid);
}
