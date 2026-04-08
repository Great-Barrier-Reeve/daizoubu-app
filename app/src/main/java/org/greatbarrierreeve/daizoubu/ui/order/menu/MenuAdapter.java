package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.MenuItem;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private final List<MenuItem> localDataSet;


    // provides reference to type of views used
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardItem;
        private final TextView textViewItemName;
        private final TextView textViewItemPrice;


        public ViewHolder(View view) {

            super(view);

            cardItem = view.findViewById(R.id.cardItem);
            textViewItemName = view.findViewById(R.id.textViewItemName);
            textViewItemPrice = view.findViewById(R.id.textViewItemPrice);

        }

    }


    // initialise dataset of adapter
    public MenuAdapter(List<MenuItem> dataSet) {

        localDataSet = dataSet;

    }


    // create new views (invoked by layout manager)
    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);
        return new MenuAdapter.ViewHolder(itemView);

    }


    // replace contents of view (invoked by layout manager)
    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder viewHolder, final int position) {

        MenuItem menuItem = localDataSet.get(position);
        viewHolder.textViewItemName.setText(menuItem.getName());
        viewHolder.textViewItemPrice.setText(menuItem.getPrice());

        viewHolder.cardItem.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
            intent.putExtra("menuItem", menuItem);
            view.getContext().startActivity(intent);

        });

    }


    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {

        return localDataSet.size();

    }

}