package com.groceryapp.ui.shopping_cart;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.helpers.DateFormatter;
import com.groceryapp.model.ShoppingCart;
import com.groceryapp.persistence.CartDA;
import com.groceryapp.ui.home.ShellActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Checkout extends Fragment {
    public static final String ARG_CODE = "total";
    private ShellActivity shell;
    String total;
    @BindView(R.id.invoice_no)
    TextView invoice;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.total_field)
    EditText totalField;

    public static Checkout newInstance(String total) {
        Checkout fragment = new Checkout();
        Bundle args = new Bundle();
        args.putString(ARG_CODE, total);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shell = (ShellActivity) activity;
        }
        shell.setToolbarTitle("Checkout");
        totalField.setKeyListener(null);
        totalField.setFocusable(false);
        totalField.setClickable(false);


        total = getArguments().getString(ARG_CODE);

        totalField.setText(total);
        date.setText(DateFormatter.getCurrentDate());
        String random = getRandomString(5);
        invoice.setText(random);

        return view;

    }

    public String getRandomString(int length) {
        final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
        StringBuilder result = new StringBuilder();
        while (length > 0) {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            length--;
        }
        return result.toString();
    }

    @OnClick(R.id.confirm_button)
    void confirm() {
        List<ShoppingCart> cartList = new ArrayList<>();
        ShoppingCart cart = new ShoppingCart(invoice.getText().toString(),
                date.getText().toString(), Double.parseDouble(total));
        cartList.add(cart);

        shell.persistenceSingle(new CartDA().saveItems(cartList))
                .subscribe(
                        success -> {
                            shell.showSnackBar("Success", R.color.feed_complete_dot);
                        },
                        error -> {
                            shell.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                        }
                );

    }

}
