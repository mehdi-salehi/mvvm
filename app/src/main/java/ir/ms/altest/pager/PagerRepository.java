package ir.ms.altest.pager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ir.ms.altest.db.order.OrderDao;
import ir.ms.altest.db.order.OrderModel;
import ir.ms.altest.db.product.CategoryDao;
import ir.ms.altest.db.product.CategoryModel;
import ir.ms.altest.db.product.ProductDao;
import ir.ms.altest.db.product.ProductModel;

@Singleton
public class PagerRepository {
    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final CategoryDao categoryDao;

    @Inject
    public PagerRepository(ProductDao productDao, OrderDao orderDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.categoryDao = categoryDao;
    }

    public Single<List<ProductModel>> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Single<List<ProductModel>> getProductsByCategory(long id) {
        return productDao.getProductsByCategory(id);
    }

    public Single<List<OrderModel>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public Single<List<CategoryModel>> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public void insertIntoProducts(ProductModel model) {
        productDao.insert(model);
    }

    public void removeFromProducts(ProductModel model) {
        productDao.remove(model);
    }

    public void insertIntoOrders(OrderModel model) {
        orderDao.insert(model);
    }

    public void updateOrder(OrderModel model) {
        orderDao.updateOrder(model);
    }

    public void removeFromOrders(OrderModel model) {
        orderDao.remove(model);
    }
}
