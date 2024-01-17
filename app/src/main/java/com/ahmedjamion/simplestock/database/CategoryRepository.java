package com.ahmedjamion.simplestock.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategory;
    private LiveData<List<Category>> categoryByInventory;
    private String categoryNameByCategoryId;
    private ExecutorService executorService;

    private Category category;

    public CategoryRepository(Application application){
        SimpleStockDatabase database = SimpleStockDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        allCategory = categoryDao.getAllCategory();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Category category) {
        executorService.submit(() -> categoryDao.insert(category));
    }

    public void delete(Category category) {
        executorService.submit(() -> categoryDao.delete(category));
    }

    public LiveData<List<Category>> getCategoryByInventory(int inventoryId){
        categoryByInventory = categoryDao.getCategoryByInventory(inventoryId);
        return categoryByInventory;
    }

    public String getCategoryNameByCategoryId(int categoryId){
        categoryNameByCategoryId = categoryDao.getCategoryNameByCategoryId(categoryId);
        return categoryNameByCategoryId;
    }
}
