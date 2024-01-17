package com.ahmedjamion.simplestock.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categoryByInventory;
    private String categoryNameByCategoryId;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public void insert(Category category){
        categoryRepository.insert(category);
    }

    public void delete(Category category){
        categoryRepository.delete(category);
    }

    public LiveData<List<Category>> getCategoryByInventory(int inventoryId) {
        categoryByInventory = categoryRepository.getCategoryByInventory(inventoryId);
        return categoryByInventory;
    }

    public String getCategoryNameByCategoryId(int categoryId){
        categoryNameByCategoryId = getCategoryNameByCategoryId(categoryId);
        return categoryNameByCategoryId;
    }
}
