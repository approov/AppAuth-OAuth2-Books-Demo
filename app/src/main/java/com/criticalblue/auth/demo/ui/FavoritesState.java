package com.criticalblue.auth.demo.ui;

import com.criticalblue.auth.demo.books.Book;

import java.util.Collections;
import java.util.List;

public class FavoritesState {
    private List<Book> books;

    public FavoritesState(List<Book> books) {
        this.books = books;
    }

    public FavoritesState(FavoritesState that) {
        if (that == null) { that = new FavoritesState(); }

        this.books = that.books;
    }

    public FavoritesState() {
        this(Collections.emptyList());
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
