package org.greatbarrierreeve.daizoubu.ui.order.menu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.AddOnItem;


public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {

    private final AddOnSelectionController addOnSelectionController;
    private final List<AddOnItem> localDataSet;


    public interface AddOnSelectionController {

        boolean isAddOnSelected(AddOnItem addOn);


        void setAddOnSelected(AddOnItem addOn, boolean isChecked);

    }


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
    public ItemDetailAdapter(List<AddOnItem> dataSet, AddOnSelectionController controller) {

        localDataSet = dataSet;
        addOnSelectionController = controller;

    }


    // create new views (invoked by layout manager)
    @NonNull
    @Override
    public ItemDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_addon, viewGroup, false);
        return new ItemDetailAdapter.ViewHolder(itemView);

    }


    // replace contents of view (invoked by layout manager)
    @Override
    public void onBindViewHolder(ItemDetailAdapter.ViewHolder viewHolder, final int position) {

        AddOnItem addOnItem = localDataSet.get(position);

        viewHolder.textViewAddOnName.setText(addOnItem.getName());

        String addOnPrice = "+$" + (new BigDecimal(addOnItem.getPrice())).setScale(2, RoundingMode.HALF_UP).toPlainString();
        viewHolder.textViewAddOnPrice.setText(addOnPrice);

        // safely add checkbox change listener
        viewHolder.checkBoxAddOn.setOnCheckedChangeListener(null);
        viewHolder.checkBoxAddOn.setChecked(addOnSelectionController.isAddOnSelected(addOnItem));
        viewHolder.checkBoxAddOn.setOnCheckedChangeListener((btn, isChecked) -> {

            int pos = viewHolder.getBindingAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                addOnSelectionController.setAddOnSelected(localDataSet.get(pos), isChecked);

            }

        });

        viewHolder.itemView.setOnClickListener(view -> viewHolder.checkBoxAddOn.toggle());

    }


    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() { return localDataSet.size(); }

}
