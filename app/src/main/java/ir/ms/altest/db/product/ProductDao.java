package ir.ms.altest.db.product;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    Single<List<ProductModel>> getAllProducts();

    @Query("SELECT * FROM products WHERE cat = :cid")
    Single<List<ProductModel>> getProductsByCategory(long cid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductModel model);

    @Delete
    void remove(ProductModel model);

}
