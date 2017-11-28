package com.groceryapp.ui.histroy;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.groceryapp.R;
import com.groceryapp.model.ShoppingCart;
import com.groceryapp.persistence.CartDA;
import com.groceryapp.ui.home.ShellActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HistroyList extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener {

    private ShellActivity shellActivity;
    @BindView(R.id.item_recycler_view)
    RecyclerView recyclerItemList;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.item_container)
    SwipeRefreshLayout itemContainer;
    private HistroyAdapter listAdapter;
    private List<ShoppingCart> currentItemList;
    @BindView(R.id.item_date_field)
    EditText dateField;
    private com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog;


    public HistroyList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_histroy_list, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }
        shellActivity.setToolbarTitle("History List");
        this.itemContainer.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemList.setLayoutManager(linearLayoutManager);

        dateField.setKeyListener(null);
        dateField.setFocusable(false);
        dateField.setClickable(false);
        initPicker();
        getItemList();

        return view;
    }

    private void initPicker() {
        datePickerDialog =
                com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, 2017, 11, 25);
        datePickerDialog.setYearRange(2017, 2030);
        datePickerDialog.setVibrate(true);
        datePickerDialog.setCloseOnSingleTapDay(true);
    }

    @OnClick(R.id.done_button)
    void serchButton() {
        if (TextUtils.isEmpty(dateField.getText().toString().trim())) {
            shellActivity.showSnackBar("Please select Date", R.color.feed_tab_selected_background);
        } else {
            serchDate(dateField.getText().toString().trim());

        }
    }

    @OnClick(R.id.item_date_field)
    void openDatePicker() {
        datePickerDialog.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    private void getItemList() {
        shellActivity.persistenceSingle(new CartDA().getAllItems())
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

    private void onSuccess(List<ShoppingCart> itemList) {
        if (itemList.size() > 0) {
            prepareItem();
        }

    }

    private void prepareItem() {
        listAdapter = new HistroyAdapter(getContext(), currentItemList);
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

    private void serchDate(String serchdDate) {
        shellActivity.persistenceSingle(new CartDA().getItemsByDate(serchdDate))
                .subscribe(
                        items -> {
                            listAdapter.clear();
                            currentItemList = new ArrayList<>();
                            currentItemList.addAll(items);
                            onSuccess(items);
                        },
                        error -> {
                            shellActivity.showSnackBar(error.getMessage(), R.color.feed_tab_selected_background);
                        }
                );
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String serchdDate = (year + "-" + (month + 1) + "-" + day);
        dateField.setText(serchdDate);
        serchDate(serchdDate);

    }
}
