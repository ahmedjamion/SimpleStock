package com.ahmedjamion.simplestock.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventoryRepository {
    private InventoryDao inventoryDao;
    private LiveData<List<Inventory>> allInventory;
    private ExecutorService executorService;

    private LiveData<String> inventoryNameById;


    public InventoryRepository(Application application){
        SimpleStockDatabase database = SimpleStockDatabase.getInstance(application);
        inventoryDao = database.inventoryDao();
        allInventory = inventoryDao.getAllInventory();
        executorService = Executors.newSingleThreadExecutor();
    }
    public void insert(Inventory inventory) {
        executorService.submit(() -> inventoryDao.insert(inventory));
    }

    public void update(Inventory inventory){
        executorService.submit(() -> inventoryDao.update(inventory));
    }

    public void delete(Inventory inventory){
        executorService.submit(() -> inventoryDao.delete(inventory));
    }

    public LiveData<List<Inventory>> getAllInventory(){
        return allInventory;
    }

    public LiveData<String> getInventoryNameById(int inventoryId){
        inventoryNameById = inventoryDao.getInventoryNameById(inventoryId);
        return inventoryNameById;
    }
}
