package com.groceryapp.model;


import com.groceryapp.persistence.GroceryDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = GroceryDatabase.class)
public class ShoppingList extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private Integer id;

    @Column
    private String barCodeId;

    @Column
    private String itemName;

    @Column
    private int quantity;

    public ShoppingList() {

    }

    public ShoppingList(String barCodeId, String itemName, int quantity) {
        this.barCodeId = barCodeId;
        this.itemName = itemName;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
