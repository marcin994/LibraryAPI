package com.library.libraryapi.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Hire {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @ManyToOne
    @NotNull
    private Customer customer;

    @OneToOne
    @NotNull
    private BookItem book;

    @NotNull
    private Date hireDate;

    @NotNull
    private Date returnDate;

    @NotNull
    private int availableExtension;

    private double charge;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Hire{" +
                "id=" + id +
                ", customer=" + customer +
                ", book=" + book +
                ", hireDate=" + hireDate +
                ", returnDate=" + returnDate +
                ", availableExtension=" + availableExtension +
                ", charge=" + charge +
                '}';
    }
}
