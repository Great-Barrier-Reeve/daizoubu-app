package org.greatbarrierreeve.daizoubu.ui.order.cart;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;
import org.greatbarrierreeve.daizoubu.data.model.OrderItem;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private List<OrderItem> orderItems;
    private final OnRemoveClickListener onRemoveClickListener;


    public interface OnRemoveClickListener {

        void onRemoveClick(OrderItem orderItem);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewItemName;
        private final TextView textViewItemPrice;
        private final TextView textViewItemQty;
        private final TextView textViewAddOns;
        private final ImageView iconRemoveItem;


        public ViewHolder(View view) {

            super(view);

            textViewItemName = view.findViewById(R.id.textViewItemName);
            textViewItemPrice = view.findViewById(R.id.textViewItemPrice);
            textViewItemQty = view.findViewById(R.id.textViewItemQty);
            textViewAddOns = view.findViewById(R.id.textViewAddOns);
            iconRemoveItem = view.findViewById(R.id.iconRemoveItem);

        }

    }


    public CartItemAdapter(List<OrderItem> orderItems, OnRemoveClickListener listener) {

        this.orderItems = orderItems;
        this.onRemoveClickListener = listener;

    }


    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_order, viewGroup, false);
        return new CartItemAdapter.ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(CartItemAdapter.ViewHolder viewHolder, final int position) {

        OrderItem orderItem = orderItems.get(position);

        viewHolder.textViewItemName.setText(orderItem.getName());

        String itemPrice = String.format("$%.2f", Double.parseDouble(orderItem.getPrice()));
        viewHolder.textViewItemPrice.setText(itemPrice);

        viewHolder.textViewItemQty.setText("x" + orderItem.getQty());

        List<AddOnItem> addOns = orderItem.getUserAddOns();
        if (addOns != null && !addOns.isEmpty()) {
            String addOnNames = addOns.stream().map(AddOnItem::getName).collect(Collectors.joining(", "));
            viewHolder.textViewAddOns.setText(addOnNames);
            viewHolder.textViewAddOns.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textViewAddOns.setVisibility(View.GONE);
        }

        viewHolder.iconRemoveItem.setOnClickListener(v -> onRemoveClickListener.onRemoveClick(orderItem));

    }


    @Override
    public int getItemCount() {

        return orderItems.size();

    }


    public void updateItems(List<OrderItem> orderItems) {

        this.orderItems = orderItems;
        notifyDataSetChanged();

    }

}
