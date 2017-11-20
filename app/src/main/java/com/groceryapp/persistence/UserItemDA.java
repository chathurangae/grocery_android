package com.groceryapp.persistence;


import com.groceryapp.model.GroceryItem;
import com.groceryapp.model.UserItem;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

import io.reactivex.Single;

public class UserItemDA {

    public Single<String> saveItems(List<UserItem> itemsList) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction
                        .Builder<>((ProcessModelTransaction
                        .ProcessModel<UserItem>) (citation, databaseWrapper) ->
                        citation.save()).addAll(itemsList).build())
                .error((transaction, error) -> singleSubscriber
                        .onError(new Exception(error.toString())))
                .success(transaction -> singleSubscriber
                        .onSuccess("success")).build().execute());
    }

    public Single<List<UserItem>> getAllItems() {
        return Single.create(singleSubscriber -> {
            try {
                List<UserItem> result = SQLite.select().from(UserItem.class).queryList();
                singleSubscriber.onSuccess(result);
            } catch (Exception exception) {
                singleSubscriber.onError(exception);
            }
        });
    }
}
