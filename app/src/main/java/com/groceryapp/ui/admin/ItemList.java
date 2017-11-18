package com.groceryapp.ui.admin;


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
import com.groceryapp.persistence.ItemDA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemList extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        ItemListAdapter.OnItemDelete {
    private AdminHome admin;
    @BindView(R.id.item_recycler_view)
    RecyclerView recyclerItemList;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.item_container)
    SwipeRefreshLayout itemContainer;
    private ItemListAdapter listAdapter;
    List<GroceryItem> currentItemList;
    public static final String ARG_TYPE = "type";
    private int itemType;


    public static ItemList newInstance(int type) {
        ItemList fragment = new ItemList();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof AdminHome) {
            admin = (AdminHome) activity;
        }
        itemType = getArguments().getInt(ARG_TYPE);
        this.itemContainer.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemList.setLayoutManager(linearLayoutManager);
        getItemList();
        return view;
    }

    private void getItemList() {
        admin.persistenceSingle(new ItemDA().getAllItems())
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

    private void onSuccess(List<GroceryItem> itemList) {
        if (itemList.size() > 0) {
            prepareItem();
        }

    }

    private void prepareItem() {
        listAdapter = new ItemListAdapter(getContext(), currentItemList, this, itemType);
        recyclerItemList.setAdapter(listAdapter);
        resetValues();
        itemContainer.setRefreshing(false);

    }

    private void resetValues() {
        itemCount.setText(currentItemList.size() + " items");
    }

    @Override
    public void onRefresh() {
        listAdapter.clear();
        getItemList();
    }

    @Override
    public void onItemDelete(String barcode) {
        admin.loadFragment(barcode,itemType);

    }
}
