package com.example.demo.model;

import java.sql.Date;

public class Invoice {

    private int id;
    private int store_id;
    private Type operation;
    private Date date;

    public Invoice() {
    }

    public Invoice(int id, int store_id, Type operation, Date date) {
        this.id = id;
        this.store_id = store_id;
        this.operation = operation;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public Type getOperation() {
        return operation;
    }

    public void setOperation(Type operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
