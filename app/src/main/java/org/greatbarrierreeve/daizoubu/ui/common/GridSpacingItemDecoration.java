package org.greatbarrierreeve.daizoubu.ui.common;


import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spanCount;
    private final int spacing;


    public GridSpacingItemDecoration(int spanCount, int spacing) {

        this.spanCount = spanCount;
        this.spacing = spacing;

    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        // get index position of view from recyclerview
        int position = parent.getChildAdapterPosition(view);

        // compute column index of view
        int column = position % spanCount;

        // add top spacing unless in top-most row
        outRect.top = position < spanCount ? 0 : spacing;

        // add left spacing unless in left-most column
        outRect.left = column == 0 ? 0 : spacing / 2;

        // add right spacing unless in right-most column
        outRect.right = column == spanCount - 1 ? 0 : spacing / 2;

    }

}
