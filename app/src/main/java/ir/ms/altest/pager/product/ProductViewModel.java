package ir.ms.altest.pager.product;

import android.util.Log;

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
import ir.ms.altest.db.product.CategoryModel;
import ir.ms.altest.db.product.ProductModel;
import ir.ms.altest.pager.PagerRepository;

public class ProductViewModel extends ViewModel {
    private final PagerRepository pagerRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<List<ProductModel>> productLiveData;
    private MutableLiveData<List<CategoryModel>> categoryLiveData;

    @Inject
    public ProductViewModel(PagerRepository favoriteShowsRepository) {
        this.pagerRepository = favoriteShowsRepository;
        compositeDisposable = new CompositeDisposable();
        productLiveData = new MutableLiveData<>();
        categoryLiveData = new MutableLiveData<>();
    }

    public void loadProducts(long id) {
        Disposable disposable = pagerRepository.getProductsByCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onProductsFetched, this::onError);
        compositeDisposable.add(disposable);
    }

    public void loadCategories() {
        Disposable disposable = pagerRepository.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCategoriesFetched, this::onError);
        compositeDisposable.add(disposable);
    }

    private void onError(Throwable throwable) {
        Log.d("ViewModel", throwable.getMessage());
    }

    private void onProductsFetched(List<ProductModel> productModels) {
        productLiveData.setValue(productModels);
    }

    private void onCategoriesFetched(List<CategoryModel> categoryModels) {
        categoryLiveData.setValue(categoryModels);
    }

    public LiveData<List<ProductModel>> getProductsLiveData() {
        return productLiveData;
    }

    public LiveData<List<CategoryModel>> getCategoriesLiveData() {
        return categoryLiveData;
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
