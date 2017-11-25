package com.groceryapp.persistence;


import com.groceryapp.model.UserItem;
import com.groceryapp.model.UserItem_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
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
                        .onSuccess("Successful")).build().execute());
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

    public UserItem getItemsByCode(String code) {
        return SQLite.select().from(UserItem.class).where(UserItem_Table
                .barCodeId.eq(code)).querySingle();
    }

    public Single<String> updateItem(UserItem currentItem) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        (ProcessModelTransaction.ProcessModel<BaseModel>) (model,
                                                                           databaseWrapper) ->
                                model.update()).addAll(currentItem).build()).error((transaction,
                                                                                    error) ->
                        singleSubscriber.onError(new Exception("Error"))).success(transaction ->
                        singleSubscriber.onSuccess("Successful")).build().execute());
    }

    public Single<String> deleteItem(String code) {
        return Single.create(singleSubscriber -> {
            try {
                SQLite.delete().from(UserItem.class)
                        .where(UserItem_Table.barCodeId.in(code)
                        ).async().execute();
                singleSubscriber.onSuccess("Successful");


            } catch (Exception e) {
                singleSubscriber.onError(e);
            }
        });
    }
}
