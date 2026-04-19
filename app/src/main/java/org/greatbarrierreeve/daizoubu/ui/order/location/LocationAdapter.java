package org.greatbarrierreeve.daizoubu.ui.order.location;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.R;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locationList;
    private List<Location> allLocations;
    private final OnLocationClickListener onLocationClickListener;

    public interface OnLocationClickListener {
        void onLocationClick(Location location);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewLocationName;
        private final TextView textViewLocationAddress;


        public ViewHolder(View view) {

            super(view);

            textViewLocationName = view.findViewById(R.id.textViewLocationName);
            textViewLocationAddress = view.findViewById(R.id.textViewLocationAddress);

        }

    }


    public LocationAdapter(List<Location> locationList, OnLocationClickListener listener) {

        this.locationList = locationList;
        this.allLocations = new ArrayList<>(locationList);
        this.onLocationClickListener = listener;

    }


    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_location, viewGroup, false);
        return new LocationAdapter.ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {

        Location location = locationList.get(position);

        holder.textViewLocationName.setText(location.getDisplayName());
        holder.textViewLocationAddress.setText(location.getAddress());

        holder.itemView.setOnClickListener(view -> onLocationClickListener.onLocationClick(location));

    }


    @Override
    public int getItemCount() {

        return locationList.size();

    }


    public void updateData(List<Location> locations) {

        this.allLocations = new ArrayList<>(locations);
        this.locationList = new ArrayList<>(locations);
        notifyDataSetChanged();

    }


    public void filter(String query) {

        if (query == null || query.trim().isEmpty()) {
            locationList = new ArrayList<>(allLocations);
        } else {
            String lower = query.toLowerCase().trim();
            List<Location> filtered = new ArrayList<>();
            for (Location location : allLocations) {
                String name = location.getDisplayName() != null ? location.getDisplayName().toLowerCase() : "";
                String address = location.getAddress() != null ? location.getAddress().toLowerCase() : "";
                if (name.contains(lower) || address.contains(lower)) {
                    filtered.add(location);
                }
            }
            locationList = filtered;
        }
        notifyDataSetChanged();

    }

}
