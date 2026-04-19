package org.greatbarrierreeve.daizoubu.ui.bounties;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.Errand;
import org.greatbarrierreeve.daizoubu.ui.bounties.bounty.BountyActivity;
import org.greatbarrierreeve.daizoubu.ui.bounties.bounty.UserBountyActivity;


public class BountiesAdapter extends RecyclerView.Adapter<BountiesAdapter.ViewHolder> {

    public static final String EXTRA_ERRAND = "errand";

    private List<Errand> localDataSet;
    private final String userId;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewBountyAmount;
        final TextView textViewBuyerId;
        final TextView textViewStatus;

        public ViewHolder(View view) {
            super(view);
            textViewBountyAmount = view.findViewById(R.id.textViewBountyAmount);
            textViewBuyerId      = view.findViewById(R.id.textViewBuyerId);
            textViewStatus       = view.findViewById(R.id.textViewStatus);
        }
    }


    public BountiesAdapter(List<Errand> dataSet, String userId) {
        localDataSet = dataSet;
        this.userId = userId;
    }


    public void setErrands(List<Errand> errands) {
        localDataSet = errands;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bounty_item, viewGroup, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Errand errand = localDataSet.get(position);
        viewHolder.textViewBountyAmount.setText("$" + errand.getBounty());
        viewHolder.textViewBuyerId.setText(errand.getBuyerId());
        viewHolder.textViewStatus.setText(errand.getStatus() != null ? errand.getStatus().name() : "");

        viewHolder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            String errandJson = new Gson().toJson(errand);
            Class<?> destination = userId != null && userId.equals(errand.getBuyerId())
                    ? UserBountyActivity.class
                    : BountyActivity.class;
            Intent intent = new Intent(context, destination);
            intent.putExtra(EXTRA_ERRAND, errandJson);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
