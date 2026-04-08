package org.greatbarrierreeve.daizoubu.ui.bounties;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import org.greatbarrierreeve.daizoubu.R;
import org.greatbarrierreeve.daizoubu.data.model.Errand;


public class BountiesAdapter extends RecyclerView.Adapter<BountiesAdapter.ViewHolder> {

    private List<Errand> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;


        public ViewHolder(View view) {

            super(view);

            textView = view.findViewById(R.id.textView);

        }


        public TextView getTextView() {

            return textView;

        }

    }


    public BountiesAdapter(List<Errand> dataSet) {

        localDataSet = dataSet;

    }


    public void setErrands(List<Errand> errands) {

        localDataSet = errands;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getTextView().setText(localDataSet.get(position).toString());

    }


    @Override
    public int getItemCount() {

        return localDataSet.size();

    }

}