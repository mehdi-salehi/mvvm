package ir.ms.altest.di;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Random;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;
import ir.ms.altest.db.AppDatabase;
import ir.ms.altest.db.order.OrderDao;
import ir.ms.altest.db.product.CategoryDao;
import ir.ms.altest.db.product.ProductDao;

@Module
public class AppDbModule {
    @Singleton
    @Provides
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
//                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
//                            @Override
//                            public void run() {
                        for (int i = 1; i < 4; i++) {
                            ContentValues values = new ContentValues();
                            values.put("id", i);
                            values.put("name", "category " + i);
                            db.insert("cats", SQLiteDatabase.CONFLICT_REPLACE, values);
                        }
                        for (int i = 1; i < 30; i++) {
                            ContentValues values = new ContentValues();
                            values.put("id", i);
                            values.put("name", "product " + i);
                            values.put("cat", new Random().nextInt(3) + 1);
                            db.insert("products", SQLiteDatabase.CONFLICT_REPLACE, values);
                        }
                        //}
                        //});
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    public ProductDao provideProductDao(AppDatabase appDatabase) {
        return appDatabase.productDao();
    }

    @Singleton
    @Provides
    public OrderDao provideOrderDao(AppDatabase appDatabase) {
        return appDatabase.orderDao();
    }

    @Singleton
    @Provides
    public CategoryDao provideCategoryDao(AppDatabase appDatabase) {
        return appDatabase.categoryDao();
    }
}
