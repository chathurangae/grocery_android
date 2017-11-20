package com.groceryapp.ui.trash;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.groceryapp.R;
import com.groceryapp.model.GroceryItem;
import com.groceryapp.model.ShoppingList;
import com.groceryapp.persistence.ItemDA;
import com.groceryapp.persistence.ShoppingListDA;
import com.groceryapp.ui.home.ShellActivity;
import com.groceryapp.ui.scanner.QrFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ItemDetails extends Fragment {

    public static final String ARG_CODE = "code";
    private ShellActivity shell;
    private String barCode;
    @BindView(R.id.item_name_field)
    EditText itemName;
    @BindView(R.id.quant_field)
    EditText quant;
    private List<ShoppingList> currentShoppingItem;
    private GroceryItem currentItem;

    public static ItemDetails newInstance(String code) {
        ItemDetails fragment = new ItemDetails();
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shell = (ShellActivity) activity;
        }
        shell.setToolbarTitle("Add Trash Item");
        barCode = getArguments().getString(ARG_CODE);
        getItem();
        return view;
    }

    private void getItem() {
        currentItem = new ItemDA().getItemsByCode(barCode);
        if (currentItem != null) {
            itemName.setText(currentItem.getItemName());
            itemName.setKeyListener(null);
            itemName.setFocusable(false);
            itemName.setClickable(false);
        }
    }

    @OnClick(R.id.add_button)
    void addButton() {
        String name = itemName.getText().toString().trim();
        String currentquant = quant.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            itemName.requestFocus();
            itemName.setError("Please enter Name");
        } else if (TextUtils.isEmpty(currentquant)) {
            quant.requestFocus();
            quant.setError("Please enter Quantity");
        } else {
            currentShoppingItem = new ArrayList<>();
            ShoppingList list = new ShoppingList(barCode, name, Integer.parseInt(currentquant));
            currentShoppingItem.add(list);
            shell.persistenceSingle(new ShoppingListDA().saveItems(currentShoppingItem))
                    .subscribe(
                            success -> {
                                shell.loadMainContainer(QrFragment.getInstance(2));
                            },
                            error -> {
                                shell.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                            }
                    );
        }
    }

    @OnClick(R.id.cancel_button)
    void cancelButton() {
        shell.loadMainContainer(QrFragment.getInstance(2));
    }

}
