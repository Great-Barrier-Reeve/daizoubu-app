package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;


public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    private List<MenuItem> menuItems;
    private final OnMenuItemClickListener onMenuItemClickListener;


    public interface OnMenuItemClickListener {

        void onMenuItemClick(MenuItem menuItem);

    }


    // provides reference to type of views used
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewItemName;
        private final TextView textViewItemPrice;


        public ViewHolder(View view) {

            super(view);

            textViewItemName = view.findViewById(R.id.textViewItemName);
            textViewItemPrice = view.findViewById(R.id.textViewItemPrice);

        }

    }


    // initialise dataset of adapter
    public MenuItemAdapter(List<MenuItem> menuItems, OnMenuItemClickListener listener) {

        this.menuItems = menuItems;
        this.onMenuItemClickListener = listener;

    }


    // create new views (invoked by layout manager)
    @NonNull
    @Override
    public MenuItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_menu, viewGroup, false);
        return new MenuItemAdapter.ViewHolder(itemView);

    }


    // replace contents of view (invoked by layout manager)
    @Override
    public void onBindViewHolder(MenuItemAdapter.ViewHolder viewHolder, final int position) {

        MenuItem menuItem = menuItems.get(position);
        viewHolder.textViewItemName.setText(menuItem.getName());
        viewHolder.textViewItemPrice.setText(menuItem.getPrice());

        viewHolder.itemView.setOnClickListener(v -> onMenuItemClickListener.onMenuItemClick(menuItem));

    }


    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {

        return menuItems.size();

    }


    public void updateMenu(List<MenuItem> menuItems) {

        this.menuItems = menuItems;
        notifyDataSetChanged();

    }

}