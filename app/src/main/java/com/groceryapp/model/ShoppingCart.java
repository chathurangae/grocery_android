package com.groceryapp.model;

import com.groceryapp.persistence.GroceryDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = GroceryDatabase.class)
public class ShoppingCart extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private Integer id;

    @Column
    private String invoiceCode;

    @Column
    private String date;

    @Column
    private double price;

    public ShoppingCart() {

    }

    public ShoppingCart(String invoiceCode, String date, double price) {
        this.invoiceCode = invoiceCode;
        this.date = date;
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
