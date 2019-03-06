package ir.ms.altest.pager.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.ms.altest.databinding.CategoryListItemBinding;
import ir.ms.altest.db.product.CategoryModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    private final List<CategoryModel> data;
    private final Callback callback;

    public CategoryAdapter(List<CategoryModel> data, Callback callback) {
        this.data = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryListItemBinding binding = CategoryListItemBinding.inflate(layoutInflater, parent, false);
        Holder holder = new Holder(binding);
        holder.binding.rect.setOnClickListener(v -> onClicked(holder.getAdapterPosition()));
        return holder;
    }

    private void onClicked(int position) {
        if (position != RecyclerView.NO_POSITION) {
            CategoryModel model = data.get(position);
            callback.onCategoryClicked(model);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CategoryModel model = data.get(position);
        holder.binding.catName.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final CategoryListItemBinding binding;

        public Holder(CategoryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onCategoryClicked(CategoryModel show);
    }
}
