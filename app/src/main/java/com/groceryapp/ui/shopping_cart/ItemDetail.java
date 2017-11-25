package com.groceryapp.ui.shopping_cart;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.helpers.DateFormatter;
import com.groceryapp.model.GroceryItem;
import com.groceryapp.model.UserItem;
import com.groceryapp.persistence.ItemDA;
import com.groceryapp.persistence.UserItemDA;

import com.groceryapp.ui.home.ShellActivity;
import com.groceryapp.ui.scanner.QrFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @BindView(R.id.discount_text)
    TextView discountText;
    private GroceryItem currentItem;
    private String barCode;
    double discountPrice;
    List<UserItem> currrentList;
    int currentQuant;


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
        currrentList = new ArrayList<>();
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
        discountPrice = 0.0;
        getItem();

        quant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (!text.isEmpty()) {
                    int quant = Integer.parseInt(charSequence.toString());
                    checkPrice(quant);
                } else {
                    total.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        return view;

    }

    @OnClick(R.id.add_button)
    void buttonAdd() {

        checkItemIsExists();
    }

    private void checkItemIsExists() {
        UserItem currentCartItem = new UserItemDA().getItemsByCode(barCode);
        if (currentCartItem != null) {
            currentCartItem.setPrice(Double.parseDouble(total.getText().toString()));
            currentCartItem.setQuantity((currentCartItem.getQuantity()+currentQuant));
            shell.persistenceSingle(new UserItemDA().updateItem(currentCartItem))
                    .subscribe(
                            success -> shell.loadMainContainer(QrFragment.getInstance(1)),
                            error -> shell.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background)
                    );
        } else {
            currrentList = new ArrayList<>();
            UserItem item = new UserItem(barCode, itemName.getText().toString(),
                    Double.parseDouble(total.getText().toString())
                    , currentQuant, DateFormatter.getCurrentDate());
            currrentList.add(item);
            shell.persistenceSingle(new UserItemDA().saveItems(currrentList))
                    .subscribe(
                            success -> shell.loadMainContainer(QrFragment.getInstance(1)),
                            error -> shell.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background));
        }
    }

    @OnClick(R.id.cancel_button)
    void cancelButton() {
        shell.loadMainContainer(QrFragment.getInstance(1));
    }

    void checkPrice(int quant) {
        currentQuant = quant;
        if (currentItem.getDiscountRate() == 0) {
            Double currentPrice = (currentItem.getPrice() * quant);
            total.setText(
                    currentPrice.toString());

        } else {
            Double currentPrice = (discountPrice * quant);
            total.setText(currentPrice.toString());
        }
    }


    private void getItem() {
        currentItem = new ItemDA().getItemsByCode(barCode);
        if (currentItem != null) {
            itemName.setText(currentItem.getItemName());
            if (currentItem.getDiscountRate() == 0) {
                discountText.setVisibility(View.GONE);
                price.setText(String.valueOf(currentItem.getPrice()));
            } else {
                discountText.setVisibility(View.VISIBLE);
                int discount = currentItem.getDiscountRate();
                Double currentPrice = currentItem.getPrice();
                discountPrice = currentPrice - ((currentPrice * discount) / 100);
                price.setText(String.valueOf(discountPrice));
                discountText.setText(" including " + String.valueOf(discount) + "% discount");
            }

            quant.setText("1");
            checkPrice(1);
        } else {
            shell.showSnackBar("No Item Found", R.color.feed_tab_selected_background);
        }


    }

}
