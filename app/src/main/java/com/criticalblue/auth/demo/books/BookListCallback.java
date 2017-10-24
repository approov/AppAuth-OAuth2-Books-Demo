package com.criticalblue.auth.demo.books;

import java.util.List;

public interface BookListCallback {
    void call(String query, List<Book> books, BooksException ex);
}
