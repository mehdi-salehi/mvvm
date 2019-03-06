package ir.ms.altest.pager.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ir.ms.altest.common.Status;
import ir.ms.altest.databinding.OrderListItemBinding;
import ir.ms.altest.db.order.OrderModel;
import ir.ms.altest.db.product.ProductModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder> {
    private List<OrderModel> data;
    private final Callback callback;

    public OrderAdapter(Callback callback) {
        this.data = Collections.emptyList();
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderListItemBinding binding = OrderListItemBinding.inflate(layoutInflater, parent, false);
        Holder holder = new Holder(binding);
        //holder.binding.box.setOnClickListener(v -> onFavouriteIconClicked(holder.getAdapterPosition()));
        return holder;
    }

    public void updateList(List<OrderModel> newList) {
        OrderDiffUtilCallback diffUtilCallback = new OrderDiffUtilCallback(data, newList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        data = newList;
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        OrderModel model = data.get(position);
        holder.binding.orderName.setText(model.getName());
        holder.binding.orderId.setText(String.format("order %s", String.valueOf(model.getId())));
        holder.binding.orderStatus.setText(Status.values()[model.getStatus()].name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final OrderListItemBinding binding;

        public Holder(OrderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onClicked(ProductModel show);
    }
}
