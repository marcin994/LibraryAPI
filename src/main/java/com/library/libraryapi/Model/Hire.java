package com.library.libraryapi.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document
public class Hire {

    @Id
    @NotNull
    private String id;

    @NotNull
    private Customer customer;

    @NotNull
    private BookItem book;

    @NotNull
    private Date hireDate;

    @NotNull
    private Date returnDate;

    private Date lastModifyDate;

    @NotNull
    private int availableExtension;

    private double charge;

    private boolean isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BookItem getBook() {
        return book;
    }

    public void setBook(BookItem book) {
        this.book = book;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getAvailableExtension() {
        return availableExtension;
    }

    public void setAvailableExtension(int availableExtension) {
        this.availableExtension = availableExtension;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Hire{" +
                "id=" + id +
                ", book=" + book +
                ", hireDate=" + hireDate +
                ", returnDate=" + returnDate +
                ", availableExtension=" + availableExtension +
                ", charge=" + charge +
                '}';
    }
}
