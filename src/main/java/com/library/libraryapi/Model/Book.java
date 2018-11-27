package com.library.libraryapi.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String category;

    @NotNull
    private int itemNumber;

    @NotNull
    private boolean isDeleted;

    @Null
    private long image;

    @OneToMany
    private List<BookItem> items;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BookItem> getItems() {
        return items;
    }

    public void setItems(List<BookItem> items) {
        this.items = items;
    }

    public long getImage() {
        return image;
    }

    public void setImage(long image) {
        this.image = image;
    }

    public void addBookItem(BookItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        this.items.add(item);
        this.setItemNumber(this.getItems().size());
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", itemNumber=" + itemNumber +
                ", isDeleted=" + isDeleted +
                ", image=" + image +
                ", items=" + items +
                ", description='" + description + '\'' +
                '}';
    }
}

