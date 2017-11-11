package com.groceryapp.ui.admin;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groceryapp.R;
import com.groceryapp.ui.shopping_cart.QrFragment;
import com.groceryapp.ui.trash.ItemDetails;
import com.groceryapp.ui.trash.Scanner;
import com.groceryapp.ui.trash.TrashIt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddItem extends Fragment {


    public static AddItem newInstance() {
        AddItem fragment = new AddItem();
        return fragment;
    }


    public AddItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        //ButterKnife.bind(this, view);
        return view;
    }





}
