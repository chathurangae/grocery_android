package com.groceryapp.ui.admin;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.helpers.PreferenceManager;
import com.groceryapp.helpers.ValidationHelper;
import com.groceryapp.model.GroceryItem;
import com.groceryapp.persistence.ItemDA;
import com.groceryapp.persistence.LoginDA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddItem extends Fragment {
    public static final String ARG_CODE = "code";
    public static final String ARG_TYPE = "type";
    @BindView(R.id.bar_code_field)
    EditText barCodeField;
    @BindView(R.id.item_name_field)
    EditText itemNameField;
    @BindView(R.id.price_field)
    EditText priceField;
    @BindView(R.id.discount_field)
    EditText discountField;
    @BindView(R.id.add_button)
    Button button;
    @BindView(R.id.text_heading)
    TextView heading;
    List<GroceryItem> itemList;
    private AdminHome admin;
    private String barCode, itemName, price, dicount;
    private int itemtype;
    private GroceryItem currentItem;


    public static AddItem newInstance(String code, int type) {
        AddItem fragment = new AddItem();
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.add_button)
    void addItem() {
        itemName = itemNameField.getText().toString().trim();
        price = priceField.getText().toString().trim();
        dicount = discountField.getText().toString().trim();
        if (TextUtils.isEmpty(itemName)) {
            itemNameField.requestFocus();
            itemNameField.setError("Required Field");
        } else if (TextUtils.isEmpty(price)) {
            priceField.requestFocus();
            priceField.setError("Required Field");
        } else if (!dicount.equals("")) {
            if (Integer.parseInt(dicount) > 90) {
                discountField.requestFocus();
                discountField.setError("Discount Less than 90%");
            }
        } else {
            ItemOperation();

        }
    }

    private void ItemOperation() {
        if (itemtype == 0) {
            if (dicount.equals("")) {
                dicount = "0";
            }
            GroceryItem item = new GroceryItem(barCode, itemName, Double.parseDouble(price)
                    , Integer.parseInt(dicount));
            itemList.add(item);

            admin.persistenceSingle(new ItemDA().saveItems(itemList))
                    .subscribe(
                            success -> {
                                admin.loadQRFragment();
                            },
                            error -> {
                                admin.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                            });
        } else if (itemtype == 1) {
            if (dicount.equals("")) {
                dicount = "0";
            }
            currentItem.setDiscountRate(Integer.parseInt(dicount));
            currentItem.setItemName(itemName);
            currentItem.setPrice(Double.parseDouble(price));
            admin.persistenceSingle(new ItemDA().updateItem(currentItem))
                    .subscribe(
                            success -> {
                                admin.showSnackBar("Successfully Update", R.color.feed_complete_dot);

                            },
                            error -> {
                                admin.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                            }
                    );

        } else {
            admin.persistenceSingle(new ItemDA().deleteItem(barCode))
                    .subscribe(
                            success -> {
                                admin.showSnackBar("Successfully Deleted", R.color.feed_complete_dot);
                            },
                            error -> {
                                admin.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                            }
                    );
        }
    }

    private void getItem() {
        if (itemtype != 0) {
            currentItem = new ItemDA().getItemsByCode(barCode);
            itemNameField.setText(currentItem.getItemName());
            priceField.setText(String.valueOf(currentItem.getPrice()));
            discountField.setText(String.valueOf(currentItem.getDiscountRate()));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        ButterKnife.bind(this, view);
        itemList = new ArrayList<>();
        Activity activity = getActivity();
        if (activity instanceof AdminHome) {
            admin = (AdminHome) activity;
        }
        itemtype = getArguments().getInt(ARG_TYPE);
        barCodeField.setKeyListener(null);
        barCodeField.setFocusable(false);
        barCodeField.setClickable(false);
        barCode = getArguments().getString(ARG_CODE);
        barCodeField.setText(barCode);
        if (itemtype == 2) {
            itemNameField.setKeyListener(null);
            itemNameField.setFocusable(false);
            itemNameField.setClickable(false);
            priceField.setKeyListener(null);
            priceField.setFocusable(false);
            priceField.setClickable(false);
            discountField.setKeyListener(null);
            discountField.setFocusable(false);
            discountField.setClickable(false);
            button.setText("Delete");
            heading.setText("Delete Item");
        }
        if (itemtype == 1) {
            button.setText("Update");
            heading.setText("Update Item");
        }
        getItem();
        return view;
    }


}
