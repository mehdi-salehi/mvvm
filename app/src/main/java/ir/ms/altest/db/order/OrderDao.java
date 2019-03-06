package ir.ms.altest.db.order;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    Single<List<OrderModel>> getAllOrders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderModel model);

    @Update
    void updateOrder(OrderModel model);

    @Delete
    void remove(OrderModel model);

}
