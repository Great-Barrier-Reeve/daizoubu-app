package org.greatbarrierreeve.daizoubu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class BountiesAdapter extends RecyclerView.Adapter<BountiesAdapter.ViewHolder> {

    private String[] localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;


        public ViewHolder(View view) {

            super(view);

            textView = (TextView) view.findViewById(R.id.textView);

        }


        public TextView getTextView() {

            return textView;

        }

    }


    public BountiesAdapter(String[] dataSet) {

        localDataSet = dataSet;

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

        viewHolder.getTextView().setText(localDataSet[position]);

    }


    @Override
    public int getItemCount() {

        return localDataSet.length;

    }

}