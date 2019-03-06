package ir.ms.altest.pager.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.ms.altest.databinding.ProductListItemBinding;
import ir.ms.altest.db.product.ProductModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    private final List<ProductModel> data;
    private final Callback callback;

    public ProductAdapter(List<ProductModel> data,
                          Callback callback) {
        this.data = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductListItemBinding binding = ProductListItemBinding.inflate(layoutInflater, parent, false);
        Holder holder = new Holder(binding);
        holder.binding.box.setOnClickListener(v -> onFavouriteIconClicked(holder.getAdapterPosition()));
        return holder;
    }

    private void onFavouriteIconClicked(int position) {
        if (position != RecyclerView.NO_POSITION) {
            ProductModel model = data.get(position);
            callback.onClicked(model);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ProductModel model = data.get(position);
        holder.binding.productName.setText(model.getName());
        holder.binding.productCat.setText(model.getCat_name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final ProductListItemBinding binding;

        public Holder(ProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onClicked(ProductModel show);
    }
}
