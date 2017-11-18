package com.groceryapp.ui.shopping_cart;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.groceryapp.R;
import com.groceryapp.model.GroceryItem;
import com.groceryapp.persistence.ItemDA;
import com.groceryapp.ui.admin.ItemList;
import com.groceryapp.ui.home.ShellActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemDetail extends Fragment {
    public static final String ARG_CODE = "code";
    private ShellActivity shell;
    @BindView(R.id.item_name_field)
    EditText itemName;
    @BindView(R.id.price_field)
    EditText price;
    @BindView(R.id.quant_field)
    EditText quant;
    @BindView(R.id.total_field)
    EditText total;
    private GroceryItem currentItem;
    private String barCode;


    public static ItemDetail newInstance(String code) {
        ItemDetail fragment = new ItemDetail();
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shell = (ShellActivity) activity;
        }
        shell.setToolbarTitle("Add Item");
        barCode = getArguments().getString(ARG_CODE);
        price.setKeyListener(null);
        price.setFocusable(false);
        price.setClickable(false);
        total.setKeyListener(null);
        total.setFocusable(false);
        total.setClickable(false);
        itemName.setKeyListener(null);
        itemName.setFocusable(false);
        itemName.setClickable(false);
        getItem();
        return view;
    }

    private void getItem() {
        currentItem = new ItemDA().getItemsByCode(barCode);
        itemName.setText(currentItem.getItemName());
        if (currentItem.getDiscountRate() == 0) {
            price.setText(String.valueOf(currentItem.getPrice()));
        } else {
            int discount = currentItem.getDiscountRate();
            Double cprice = currentItem.getPrice();
            double discountPrice = cprice - ((cprice * discount) / 100);
            price.setText(String.valueOf(discountPrice) + " including " + String.valueOf(discount) + "% discount");
        }


    }

}
