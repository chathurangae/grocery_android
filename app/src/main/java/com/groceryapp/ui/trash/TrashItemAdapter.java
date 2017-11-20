package com.groceryapp.ui.trash;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.model.ShoppingList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrashItemAdapter extends RecyclerView.Adapter<TrashItemAdapter.TrashListViewHolder> {
    private final Context mContext;
    private final List<ShoppingList> itemList;
    private final int DIRECTORY_DETAIL = 100;
    private View directoryItemView;
    private OnItemDelete onItemDelete;

    TrashItemAdapter(Context mContext, List<ShoppingList> itemList, OnItemDelete onItemDelete) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.onItemDelete = onItemDelete;
    }

    @Override
    public TrashListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        directoryItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_row, parent, false);

        return new TrashListViewHolder(directoryItemView);
    }

    @Override
    public void onBindViewHolder(TrashListViewHolder holder, int position) {
        ShoppingList item = itemList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemId.setText(item.getBarCodeId());
        holder.itemQuant.setText(String.valueOf(item.getQuantity()));
        holder.delete.setBackgroundResource(R.drawable.ic_delete);

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

    class TrashListViewHolder extends RecyclerView.ViewHolder {

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


        TrashListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemPrice.setVisibility(View.GONE);
        }

    }

    public interface OnItemDelete {
        void onItemDelete(String barCode);
    }
}
