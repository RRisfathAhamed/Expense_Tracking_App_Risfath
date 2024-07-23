package com.example.esxpense_tracking_app_risfath;

public class Expense {

    private int id;
    private double amount;
    private String category;
    private String date;
    private String notes;

    public Expense(int id, double amount, String category, String date, String notes) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }
}
