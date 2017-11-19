package com.groceryapp.ui.shopping_cart;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.model.GroceryItem;
import com.groceryapp.model.UserItem;
import com.groceryapp.persistence.ItemDA;
import com.groceryapp.persistence.UserItemDA;
import com.groceryapp.ui.admin.ItemListAdapter;
import com.groceryapp.ui.home.ShellActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserItemList extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        UserItemAdapter.OnItemDelete {

    private ShellActivity shellActivity;
    @BindView(R.id.item_recycler_view)
    RecyclerView recyclerItemList;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.item_container)
    SwipeRefreshLayout itemContainer;
    @BindView(R.id.total_price)
    TextView totalPrice;
    private UserItemAdapter listAdapter;
    private List<UserItem> currentItemList;


    public UserItemList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_item_list, container, false);
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
        shellActivity.persistenceSingle(new UserItemDA().getAllItems())
                .subscribe(
                        items -> {
                            currentItemList = new ArrayList<>();
                            currentItemList.addAll(items);
                            onSuccess(items);
                        },
                        error -> {

                        }
                );


    }

    private void onSuccess(List<UserItem> itemList) {
        if (itemList.size() > 0) {
            prepareItem();
        }

    }

    private void prepareItem() {
        listAdapter = new UserItemAdapter(getContext(), currentItemList, this);
        recyclerItemList.setAdapter(listAdapter);
        resetValues();
        itemContainer.setRefreshing(false);

    }

    private void resetValues() {
        itemCount.setText(currentItemList.size() + " items");
        Double sum = 0.0;
        for (int i = 0; i < currentItemList.size(); i++) {
            sum = sum + currentItemList.get(i).getPrice();
        }
        totalPrice.setText(String.valueOf(sum) + " Rs");
    }

    @Override
    public void onRefresh() {
        listAdapter.clear();
        getItemList();
    }

    @Override
    public void onItemDelete(String barCode) {

    }
}
