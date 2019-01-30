package com.library.libraryapi.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document
public class Customer {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String accountType;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean isDeleted;

    private Address address;

    private List<Hire> hireBooks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Hire> getHireBooks() {
        return hireBooks;
    }

    public void setHireBooks(List<Hire> hireBooks) {
        this.hireBooks = hireBooks;
    }

    public void addHire(Hire hire) {
        if (this.getHireBooks() == null) {
            this.setHireBooks(new ArrayList<>());
        }

        this.hireBooks.add(hire);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", accountType='" + accountType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                ", address=" + address +
                '}';
    }
}
