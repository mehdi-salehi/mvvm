package ir.ms.altest.pager.order;

import android.os.Bundle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import ir.ms.altest.db.order.OrderModel;

public class OrderDiffUtilCallback extends DiffUtil.Callback {
    private final List<OrderModel> oldList;
    private final List<OrderModel> newList;

    public OrderDiffUtilCallback(List<OrderModel> oldList, List<OrderModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        OrderModel oldItem = oldList.get(oldItemPosition);
        OrderModel newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        OrderModel oldItem = oldList.get(oldItemPosition);
        OrderModel newItem = newList.get(newItemPosition);
        return oldItem.getStatus() == newItem.getStatus();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        OrderModel oldItem = oldList.get(oldItemPosition);
        OrderModel newItem = newList.get(newItemPosition);
        Bundle bundle = new Bundle();
        if (oldItem.getStatus() != newItem.getStatus()) {
            bundle.putInt("status", newItem.getStatus());
        }
        return bundle;
    }
}
