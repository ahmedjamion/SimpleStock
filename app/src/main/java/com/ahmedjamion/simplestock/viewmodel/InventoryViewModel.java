package com.ahmedjamion.simplestock.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.database.InventoryRepository;

import java.util.List;

public class  InventoryViewModel extends AndroidViewModel {
    private InventoryRepository repository;
    private LiveData<List<Inventory>> allInventory;
    private LiveData<String> inventoryNameById;
    public InventoryViewModel(@NonNull Application application) {
        super(application);
        repository = new InventoryRepository(application);
        allInventory = repository.getAllInventory();
    }

    public void insert(Inventory inventory){
        repository.insert(inventory);
    }

    public void delete(Inventory inventory){
        repository.delete(inventory);
    }

    public void update(Inventory inventory) {
        repository.update(inventory);
    }

    public LiveData<List<Inventory>> getAllInventory() {
        return allInventory;
    }

    public LiveData<String> getInventoryNameById(int inventoryId){
        inventoryNameById = repository.getInventoryNameById(inventoryId);
        return inventoryNameById;
    }
}
