package com.groceryapp.persistence;


import com.groceryapp.model.User;
import com.groceryapp.model.User_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import io.reactivex.Single;

public class LoginDA {

    public Single<String> saveUser(User currentUser) {
        return Single.create(singleSubscriber -> {
            try {
                FlowManager.getModelAdapter(User.class).save(currentUser);
                //currentUser.save();
                singleSubscriber.onSuccess("sucess");
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
}
