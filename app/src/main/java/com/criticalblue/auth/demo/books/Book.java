package com.criticalblue.auth.demo.books;

public class Book {
    private String title;
    private String subtitle;
    private String publisher;
    private String publicationDate;
    private String authors;
    private String description;
    private String imageLink;

    public Book(String title, String subtitle, String publisher, String publicationDate,
                String authors, String description, String imageLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.authors = authors;
        this.description = description;
        this.imageLink = imageLink;
    }

    public Book(String title) {
        this(title, null, null, null, null, null, null);
    }

    public Book(String title, String imageLink) {
        this(title, null, null, null, null, null, imageLink);
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }
}
