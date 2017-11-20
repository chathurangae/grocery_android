package com.groceryapp.persistence;


import com.groceryapp.model.GroceryItem;
import com.groceryapp.model.ShoppingCart;
import com.groceryapp.model.ShoppingCart_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

import io.reactivex.Single;

public class CartDA {

    public Single<String> saveItems(List<ShoppingCart> itemsList) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction
                        .Builder<>((ProcessModelTransaction
                        .ProcessModel<ShoppingCart>) (citation, databaseWrapper) ->
                        citation.save()).addAll(itemsList).build())
                .error((transaction, error) -> singleSubscriber
                        .onError(new Exception(error.toString())))
                .success(transaction -> singleSubscriber
                        .onSuccess("success")).build().execute());
    }

    public Single<List<ShoppingCart>> getItemsByDate(String date) {
        return Single.create(singleSubscriber -> {
            try {
                List<ShoppingCart> item = SQLite.select().from(ShoppingCart.class).where(ShoppingCart_Table
                        .date.eq(date)).queryList();
                singleSubscriber.onSuccess(item);
            } catch (Exception exception) {
                singleSubscriber.onError(exception);
            }
        });
    }

    public Single<List<ShoppingCart>> getAllItems() {
        return Single.create(singleSubscriber -> {
            try {
                List<ShoppingCart> result = SQLite.select().from(ShoppingCart.class).queryList();
                singleSubscriber.onSuccess(result);
            } catch (Exception exception) {
                singleSubscriber.onError(exception);
            }
        });
    }
}
