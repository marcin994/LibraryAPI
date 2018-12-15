package com.library.libraryapi.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class BookItem {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String publishing;

    private int publishingYear;
    private int pages;

    @NotNull
    private boolean isDeleted;

    @NotNull
    private boolean isAvailable;

    @OneToOne
    private transient Book book;

    @OneToOne
    private File image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishing='" + publishing + '\'' +
                ", publishingYear=" + publishingYear +
                ", pages=" + pages +
                ", isDeleted=" + isDeleted +
                ", isAvailable=" + isAvailable +
                ", book=" + book +
                ", image=" + image +
                '}';
    }
}
