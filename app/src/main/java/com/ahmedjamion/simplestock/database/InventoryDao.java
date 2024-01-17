package com.ahmedjamion.simplestock.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InventoryDao {
    @Insert
    void insert(Inventory inventory);

    @Delete
    void delete(Inventory inventory);

    @Update
    void update(Inventory inventory);

    @Query("SELECT inventoryName FROM Inventory WHERE inventoryId = :inventoryId")
    LiveData<String> getInventoryNameById(int inventoryId);

    @Query("SELECT * FROM Inventory")
    LiveData<List<Inventory>> getAllInventory();

}
