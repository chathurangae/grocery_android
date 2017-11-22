package com.groceryapp.persistence;


import com.groceryapp.model.ShoppingList;
import com.groceryapp.model.ShoppingList_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

import io.reactivex.Single;

public class ShoppingListDA {

    public Single<String> saveItems(List<ShoppingList> itemsList) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction
                        .Builder<>((ProcessModelTransaction
                        .ProcessModel<ShoppingList>) (citation, databaseWrapper) ->
                        citation.save()).addAll(itemsList).build())
                .error((transaction, error) -> singleSubscriber
                        .onError(new Exception(error.toString())))
                .success(transaction -> singleSubscriber
                        .onSuccess("success")).build().execute());
    }

    public Single<String> deleteItem(String code) {
        return Single.create(singleSubscriber -> {
            try {
                SQLite.delete().from(ShoppingList.class)
                        .where(ShoppingList_Table.barCodeId.in(code)
                        ).async().execute();
                singleSubscriber.onSuccess("Success");


            } catch (Exception e) {
                singleSubscriber.onError(e);
            }
        });
    }

    public ShoppingList getItemsByCode(String code) {
        return SQLite.select().from(ShoppingList.class).where(ShoppingList_Table
                .barCodeId.eq(code)).querySingle();
    }

    public Single<String> updateItem(ShoppingList currentItem) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        (ProcessModelTransaction.ProcessModel<BaseModel>) (model,
                                                                           databaseWrapper) ->
                                model.update()).addAll(currentItem).build()).error((transaction,
                                                                                    error) ->
                        singleSubscriber.onError(new Exception("Error"))).success(transaction ->
                        singleSubscriber.onSuccess("success")).build().execute());
    }

    public Single<List<ShoppingList>> getAllItems() {
        return Single.create(singleSubscriber -> {
            try {
                List<ShoppingList> result = SQLite.select().from(ShoppingList.class).queryList();
                singleSubscriber.onSuccess(result);
            } catch (Exception exception) {
                singleSubscriber.onError(exception);
            }
        });
    }
}
