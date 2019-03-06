package ir.ms.altest.pager.order;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.ms.altest.db.order.OrderModel;
import ir.ms.altest.db.product.ProductModel;
import ir.ms.altest.pager.PagerRepository;

public class OrderViewModel extends ViewModel {
    private final PagerRepository pagerRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<List<ProductModel>> productLiveData;
    private MutableLiveData<List<OrderModel>> orderLiveData;

    @Inject
    public OrderViewModel(PagerRepository favoriteShowsRepository) {
        this.pagerRepository = favoriteShowsRepository;
        compositeDisposable = new CompositeDisposable();
        productLiveData = new MutableLiveData<>();
        orderLiveData = new MutableLiveData<>();
    }

    public void loadOrders() {
        Disposable disposable = pagerRepository.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onOrdersFetched, this::onError);
        compositeDisposable.add(disposable);
    }

    public void checkOrders() {
        Disposable disposable = pagerRepository.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::changeOrders, this::onError);
        compositeDisposable.add(disposable);
    }

    private void changeOrders(List<OrderModel> orderModels) {
        for (OrderModel model : orderModels) {
            if (model.getStatus() < 3) {
                Log.e("changeOrders", "getStatus: " + model.getStatus());
                model.setStatus(model.getStatus() + 1);
                pagerRepository.updateOrder(model);
            }
        }
    }

    private void onError(Throwable throwable) {
        Log.d("ViewModel", throwable.getMessage());
    }

    private void onProductsFetched(List<ProductModel> data) {
        productLiveData.setValue(data);
    }

    private void onOrdersFetched(List<OrderModel> data) {
        Collections.reverse(data);
        orderLiveData.setValue(data);
    }

    public LiveData<List<ProductModel>> getProductsLiveData() {
        return productLiveData;
    }

    public LiveData<List<OrderModel>> getOrdersLiveData() {
        return orderLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void addProduct(ProductModel model) {
        pagerRepository.insertIntoProducts(model);
    }

    public void removeProduct(ProductModel model) {
        pagerRepository.removeFromProducts(model);
    }

    public void addOrder(OrderModel model) {
        pagerRepository.insertIntoOrders(model);
    }

    public void removeOrder(OrderModel model) {
        pagerRepository.removeFromOrders(model);
    }
}
