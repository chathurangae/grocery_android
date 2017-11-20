package com.groceryapp.ui.histroy;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.model.ShoppingCart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistroyAdapter extends RecyclerView.Adapter<HistroyAdapter.HistroyListViewHolder> {
    private final Context mContext;
    private final List<ShoppingCart> itemList;
    private final int DIRECTORY_DETAIL = 100;
    private View directoryItemView;


    HistroyAdapter(Context mContext, List<ShoppingCart> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;

    }

    @Override
    public HistroyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        directoryItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_row, parent, false);

        return new HistroyListViewHolder(directoryItemView);
    }

    @Override
    public void onBindViewHolder(HistroyListViewHolder holder, int position) {
        ShoppingCart item = itemList.get(position);
        holder.itemName.setText(item.getInvoiceCode());
        holder.itemId.setText(item.getDate());
        holder.itemQuant.setText(String.valueOf(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    void clear() {
        int size = this.itemList.size();
        this.itemList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class HistroyListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_id)
        TextView itemId;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_quant)
        TextView itemQuant;
        @BindView(R.id.item_delete)
        ImageView delete;


        HistroyListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            delete.setVisibility(View.GONE);
            itemPrice.setVisibility(View.GONE);
        }

    }

}


