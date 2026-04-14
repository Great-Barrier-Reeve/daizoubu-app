package org.greatbarrierreeve.daizoubu.ui.order;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.Store;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<Store> storeList;
    private final OnStoreClickListener onStoreClickListener;


    public interface OnStoreClickListener {

        void onStoreClick(Store store);

    }


    // provides reference to type of views used
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewStoreCategory;
        private final TextView textViewStoreName;
        private final TextView textViewStoreDesc;


        public ViewHolder(View view) {

            super(view);

            textViewStoreCategory = view.findViewById(R.id.textViewStoreCategory);
            textViewStoreName = view.findViewById(R.id.textViewStoreName);
            textViewStoreDesc = view.findViewById(R.id.textViewStoreDesc);

        }

    }


    // initialise dataset of adapter
    public StoreAdapter(List<Store> stores, OnStoreClickListener listener) {

        this.storeList = stores;
        this.onStoreClickListener = listener;

    }


    // create new views (invoked by layout manager)
    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_store, viewGroup,false);
        return new StoreAdapter.ViewHolder(view);

    }


    // replace contents of view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {

        Store store = storeList.get(position);

        holder.textViewStoreCategory.setText(store.getCategory());
        holder.textViewStoreName.setText(store.getName());
        holder.textViewStoreDesc.setText(store.getDescription());

        // triggers store card click listener
        holder.itemView.setOnClickListener(view -> onStoreClickListener.onStoreClick(store));

    }


    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {

        return storeList.size();

    }


    public void updateStores(List<Store> storeList) {

        this.storeList = storeList;
        notifyDataSetChanged();

    }

}