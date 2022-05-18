package be.pxl.auctions.rest.resource;

import be.pxl.auctions.model.User;

import java.time.LocalDate;

public class BidCreateResource {
    private String email;
    private double amount;
    private User user;
    private LocalDate date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
