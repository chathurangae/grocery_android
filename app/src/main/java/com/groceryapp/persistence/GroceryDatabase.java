package com.groceryapp.persistence;


import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = GroceryDatabase.NAME, version = GroceryDatabase.VERSION)
public class GroceryDatabase {
    static final String NAME = "grocery";
    static final int VERSION = 1;
}
