package com.groceryapp.ui.trash;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.model.ShoppingList;
import com.groceryapp.persistence.ShoppingListDA;
import com.groceryapp.ui.home.ShellActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TrashItList extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        TrashItemAdapter.OnItemDelete {
    private ShellActivity shellActivity;
    @BindView(R.id.item_recycler_view)
    RecyclerView recyclerItemList;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.item_container)
    SwipeRefreshLayout itemContainer;
    private TrashItemAdapter listAdapter;
    private List<ShoppingList> currentItemList;
    @BindView(R.id.item_name_field)
    EditText itemName;
    @BindView(R.id.item_quant_field)
    EditText quantity;
    private List<ShoppingList> currentShoppingItem;


    public TrashItList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trash_it, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }
        this.itemContainer.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemList.setLayoutManager(linearLayoutManager);
        getItemList();

        return view;
    }

    private void getItemList() {
        shellActivity.persistenceSingle(new ShoppingListDA().getAllItems())
                .subscribe(
                        items -> {
                            currentItemList = new ArrayList<>();
                            currentItemList.addAll(items);
                            onSuccess(items);
                        },
                        error -> {
                            shellActivity.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                        }
                );


    }

    private void onSuccess(List<ShoppingList> itemList) {
        if (itemList.size() > 0) {
            prepareItem();
        }

    }

    private void prepareItem() {
        listAdapter = new TrashItemAdapter(getContext(), currentItemList, this);
        recyclerItemList.setAdapter(listAdapter);
        resetValues();
        itemContainer.setRefreshing(false);

    }

    private void resetValues() {
        itemCount.setText(currentItemList.size() + " items");
    }

    @OnClick(R.id.done_button)
    void addNewItem() {
        String name = itemName.getText().toString().trim();
        String currentquant = quantity.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            itemName.requestFocus();
            itemName.setError("Please enter Name");
        } else if (TextUtils.isEmpty(currentquant)) {
            quantity.requestFocus();
            quantity.setError("Please enter Quantity");
        } else {
            currentShoppingItem = new ArrayList<>();
            ShoppingList list = new ShoppingList("0000000", name, Integer.parseInt(currentquant));
            currentShoppingItem.add(list);
            shellActivity.persistenceSingle(new ShoppingListDA().saveItems(currentShoppingItem))
                    .subscribe(
                            success -> {
                                onRefresh();
                            },
                            error -> {
                                shellActivity.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                            }
                    );
        }
    }


    @Override
    public void onRefresh() {
        listAdapter.clear();
        getItemList();
    }

    @Override
    public void onItemDelete(String barCode) {
        shellActivity.persistenceSingle(new ShoppingListDA().deleteItem(barCode))
                .subscribe(
                        success -> {
                            getItemList();
                        },
                        error -> {
                            shellActivity.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                        }
                );
    }
}
