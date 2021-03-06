package com.groceryapp.model;


import com.groceryapp.persistence.GroceryDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = GroceryDatabase.class)
public class GroceryItem extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private Integer id;

    @Column
    private String barCodeId;

    @Column
    private String itemName;

    @Column
    private double price;

    @Column
    private int discountRate;

    public GroceryItem() {

    }

    public GroceryItem(String barCodeId, String itemName, double price, int discountRate) {
        this.barCodeId = barCodeId;
        this.itemName = itemName;
        this.price = price;
        this.discountRate = discountRate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarCodeId() {
        return barCodeId;
    }

    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }
}
