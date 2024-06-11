package com.example.expensetrackingapp;

import java.io.Serializable;

public class IncomeExpenseModel implements Serializable {
    String amountId, title, amount, details, dateTime, type;

    public IncomeExpenseModel() {
    }

    public IncomeExpenseModel(String title, String amount, String details, String dateTime, String type) {
        this.title = title;
        this.amount = amount;
        this.details = details;
        this.dateTime = dateTime;
        this.type = type;
    }

    public IncomeExpenseModel(String amountId, String title, String amount, String details, String dateTime, String type) {
        this.amountId = amountId;
        this.title = title;
        this.amount = amount;
        this.details = details;
        this.dateTime = dateTime;
        this.type = type;
    }

    public String getAmountId() {
        return amountId;
    }

    public void setAmountId(String amountId) {
        this.amountId = amountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
