package ir.ms.altest.common;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int noOfColumns;

    public GridItemDecoration(int space, int noOfColumns) {
        this.space = space;
        this.noOfColumns = noOfColumns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;

        if (parent.getChildLayoutPosition(view) % noOfColumns == 0) {
            outRect.left = 0;
            outRect.right = space;
        } else if (parent.getChildLayoutPosition(view) % noOfColumns == noOfColumns - 1) {
            outRect.left = space;
            outRect.right = 0;
        } else {
            //do nothing
        }
    }
}
