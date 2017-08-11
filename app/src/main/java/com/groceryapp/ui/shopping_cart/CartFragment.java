package com.groceryapp.ui.shopping_cart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.groceryapp.R;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "BarCode", "Test1","Test2"    };
    private View view;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

    private MyPagerAdapter serviceTabsPagerAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        mFragments.add(QrFragment.getInstance());
        //mFragments.add(.getInstance());
        //mFragments.add(QrFragment.getInstance());
        viewPager = (ViewPager) v.findViewById(R.id.pager_genaral);
        serviceTabsPagerAdapter =new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(serviceTabsPagerAdapter);

        tabLayout = (SlidingTabLayout) v.findViewById(R.id.tab_layout_genaral);
        tabLayout.setViewPager(viewPager,mTitles,getActivity(),mFragments);
        return v;
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
