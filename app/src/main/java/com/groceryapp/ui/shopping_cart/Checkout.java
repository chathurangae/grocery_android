package com.groceryapp.ui.shopping_cart;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.helpers.DateFormatter;
import com.groceryapp.model.ShoppingCart;
import com.groceryapp.persistence.CartDA;
import com.groceryapp.persistence.UserItemDA;
import com.groceryapp.ui.home.HomeFragment;
import com.groceryapp.ui.home.ShellActivity;
import com.groceryapp.ui.scanner.QrFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Checkout extends Fragment implements
        com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener {
    public static final String ARG_CODE = "total";
    private ShellActivity shell;
    String total;
    @BindView(R.id.invoice_no)
    TextView invoice;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.total_field)
    EditText totalField;
    @BindView(R.id.card_no_field)
    EditText cardNo;
    @BindView(R.id.date_field)
    EditText dateField;
    @BindView(R.id.pin_field)
    EditText pin;
    private com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog;

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

        dateField.setKeyListener(null);
        dateField.setFocusable(false);
        dateField.setClickable(false);


        total = getArguments().getString(ARG_CODE);

        totalField.setText(total);
        date.setText(DateFormatter.getCurrentDate());
        String random = getRandomString(5);
        invoice.setText("I" + random);
        initPicker();
        return view;

    }

    private void initPicker() {
        datePickerDialog =
                com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, 2017, 11, 0);
        datePickerDialog.setYearRange(2017, 2030);
        datePickerDialog.setVibrate(true);
        datePickerDialog.setCloseOnSingleTapDay(true);
    }

    @OnClick(R.id.date_field)
    void showPicker() {
        datePickerDialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }


    public String getRandomString(int length) {
        final String characters = "1234567890";
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
        if (TextUtils.isEmpty(cardNo.getText().toString().trim())) {
            cardNo.requestFocus();
            cardNo.setError("Please Add valid card");
        } else if (cardNo.getText().toString().trim().length() < 15) {
            cardNo.requestFocus();
            cardNo.setError("Invalid card Number");
        } else if (TextUtils.isEmpty(dateField.getText().toString().trim())) {
            dateField.requestFocus();
            dateField.setError("Please enter Exp. Date");
        } else if (TextUtils.isEmpty(pin.getText().toString().trim())) {
            pin.requestFocus();
            pin.setError("Please enter CVV");
        } else if (pin.getText().toString().trim().length() < 3) {
            pin.requestFocus();
            pin.setError("Invalid CVV");
        } else {
            List<ShoppingCart> cartList = new ArrayList<>();
            ShoppingCart cart = new ShoppingCart(invoice.getText().toString(),
                    date.getText().toString(), Double.parseDouble(total));
            cartList.add(cart);

            shell.persistenceSingle(new CartDA().saveItems(cartList))
                    .subscribe(
                            success -> {
                                deleteAllItems();
                            },
                            error -> shell.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background)
                    );

        }
    }

    private void deleteAllItems() {
        shell.persistenceSingle(new UserItemDA().deleteAllItem())
                .subscribe(
                        success -> {
                            shell.loadMainContainer(new HomeFragment());
                            shell.showSnackBar("Successful Checkout", R.color.feed_complete_dot);
                        },
                        error -> shell.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background)
                );
    }


    @OnClick(R.id.cancel_button)
    void cancel() {
        shell.loadMainContainer(QrFragment.getInstance(1));
    }


    @Override
    public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {
        dateField.setText((month + 1) + "/" + year);
    }
}
