package com.groceryapp.persistence;


import com.groceryapp.model.User;
import com.groceryapp.model.User_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import io.reactivex.Single;

public class LoginDA {

    public Single<String> saveUser(User currentUser) {
        return Single.create(singleSubscriber -> {
            try {
                FlowManager.getModelAdapter(User.class).save(currentUser);
                singleSubscriber.onSuccess("Successful");
            } catch (Exception error) {
                singleSubscriber.onError(error);
            }
        });
    }

    public int checkUser(String email, int pin) {
        double count = SQLite.selectCountOf().from(User.class)
                .where(User_Table.email.eq(email))
                .and(User_Table.pin.eq(pin)).count();
        return (int) count;
    }

    public User getUserByNIC(String nic) {
        return SQLite.select().from(User.class).where(User_Table
                .nic.eq(nic)).querySingle();
    }

    public Single<String> updateUser(User currentItem) {
        return Single.create(singleSubscriber -> FlowManager.getDatabase(GroceryDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        (ProcessModelTransaction.ProcessModel<BaseModel>) (model,
                                                                           databaseWrapper) ->
                                model.update()).addAll(currentItem).build()).error((transaction,
                                                                                    error) ->
                        singleSubscriber.onError(new Exception("Error"))).success(transaction ->
                        singleSubscriber.onSuccess("Successful")).build().execute());
    }
}
