package org.greatbarrierreeve.daizoubu.ui.order.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greatbarrierreeve.daizoubu.data.model.Location;
import org.greatbarrierreeve.daizoubu.R;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.Viewholder> {
    private List<Location> locationList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        private final TextView textView;

        public ViewHolder(View view){
            super(view);
//            textView = view.findViewById(R.id.textViewBounty);
        }
        public TextView getTextView(){
//            return textView;
        }


    }
    public LocationAdapter(List<Location> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.Viewholder holder, int position) {
        holder.getTextView().setText(locationList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void updateData(List<Location> locationList){
        this.locationList = locationList;
        notifyDataSetChanged();
    }

}
