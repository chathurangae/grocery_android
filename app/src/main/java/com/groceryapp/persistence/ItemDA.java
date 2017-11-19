package com.groceryapp.persistence;


import com.groceryapp.model.GroceryItem;
import com.groceryapp.model.GroceryItem_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

import io.reactivex.Single;

public class ItemDA {
    public Single<String> saveItems(List<GroceryItem> itemsList) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction
                        .Builder<>((ProcessModelTransaction
                        .ProcessModel<GroceryItem>) (citation, databaseWrapper) ->
                        citation.save()).addAll(itemsList).build())
                .error((transaction, error) -> singleSubscriber
                        .onError(new Exception(error.toString())))
                .success(transaction -> singleSubscriber
                        .onSuccess("success")).build().execute());
    }

    public Single<List<GroceryItem>> getAllItems() {
        return Single.create(singleSubscriber -> {
            try {
                List<GroceryItem> result = SQLite.select().from(GroceryItem.class).queryList();
                singleSubscriber.onSuccess(result);
            } catch (Exception exception) {
                singleSubscriber.onError(exception);
            }
        });
    }

    public GroceryItem getItemsByCode(String code) {
        return SQLite.select().from(GroceryItem.class).where(GroceryItem_Table
                .barCodeId.eq(code)).querySingle();
    }

    public Single<String> updateItem(GroceryItem currentItem) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        (ProcessModelTransaction.ProcessModel<BaseModel>) (model,
                                                                           databaseWrapper) ->
                                model.update()).addAll(currentItem).build()).error((transaction,
                                                                                    error) ->
                        singleSubscriber.onError(new Exception("Error"))).success(transaction ->
                        singleSubscriber.onSuccess("success")).build().execute());
    }

    public Single<String> deleteItem(String code) {
        return Single.create(singleSubscriber -> {
            try {
                SQLite.delete().from(GroceryItem.class)
                        .where(GroceryItem_Table.barCodeId.in(code)
                        ).async().execute();
                singleSubscriber.onSuccess("Success");


            } catch (Exception e) {
                singleSubscriber.onError(e);
            }
        });
    }

}
