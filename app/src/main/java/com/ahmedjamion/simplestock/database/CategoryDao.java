package com.ahmedjamion.simplestock.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);
    @Delete
    void delete(Category category);

    @Query("select * from Category")
    LiveData<List<Category>> getAllCategory();

    @Query("SELECT * FROM Category WHERE inventoryId = :inventoryId")
    LiveData<List<Category>> getCategoryByInventory(int inventoryId);

    @Query("SELECT categoryName FROM Category WHERE categoryId = :categoryId")
    String getCategoryNameByCategoryId(int categoryId);
}
