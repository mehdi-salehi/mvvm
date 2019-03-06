package ir.ms.altest.pager.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.ms.altest.R;
import ir.ms.altest.common.Constants;
import ir.ms.altest.common.GridItemDecoration;
import ir.ms.altest.databinding.FragmentOrderBinding;
import ir.ms.altest.db.order.OrderModel;
import ir.ms.altest.db.product.ProductModel;
import ir.ms.altest.di.Injectable;


public class OrderFragment extends Fragment implements Injectable, OrderAdapter.Callback {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private OrderViewModel viewModel;
    private FragmentOrderBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private OrderAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OrderViewModel.class);
        viewModel.getOrdersLiveData().observe(this, this::show);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), Constants.NO_OF_COLUMNS);
        binding.list.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(this);
        binding.list.setAdapter(adapter);
        int spacing = getResources().getDimensionPixelSize(R.dimen.show_grid_spacing);
        binding.list.addItemDecoration(new GridItemDecoration(spacing, Constants.NO_OF_COLUMNS));

        Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getDisposable().add(d);
                    }

                    @Override
                    public void onNext(Object aLong) {
                        Log.e("onNext", "reload Orders");
                        viewModel.loadOrders();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Orders runner", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadOrders();
    }

    @Override
    public void onDestroy() {
        if (getDisposable() != null)
            getDisposable().clear();
        super.onDestroy();
    }

    public static OrderFragment getInstance(int state) {
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        OrderFragment f = new OrderFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void show(List<OrderModel> data) {
        binding.progress.setVisibility(View.GONE);
        adapter.updateList(data);
        binding.list.setVisibility(View.VISIBLE);
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    @Override
    public void onClicked(ProductModel show) {

    }
}
