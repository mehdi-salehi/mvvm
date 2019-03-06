package ir.ms.altest.pager.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ir.ms.altest.R;
import ir.ms.altest.common.Constants;
import ir.ms.altest.common.GridItemDecoration;
import ir.ms.altest.common.Status;
import ir.ms.altest.databinding.FragmentProductBinding;
import ir.ms.altest.db.order.OrderModel;
import ir.ms.altest.db.product.CategoryModel;
import ir.ms.altest.db.product.ProductModel;
import ir.ms.altest.di.Injectable;
import ir.ms.altest.pager.PagerActivity;
import ir.ms.altest.pager.map.MapActivity;


public class ProductFragment extends Fragment implements ProductAdapter.Callback,
        Injectable, CategoryAdapter.Callback {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProductViewModel viewModel;
    private FragmentProductBinding binding;
    private ProductModel selected = null;
    private GridLayoutManager layoutManager;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        View view = binding.getRoot();
        unbinder = ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel.class);
        viewModel.getProductsLiveData().observe(this, this::showProducts);
        viewModel.getCategoriesLiveData().observe(this, this::showCategories);

        layoutManager = new GridLayoutManager(getActivity(), Constants.NO_OF_COLUMNS);
        binding.list.setLayoutManager(layoutManager);
        int spacing = getResources().getDimensionPixelSize(R.dimen.show_grid_spacing);
        binding.list.addItemDecoration(new GridItemDecoration(spacing, Constants.NO_OF_COLUMNS));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadCategories();
    }

    public static ProductFragment getInstance(int state) {
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        ProductFragment f = new ProductFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showProducts(List<ProductModel> data) {
        binding.progress.setVisibility(View.GONE);

        productAdapter = new ProductAdapter(data, this);
        binding.list.setAdapter(productAdapter);

        binding.list.setVisibility(View.VISIBLE);
    }

    private void showCategories(List<CategoryModel> data) {
        binding.progress.setVisibility(View.GONE);

        categoryAdapter = new CategoryAdapter(data, this);
        binding.list.setAdapter(categoryAdapter);

        binding.list.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClicked(ProductModel model) {
        Log.d("onClicked", model.getName());
        this.selected = model;
        Intent i = new Intent(getActivity(), MapActivity.class);
        startActivityForResult(i, 12);
        binding.back.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == MapActivity.RESULT_OK) {
            OrderModel o = new OrderModel();
            o.setLat(data.getStringExtra("lat"));
            o.setLat(data.getStringExtra("lng"));
            o.setName(selected.getName());
            o.setStatus(Status.PENDING.getValue());
            o.setPid(selected.getId());
            viewModel.addOrder(o);
            ((PagerActivity) getActivity()).nextPage();
        }
    }

    @Override
    public void onCategoryClicked(CategoryModel model) {
        viewModel.loadProducts(model.getId());
        final Handler handler = new Handler();
        handler.postDelayed(() -> binding.back.setVisibility(View.VISIBLE), 500);
    }

    @OnClick(R.id.back)
    public void backToCategory() {
        viewModel.loadCategories();
        binding.back.setVisibility(View.GONE);
    }
}
