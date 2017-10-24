package com.criticalblue.auth.demo.ui;

import com.criticalblue.auth.demo.books.Book;

import java.util.Collections;
import java.util.List;

public class SearchState {
    private String query;
    private List<Book> books;

    public SearchState(String query, List<Book> books) {
        this.query = query;
        this.books = books;
    }

    public SearchState(SearchState that) {
        if (that == null) { that = new SearchState(); }

        this.query = that.query;
        this.books = that.books;
    }

    public SearchState() {
        this("", Collections.emptyList());
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
