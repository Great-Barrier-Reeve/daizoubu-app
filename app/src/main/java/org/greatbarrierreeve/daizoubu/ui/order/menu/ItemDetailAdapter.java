package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;


public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {

    private final List<AddOnItem> localDataSet;

    // provides reference to type of views used
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBoxAddOn;
        private final TextView textViewAddOnName;
        private final TextView textViewAddOnPrice;

        public ViewHolder(View view) {

            super(view);

            checkBoxAddOn = view.findViewById(R.id.checkBoxAddOn);
            textViewAddOnName = view.findViewById(R.id.textViewAddOnName);
            textViewAddOnPrice = view.findViewById(R.id.textViewAddOnPrice);

        }

    }


    // initialise dataset of adapter
    public ItemDetailAdapter(List<AddOnItem> dataSet) {

        localDataSet = dataSet;

    }


    // create new views (invoked by layout manager)
    @NonNull
    @Override
    public ItemDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.addon_item, viewGroup, false);
        return new ItemDetailAdapter.ViewHolder(itemView);

    }


    // replace contents of view (invoked by layout manager)
    @Override
    public void onBindViewHolder(ItemDetailAdapter.ViewHolder viewHolder, final int position) {

        AddOnItem addOnItem = localDataSet.get(position);
        viewHolder.textViewAddOnName.setText(addOnItem.getName());
        viewHolder.textViewAddOnPrice.setText("+$" + addOnItem.getPrice());

        // safely add checkbox change listener
        viewHolder.checkBoxAddOn.setOnCheckedChangeListener(null);
        viewHolder.checkBoxAddOn.setChecked(addOnItem.getIsSelected());
        viewHolder.checkBoxAddOn.setOnCheckedChangeListener((btn, isChecked) -> addOnItem.setIsSelected(isChecked));

        viewHolder.itemView.setOnClickListener(view -> viewHolder.checkBoxAddOn.toggle());

    }


    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {

        return localDataSet.size();

    }

}
