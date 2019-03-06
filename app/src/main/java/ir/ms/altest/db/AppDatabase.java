package ir.ms.altest.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import ir.ms.altest.db.order.OrderDao;
import ir.ms.altest.db.order.OrderModel;
import ir.ms.altest.db.product.CategoryDao;
import ir.ms.altest.db.product.CategoryModel;
import ir.ms.altest.db.product.ProductDao;
import ir.ms.altest.db.product.ProductModel;

@Database(entities = {
        ProductModel.class,
        OrderModel.class,
        CategoryModel.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "app.db";

    public abstract ProductDao productDao();

    public abstract OrderDao orderDao();

    public abstract CategoryDao categoryDao();

}
