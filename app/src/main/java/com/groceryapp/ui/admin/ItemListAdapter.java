package com.groceryapp.ui.admin;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.model.GroceryItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private final Context mContext;
    private final List<GroceryItem> itemList;
    private final int DIRECTORY_DETAIL = 100;
    private View directoryItemView;
    private OnItemDelete onItemDelete;
    private int currentType;

    ItemListAdapter(Context mContext, List<GroceryItem> itemList, OnItemDelete onItemDelete,
                    int currentType) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.currentType = currentType;
        this.onItemDelete = onItemDelete;
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        directoryItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_card, parent, false);

        return new ItemListAdapter.ItemListViewHolder(directoryItemView);
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder holder, int position) {
        GroceryItem item = itemList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(String.valueOf(item.getPrice()) + " Rs");
        holder.itemId.setText(item.getBarCodeId());
        if(currentType==1)
        {
            holder.delete.setBackgroundResource(R.drawable.ic_accepted);

        }else {
            holder.delete.setBackgroundResource(R.drawable.ic_delete);
        }
        holder.delete.setOnClickListener(v -> {
            onItemDelete.onItemDelete(item.getBarCodeId());
        });

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

    List<GroceryItem> currentList() {
        return itemList;
    }


    class ItemListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_id)
        TextView itemId;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_delete)
        ImageView delete;


        ItemListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemDelete {
        void onItemDelete(String barCode);
    }
}
