package com.ahmedjamion.simplestock.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Inventory.class, Category.class, Product.class}, version = 1)
public abstract class SimpleStockDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 1;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static SimpleStockDatabase instance;

    public abstract InventoryDao inventoryDao();
    public abstract CategoryDao categoryDao();
    public abstract ProductDao productDao();

    public static synchronized SimpleStockDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SimpleStockDatabase.class, "simple_stock")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Execute the PopulateDbRunnable using ExecutorService when the database is created
            databaseWriteExecutor.execute(new PopulateDbRunnable(instance));
        }
    };

    private static class PopulateDbRunnable implements Runnable {
        private InventoryDao inventoryDao;
        private CategoryDao categoryDao;
        private ProductDao productDao;

        private PopulateDbRunnable(SimpleStockDatabase db) {
            inventoryDao = db.inventoryDao();
            categoryDao = db.categoryDao();
            productDao = db.productDao();
        }

        @Override
        public void run() {
            // Insert initial data into the database
            inventoryDao.insert(new Inventory("Inventory1"));
            categoryDao.insert(new Category("Category1", 1));
            productDao.insert(new Product("Product1", 1, 1, 1, 1));
        }
    }


}
