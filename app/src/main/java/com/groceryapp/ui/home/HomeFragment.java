package com.groceryapp.ui.home;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.groceryapp.R;
import com.groceryapp.model.Home;
import com.groceryapp.ui.histroy.HistroyList;
import com.groceryapp.ui.scanner.QrFragment;
import com.groceryapp.ui.shopping_cart.UserItemList;
import com.groceryapp.ui.trash.TrashItList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeAdapter.OnItemSelect {

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ShellActivity shellActivity;

    private HomeAdapter adapter;
    private List<Home> homeList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View home = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, home);

        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }

        initCollapsingToolbar();
        shellActivity.setToolbarTitle("Home");
        homeList = new ArrayList<>();
        adapter = new HomeAdapter(getContext(), homeList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.img_background).into((ImageView) home.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return home;
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.ic_shopping_cart,
                R.drawable.ic_shopping_list,
                R.drawable.ic_history,
                R.drawable.ic_trash_it,
                R.drawable.ic_add_shopping_cart,
                R.drawable.ic_logout

        };

        Home a = new Home("Add to Cart", covers[4]);
        homeList.add(a);

        a = new Home("Shopping List", covers[1]);
        homeList.add(a);

        a = new Home("History", covers[2]);
        homeList.add(a);

        a = new Home("Trash It", covers[3]);
        homeList.add(a);

        a = new Home("Cart", covers[0]);
        homeList.add(a);

        a = new Home("Logout", covers[5]);
        homeList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TransparentText);
        collapsingToolbarLayout.setTitle(" ");

        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onItemSelect(String name) {
        if (name.equals("Add to Cart")) {
            shellActivity.loadMainContainer(QrFragment.getInstance(1));
        } else if (name.equals("Shopping List")) {
            shellActivity.loadMainContainer(new TrashItList());
        } else if (name.equals("History")) {
            shellActivity.loadMainContainer(new HistroyList());
        } else if (name.equals("Trash It")) {
            shellActivity.loadMainContainer(QrFragment.getInstance(2));
        } else if (name.equals("Cart")) {
            shellActivity.loadMainContainer(new UserItemList());
        } else {
            shellActivity.signout();
        }

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
