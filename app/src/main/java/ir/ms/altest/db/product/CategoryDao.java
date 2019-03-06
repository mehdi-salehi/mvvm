package ir.ms.altest.db.product;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM cats")
    Single<List<CategoryModel>> getAllCategories();
}
