package com.groceryapp.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.groceryapp.R;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    public <T> Single<T> persistenceSingle(Single<T> observable) {
        Single<T> returnSingle = observable.subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread());
        returnSingle.subscribe(t -> {
        }, error -> {
        });
        return returnSingle;
    }


}
